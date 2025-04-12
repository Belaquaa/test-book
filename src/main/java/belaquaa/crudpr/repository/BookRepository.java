package belaquaa.crudpr.repository;

import belaquaa.crudpr.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books b " +
            "WHERE (:title IS NULL OR b.title ILIKE CONCAT('%', :title, '%')) " +
            "AND (:brand IS NULL OR b.brand ILIKE CONCAT('%', :brand, '%')) " +
            "AND (:year IS NULL OR b.year = :year)",
            nativeQuery = true)
    Page<Book> searchBooks(@Param("title") String title,
                           @Param("brand") String brand,
                           @Param("year") Integer year,
                           Pageable pageable);


}
