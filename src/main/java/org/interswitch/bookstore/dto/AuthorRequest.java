package org.interswitch.bookstore.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthorRequest {
    private String fullName;
    @Email
    private String email;
    private String bio;

}
