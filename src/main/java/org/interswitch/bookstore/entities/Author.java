package org.interswitch.bookstore.entities;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author extends AppendableReference {
    @Column(nullable = false, length = 40)
    private String fullName;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String bio;
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
