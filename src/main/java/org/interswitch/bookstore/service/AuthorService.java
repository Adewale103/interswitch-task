package org.interswitch.bookstore.service;



import org.interswitch.bookstore.dto.AuthorRequest;
import org.interswitch.bookstore.entities.Author;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface AuthorService {
    Author createAuthor(AuthorRequest author);
    void save(Author author);
    void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException;
    void deleteAuthor(long id);
    Optional<Author> findByEmail(String email);
    Optional<Author> findById(long id);

}
