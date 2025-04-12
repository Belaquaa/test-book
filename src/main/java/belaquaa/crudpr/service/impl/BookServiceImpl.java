package belaquaa.crudpr.service.impl;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.entity.Book;
import belaquaa.crudpr.exception.ResourceNotFoundException;
import belaquaa.crudpr.mapper.BookMapper;
import belaquaa.crudpr.repository.BookRepository;
import belaquaa.crudpr.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<Book> listBooks(String title, String brand, Integer year, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.searchBooks(title, brand, year, pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга не найдена"));
    }

    @Override
    public Book createBook(BookRequest request) {
        Book book = bookMapper.toBook(request);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга не найдена"));
        bookMapper.updateBookFromRequest(request, book);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Книга не найдена");
        }
        bookRepository.deleteById(id);
    }
}
