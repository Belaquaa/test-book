package belaquaa.crudpr.controller;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.dto.BookResponse;
import belaquaa.crudpr.entity.Book;
import belaquaa.crudpr.mapper.BookMapper;
import belaquaa.crudpr.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Operation(summary = "Получить список книг с фильтрацией и пагинацией")
    @GetMapping
    public Page<BookResponse> listBooks(@RequestParam(required = false) String title,
                                        @RequestParam(required = false) String brand,
                                        @RequestParam(required = false) Integer year,
                                        Pageable pageable) {
        return bookService.listBooks(title, brand, year, pageable);
    }

    @Operation(summary = "Создать новую книгу")
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        Book created = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toBookResponse(created));
    }

    @Operation(summary = "Обновить данные книги")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        Book updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(bookMapper.toBookResponse(updated));
    }

    @Operation(summary = "Удалить книгу")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
