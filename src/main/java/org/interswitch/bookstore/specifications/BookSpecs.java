package org.interswitch.bookstore.specifications;


import org.interswitch.bookstore.entities.Book;

public class BookSpecs extends QueryToCriteria<Book> {
    public BookSpecs(String query) {
        super(query);
    }
}
