package belaquaa.crudpr.aspect;

import belaquaa.crudpr.dto.AuthRequest;
import belaquaa.crudpr.dto.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] maskedArgs = Arrays.stream(args)
                .map(this::maskSensitiveData)
                .toArray();
        log.info("Entering: {} with arguments {}", joinPoint.getSignature().toShortString(), maskedArgs);
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Object logDetail = extractLogDetail(result);
        log.info("Exiting: {} with {}", joinPoint.getSignature().toShortString(), logDetail);
    }

    private Object maskSensitiveData(Object arg) {
        if (arg instanceof AuthRequest auth) {
            return "AuthRequest(username=" + auth.getUsername() + ", password=*****)";
        }
        if (arg instanceof RegisterRequest reg) {
            return "RegisterRequest(username=" + reg.getUsername() + ", password=*****)";
        }
        return arg;
    }

    private Object extractLogDetail(Object result) {
        if (result instanceof ResponseEntity) {
            Object body = ((ResponseEntity<?>) result).getBody();
            return extractLogDetail(body);
        }
        if (result instanceof Page) {
            return "totalPages " + ((Page<?>) result).getTotalPages();
        }
        if (result instanceof Map<?, ?> map) {
            if (map.containsKey("totalPages")) {
                return "totalPages " + map.get("totalPages");
            }
            return "map size " + map.size();
        }
        if (result instanceof Collection) {
            return "collection size " + ((Collection<?>) result).size();
        }
        if (result instanceof String str) {
            int threshold = 100;
            return str.length() > threshold ? "response length " + str.length() : str;
        }
        return result;
    }
}
