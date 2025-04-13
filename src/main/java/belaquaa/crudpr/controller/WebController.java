package belaquaa.crudpr.controller;

import belaquaa.crudpr.dto.BookResponse;
import belaquaa.crudpr.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final BookService bookService;

    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Неправильное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы вышли из системы");
        }
        return "login";
    }

    @GetMapping("/books")
    public String booksPage(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String brand,
                            @RequestParam(required = false) Integer year,
                            Pageable pageable,
                            Model model) {
        Page<BookResponse> bookPage = bookService.listBooks(title, brand, year, pageable);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("titleFilter", title);
        model.addAttribute("brandFilter", brand);
        model.addAttribute("yearFilter", year);
        return "books";
    }
}
