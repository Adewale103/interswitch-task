package org.interswitch.bookstore.service.impl;

import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.entities.Book;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.repository.AuthorRepository;
import org.interswitch.bookstore.utils.BeanUtilHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.service.AuthorService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.interswitch.bookstore.utils.Constants.ALREADY_EXIST;
import static org.interswitch.bookstore.utils.Constants.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;


    @Override
    public Author createAuthor(AuthorRequest authorRequest) {
        authorDoesNotExist(authorRequest.getEmail());
        Author author = new Author();
        BeanUtils.copyProperties(authorRequest, author);
        return authorRepository.save(author);
    }

    public void save(Author author){
        authorRepository.save(author);
    }

    public Optional<Author> findByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException {
        Author author = this.findAuthor(id);
        BeanUtilHelper.copyPropertiesIgnoreNull(updateAuthorRequest, author);
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(long id) {
        Author author = this.findAuthor(id);

        if (author != null) {
            // Remove the author's association from books
            for (Book book : author.getBooks()) {
                book.getAuthors().remove(author);
            }
            // Clear the author's association from books
            author.getBooks().clear();
            authorRepository.delete(author);
        }
    }

    private Author findAuthor(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void authorDoesNotExist(String email) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
        if(optionalAuthor.isPresent()){
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
    }

}
