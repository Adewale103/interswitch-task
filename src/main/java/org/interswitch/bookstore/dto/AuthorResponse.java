package org.interswitch.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import org.interswitch.bookstore.entities.Author;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
public class AuthorResponse {
    private String fullName;
    private String email;
    private String reference;
    private String bio;
    private List<BookResponse> books;

    public static AuthorResponse fromModel(Author author){
      AuthorResponse authorResponse = new AuthorResponse();
        BeanUtils.copyProperties(author, authorResponse);
        authorResponse.setBooks(author.getBooks().stream().map(book -> {
            BookResponse bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            return bookResponse;
        }).toList());
        return authorResponse;
    }

    public static AuthorResponse toDto(Object o){
        return fromModel((Author) o);
    }
}
