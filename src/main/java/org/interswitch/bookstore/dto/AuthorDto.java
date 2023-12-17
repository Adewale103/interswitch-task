package org.interswitch.bookstore.dto;

import lombok.*;
import org.interswitch.bookstore.entities.Author;
import org.springframework.beans.BeanUtils;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private String fullName;
    private String email;
    private String bio;
    private String reference;


    public static AuthorDto fromModel(Author author){
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author,authorDto);
        return authorDto;
    }


    public static AuthorDto toDto(Object o) {
        return fromModel((Author) o);
    }
}
