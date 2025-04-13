package belaquaa.crudpr.service.impl;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.dto.BookResponse;
import belaquaa.crudpr.entity.Book;
import belaquaa.crudpr.exception.ResourceNotFoundException;
import belaquaa.crudpr.mapper.BookMapper;
import belaquaa.crudpr.repository.BookRepository;
import belaquaa.crudpr.repository.specification.BookSpecification;
import belaquaa.crudpr.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> listBooks(String title, String brand, Integer year, Pageable pageable) {
        Specification<Book> spec = Specification
                .where(BookSpecification.titleContains(title))
                .and(BookSpecification.brandContains(brand))
                .and(BookSpecification.yearEquals(year));

        Page<Book> pageResult = bookRepository.findAll(spec, pageable);
        return pageResult.map(bookMapper::toBookResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга не найдена"));
    }

    @Override
    @Transactional
    public Book createBook(BookRequest request) {
        Book book = bookMapper.toBook(request);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Книга не найдена"));
        bookMapper.updateBookFromRequest(request, book);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Книга не найдена");
        }
        bookRepository.deleteById(id);
    }
}
