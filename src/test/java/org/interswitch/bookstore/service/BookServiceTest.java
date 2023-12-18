package org.interswitch.bookstore.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.AuthorDto;
import org.interswitch.bookstore.dto.BookDto;
import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.dto.request.BookRequest;
import org.interswitch.bookstore.dto.response.AmazonResponse;
import org.interswitch.bookstore.dto.response.PagedResponse;
import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.entities.Book;
import org.interswitch.bookstore.enums.Genre;
import org.interswitch.bookstore.repository.BookRepository;
import org.interswitch.bookstore.service.impl.BookServiceImpl;
import org.interswitch.bookstore.service.storage.StorageService;
import org.interswitch.bookstore.service.storage.UploadObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    @Mock
    private StorageService storageService;

    private String bucketName = "bookBucket";
    @Mock
    private ObjectMapper objectMapper;
    private BookService bookService;
    private BookRequest bookRequest;
    private Book book;
    private Author author;
    private List<Author> authors;

    @BeforeEach
    void setUp() throws IOException {
        bookService  = new BookServiceImpl(bookRepository,authorService,storageService,bucketName,objectMapper);
        bookRequest = new BookRequest();
        bookRequest.setFile(new MockMultipartFile("test file",new ByteArrayInputStream(new byte[4])));
        bookRequest.setGenre(String.valueOf(Genre.FICTION));
        bookRequest.setIsbn("1222222");
        bookRequest.setDescription("Just to test");
        bookRequest.setPrice(BigDecimal.valueOf(2300));
        bookRequest.setTitle("fish");
        bookRequest.setAuthorList("[{\"fullName\": \"Doe\", \"email\": \"dewale@yahoo.com\", \"bio\": \"dummy bio\"}]");
        bookRequest.setPublicationYear(2022);
        bookRequest.setDateOfUpload("2023-08-12");
        authors = new ArrayList<>();
        author = new Author();
        author.setEmail("dewale@yahoo.com");
        author.setFullName("Doe");
        author.setBio("dummy bio");
        authors.add(author);


        book = new Book();
        book.setUrl("amzon.hdskwjhg.com");
        book.setFileName("PENtyudijhb");
        book.setGenre(Genre.FICTION);
        book.setIsbn("1222222");
        book.setDescription("Just to test");
        book.setPrice(BigDecimal.valueOf(1234));
        book.setTitle("fish");
        book.setAuthors(authors);
        book.setPublicationYear(2022);
        book.setDateUploaded(LocalDate.parse("2023-08-12"));

    }

    @Test
    public void testThatBookCanBeCreated() throws IOException {
        when(bookRepository.findBookByIsbn(any(String.class))).thenReturn(Optional.empty());
        AuthorDto authorDto = new AuthorDto();
        authorDto.setEmail("dewale@yahoo.com");
        authorDto.setFullName("Doe");
        authorDto.setBio("dummy bio");
        AmazonResponse amazonResponse = AmazonResponse.builder().url("amzon.hdskwjhg.com").fileName("PENtyudijhb").build();
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorDtos.add(authorDto);
        when(objectMapper.readValue(any(String.class), any(TypeReference.class)))
                .thenReturn(authorDtos);
        when(authorService.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(authorService.createAuthor(any(AuthorRequest.class))).thenReturn(author);
        when(storageService.uploadToBucket(any(InputStream.class),any(UploadObject.class))).thenReturn(amazonResponse);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.createBook(bookRequest);
        assertThat(bookDto.getTitle()).isEqualTo("fish");
        assertThat(bookDto.getIsbn()).isEqualTo("1222222");
    }

    @Test
    void findByAuthorTest() {
        when(authorService.findById(any(Long.class))).thenReturn(Optional.of(author));
        List<BookDto> bookDto = bookService.filterByAuthor("1_hLrFyzQ");
        assertThat(bookDto).isNotEqualTo(null);
    }

    @Test
    void findAllTest() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        PagedResponse result = bookService.findAll(0, 1);
        assertEquals(1, result.getPagedResponse().get("totalNumberOfPages"));
        assertEquals(1, result.getPagedResponse().get("size"));
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }

}
