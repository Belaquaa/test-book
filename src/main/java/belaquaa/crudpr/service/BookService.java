package belaquaa.crudpr.service;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.dto.BookResponse;
import belaquaa.crudpr.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookResponse> listBooks(String title, String brand, Integer year, Pageable pageable);

    Book getBookById(Long id);

    Book createBook(BookRequest request);

    Book updateBook(Long id, BookRequest request);

    void deleteBook(Long id);
}
