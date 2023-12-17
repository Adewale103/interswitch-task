package org.interswitch.bookstore.service;



import org.interswitch.bookstore.dto.*;
import org.interswitch.bookstore.dto.request.BookRequest;
import org.interswitch.bookstore.dto.response.PagedResponse;

import java.io.IOException;
import java.util.List;

public interface BookService {
    BookDto createBook(BookRequest bookRequest) throws IOException;
    void updateBook(long id, BookRequest bookRequest) throws IOException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(String query, int page, int size);
    void deleteBook(long id);
    List<BookDto> filterByAuthor(String reference);
}
