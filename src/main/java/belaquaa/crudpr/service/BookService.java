package belaquaa.crudpr.service;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<Book> listBooks(String title, String brand, Integer year, int page, int size);

    Book getBookById(Long id);

    Book createBook(BookRequest request);

    Book updateBook(Long id, BookRequest request);

    void deleteBook(Long id);
}
