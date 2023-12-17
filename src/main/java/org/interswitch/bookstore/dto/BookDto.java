package org.interswitch.bookstore.dto;

import lombok.*;
import org.interswitch.bookstore.entities.Book;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String description;
    private int isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private String reference;
    private String url;
    private List<AuthorDto> authors;

    public static BookDto fromModel(Book book){
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setAuthors(book.getAuthors().stream().map(author -> {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            return authorDto;
        }).toList());
        return bookDto;
    }

    public static BookDto toDto(Object o){
        return fromModel((Book) o);
    }

}
