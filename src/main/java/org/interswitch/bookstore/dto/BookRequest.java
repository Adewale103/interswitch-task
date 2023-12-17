package org.interswitch.bookstore.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.interswitch.bookstore.annotation.ValidGenre;
import org.interswitch.bookstore.annotation.ValidISBN;
import org.interswitch.bookstore.annotation.ValidTitle;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @ValidTitle
    private String title;
    @NotBlank
    private String description;
    @ValidISBN
    private String isbn;
    @NotBlank
    @Min(value = 1800, message = "Publication year can not be earlier than 1800")
    private int publicationYear;
    @Min(value = 1, message = "Price can not be less than 1")
    private BigDecimal price;
    private int quantity;
    private String dateOfUpload;
    @NotBlank
    private String authorList;
    private MultipartFile file;
    @ValidGenre
    private String genre;
}
