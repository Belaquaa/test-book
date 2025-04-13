package belaquaa.crudpr.initializer;

import belaquaa.crudpr.entity.Book;
import belaquaa.crudpr.entity.Role;
import belaquaa.crudpr.entity.User;
import belaquaa.crudpr.repository.BookRepository;
import belaquaa.crudpr.repository.RoleRepository;
import belaquaa.crudpr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Инициализация данных начала работу.");

        if (roleRepository.count() == 0L) {
            log.info("Роли не найдены. Создаём роли ADMIN и USER...");
            Role roleUser = new Role();
            roleUser.setName("USER");
            roleRepository.save(roleUser);

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleRepository.save(roleAdmin);
            log.info("Роли успешно созданы.");
        } else {
            log.info("Роли уже существуют, пропускаем создание.");
        }

        if (userRepository.count() == 0L) {
            log.info("Пользователи не найдены. Создаём пользователей admin и user...");
            Role roleUser = roleRepository.findByName("USER").orElseThrow();
            Role roleAdmin = roleRepository.findByName("ADMIN").orElseThrow();

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("$2b$10$pZL9rroc0ceu/5XyvxnTmOBhnEZxQQ4UMr20v2J/dAsYqki.Vsi5q"); // <-- password: admin
            admin.setRoles(Set.of(roleAdmin, roleUser));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("$2b$10$M5JLAgfhKbHYPAV/cY1RduMUV/4mV99k/PuO5jKoEJO1uHddm1jIO"); // <-- password: userpass
            user.setRoles(Set.of(roleUser));
            userRepository.save(user);
            log.info("Пользователи admin и user успешно созданы.");
        } else {
            log.info("Пользователи уже существуют, пропускаем создание.");
        }

        if (bookRepository.count() == 0L) {
            log.info("Книги не найдены. Создаём книги...");
            bookRepository.save(new Book(null, "B1", "Spring in Action", 2018, "Manning", 5, 45.00));
            bookRepository.save(new Book(null, "B2", "Java Concurrency in Practice", 2006, "Addison-Wesley", 3, 39.99));
            bookRepository.save(new Book(null, "B3", "Clean Code", 2008, "Prentice Hall", 10, 49.99));
            bookRepository.save(new Book(null, "B4", "Effective Java", 2018, "Addison-Wesley", 7, 54.99));
            bookRepository.save(new Book(null, "B5", "Head First Java", 2005, "O’Reilly", 4, 42.00));
            bookRepository.save(new Book(null, "B6", "Java: The Complete Reference", 2019, "McGraw-Hill", 6, 59.99));
            bookRepository.save(new Book(null, "B7", "Spring Boot in Action", 2015, "Manning", 5, 44.99));
            bookRepository.save(new Book(null, "B8", "Modern Java in Action", 2019, "Manning", 8, 49.50));
            bookRepository.save(new Book(null, "B9", "Spring Microservices in Action", 2017, "Manning", 4, 47.50));
            bookRepository.save(new Book(null, "B10", "Java: A Beginner’s Guide", 2018, "McGraw-Hill", 9, 39.95));
            log.info("Книги успешно созданы.");
        } else {
            log.info("Книги уже существуют, пропускаем создание.");
        }

        log.info("Инициализация данных завершена.");
    }
}