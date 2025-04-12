package belaquaa.crudpr.mapper;

import belaquaa.crudpr.dto.BookRequest;
import belaquaa.crudpr.dto.BookResponse;
import belaquaa.crudpr.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse toBookResponse(Book book);

    Book toBook(BookRequest bookRequest);

    @Mapping(target = "id", ignore = true)
    void updateBookFromRequest(BookRequest bookRequest, @MappingTarget Book book);
}
