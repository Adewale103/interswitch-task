package org.interswitch.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponse {
    private String title;
    private String description;
    private int isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private String reference;
    private String url;

}
