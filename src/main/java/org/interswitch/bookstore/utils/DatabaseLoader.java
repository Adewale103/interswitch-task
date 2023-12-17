package org.interswitch.bookstore.utils;

import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.dto.request.BookRequest;
import org.interswitch.bookstore.dto.request.RegisterRequest;
import org.interswitch.bookstore.enums.Role;
import org.interswitch.bookstore.service.AuthenticationService;
import org.interswitch.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final BookService bookService;
    private final AuthenticationService authenticationService;


    @Override
    public void run(String... args) throws Exception {
        // Preloaded the database with ten books
        BookRequest book1 = createSampleBook("Game of Thrones", "It is a book about battle of kingdoms", "123-456-7890", 2022, BigDecimal.valueOf(2000), 20,
                "[{\"fullName\": \"Brian Doe\", \"email\": \"brian@yahoo.com\",\"bio\": \"Brian Doe is a writer\"}]",
                "2023-12-12", "FICTION"
        );
        BookRequest book2 = createSampleBook("House of Dragons", "It is a book about what happened before game of thrones", "987-654-3210", 2023, BigDecimal.valueOf(2410), 30,
                "[{\"fullName\": \"Jane Doe\", \"email\": \"jane.doe@gmail.com\",\"bio\": \"Author and poet\"}]",
                "2023-12-13", "FICTION"
        );
        BookRequest book3 = createSampleBook("The Lord of the Rings", "A classic fantasy novel", "111-222-3339", 1954, BigDecimal.valueOf(1500), 15,
                "[{\"fullName\": \"J.R.R. Tolkien\", \"email\": \"tolkien@gmail.com\",\"bio\": \"Legendary author\"}]",
                "2023-12-14", "MYSTERY"
        );

        BookRequest book4 = createSampleBook("To Kill a Mockingbird", "A novel by Harper Lee", "444-555-6666", 1960, BigDecimal.valueOf(1800), 25,
                "[{\"fullName\": \"Harper Lee\", \"email\": \"harper.lee@gmail.com\",\"bio\": \"Author of classic literature\"}]",
                "2023-12-15", "FICTION"
        );

        BookRequest book5 = createSampleBook("1984", "A dystopian novel by George Orwell", "777-888-9999", 1949, BigDecimal.valueOf(1200), 10,
                "[{\"fullName\": \"George Orwell\", \"email\": \"orwell@gmail.com\",\"bio\": \"Visionary writer\"}]",
                "2023-12-16", "POETRY"
        );

        BookRequest book6 = createSampleBook("The Great Gatsby", "A novel by F. Scott Fitzgerald", "111-222-3334", 1925, BigDecimal.valueOf(1350), 18,
                "[{\"fullName\": \"F. Scott Fitzgerald\", \"email\": \"fitzgerald@gmail.com\",\"bio\": \"Iconic American author\"}]",
                "2023-12-17", "HORROR"
        );

        BookRequest book7 = createSampleBook("Harry Potter and the Philosophers Stone", "The first book in the Harry Potter series", "444-555-6667", 1997, BigDecimal.valueOf(2200), 22,
                "[{\"fullName\": \"J.K. Rowling\", \"email\": \"jkrowling@gmail.com\",\"bio\": \"Wizarding World creator\"}]",
                "2023-12-18", "THRILLER"
        );

        BookRequest book8 = createSampleBook("The Catcher in the Rye", "A novel by J.D. Salinger", "777-888-9998", 1951, BigDecimal.valueOf(1600), 14,
                "[{\"fullName\": \"J.D. Salinger\", \"email\": \"salinger@gmail.com\",\"bio\": \"Reclusive literary figure\"}]",
                "2023-12-19", "FICTION"
        );

        BookRequest book9 = createSampleBook("The Catcher in the Rain", "A novel by J.D. Salinger", "777-888-4444", 1950, BigDecimal.valueOf(1600), 14,
                "[{\"fullName\": \"J.D. Salinger\", \"email\": \"salinger@gmail.com\",\"bio\": \"Reclusive literary figure\"}]",
                "2023-12-19", "POETRY"
        );
        BookRequest book10 = createSampleBook("The Lord of Blacks", "A classic fantasy novel", "111-222-3333", 1954, BigDecimal.valueOf(1500), 15,
                "[{\"fullName\": \"J.R.R. Tolkien\", \"email\": \"tolkien@gmail.com\",\"bio\": \"Legendary author\"}]",
                "2023-12-14", "HORROR"
        );
        authenticationService.register(createSampleUser());
        bookService.createBook(book1);
        bookService.createBook(book2);
        bookService.createBook(book3);
        bookService.createBook(book4);
        bookService.createBook(book5);
        bookService.createBook(book6);
        bookService.createBook(book7);
        bookService.createBook(book8);
        bookService.createBook(book9);
        bookService.createBook(book10);

    }

    private BookRequest createSampleBook(String title, String description, String isbn, int publicationYear,
                                         BigDecimal price, int quantity, String authorList, String dateOfUpload, String genre)  {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(title);
        bookRequest.setDescription(description);
        bookRequest.setIsbn(isbn);
        bookRequest.setPublicationYear(publicationYear);
        bookRequest.setPrice(price);
        bookRequest.setQuantity(quantity);
        bookRequest.setDateOfUpload(dateOfUpload);
        bookRequest.setAuthorList(authorList);
        bookRequest.setGenre(genre);
        bookRequest.setFile(null);
        return bookRequest;
    }


    private RegisterRequest createSampleUser() {
        RegisterRequest userRequest = new RegisterRequest();
        userRequest.setFirstName("Adewale");
        userRequest.setLastName("Adeyinka");
        userRequest.setEmail("ade@gmail.com");
        userRequest.setPassword("Adewale@01");
        userRequest.setRole(Role.ADMIN);
        return userRequest;
    }
}
