package org.interswitch.bookstore.entities;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.interswitch.bookstore.enums.Genre;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends AppendableReference {
    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    private String isbn;
    @Column(nullable = false)
    @Min(1800)
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDate dateUploaded;
    private String url;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    private String fileName;

    @UpdateTimestamp
    private Date updatedAt;
}
