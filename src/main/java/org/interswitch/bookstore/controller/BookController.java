package org.interswitch.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.annotation.ValidateMultipart;
import org.interswitch.bookstore.dto.*;
import org.interswitch.bookstore.service.BookService;
import org.interswitch.bookstore.utils.AppendableReferenceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/create", produces = "application/json")
    @ValidateMultipart(extensions = "pdf,docx,doc", maxSize = 52428800)
    public ResponseEntity<ResponseDto> createBook(@ModelAttribute BookRequest bookRequest) throws IOException {
        BookDto book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(book,"successful"), HttpStatus.CREATED);
    }


    @GetMapping(value = "/search-book", produces = "application/json")
    public ResponseEntity<ResponseDto> filter(@RequestParam(value = "q") String query,
                                                         @RequestParam int page,
                                                         @RequestParam int size) {
        PageDto pageDto = bookService.searchCriteria(query, page, size);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(pageDto,"successful"), HttpStatus.OK);
    }


    @GetMapping(value = "/filter/{reference}", produces = "application/json")
    public ResponseEntity<ResponseDto> filterBooksByAuthor(@PathVariable String reference) {
        List<BookDto> books = bookService.filterByAuthor(reference);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(books,"successful"), HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestParam String bookId, @ModelAttribute BookRequest bookRequest) throws IOException {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.updateBook(id, bookRequest);
    }


    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<ResponseDto> getAllBooks(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
            @RequestParam(value = "noOfItems", required = false, defaultValue = "1") String numberOfItems){
        PagedResponse pagedResponse = bookService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(pagedResponse, "successful"), HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(@RequestParam String bookId) {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.deleteBook(id);
    }

}
