package belaquaa.crudpr.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Username не может быть пустым")
    private String username;

    @NotBlank(message = "Password не может быть пустым")
    private String password;
}