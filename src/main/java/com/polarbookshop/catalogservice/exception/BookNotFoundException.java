package com.polarbookshop.catalogservice.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String isbn) {
        super("Книга с ISBN " + isbn + " не найдено.");
    }

}
