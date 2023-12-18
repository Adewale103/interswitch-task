package org.interswitch.bookstore.service;


import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.repository.AuthorRepository;
import org.interswitch.bookstore.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    private AuthorRequest authorRequest;
    private Author savedAuthor;

    @BeforeEach
    void setUp() throws GenericException {
        authorRequest = new AuthorRequest();
        authorRequest.setFullName("Wale");
        authorRequest.setEmail("wale@gmail.com");
        authorRequest.setBio("dummy bio");

        savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setFullName("Ernest");
        savedAuthor.setEmail("ernest@gmail.com");
        savedAuthor.setBio("dummy bio");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        authorService = new AuthorServiceImpl(authorRepository);
        savedAuthor = authorService.createAuthor(authorRequest);
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void createAuthorTest() {
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        AuthorRequest authorRequest2 = new AuthorRequest();
        authorRequest2.setFullName("Kayode");
        authorRequest2.setEmail("kayode11@gmail.com");
        authorRequest2.setBio("dummy bio");
        authorService.createAuthor(authorRequest2);
        verify(authorRepository, times(2)).save(any(Author.class));
    }

    @Test
    void twoAuthorCannotExistWithSameEmailTest() {
        when(authorRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(savedAuthor));
        assertThrows(GenericException.class, () -> authorService.createAuthor(authorRequest));
        verify(authorRepository, times(2)).findByEmail(any(String.class));
    }


    @Test
    void deleteAuthorTest() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(savedAuthor));
        authorService.deleteAuthor(savedAuthor.getId());
        verify(authorRepository, times(1)).delete(savedAuthor);
    }

    @Test
    void updateAuthorTest() throws InvocationTargetException, IllegalAccessException {
        AuthorRequest updatedAuthor = new AuthorRequest();
        updatedAuthor.setFullName("Ahmed");
        updatedAuthor.setEmail("ahmed11@gmail.com");
        updatedAuthor.setBio("new bio");
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(savedAuthor));
        authorService.updateAuthor(1L, updatedAuthor);
        verify(authorRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(2)).save(any(Author.class));
    }
}

