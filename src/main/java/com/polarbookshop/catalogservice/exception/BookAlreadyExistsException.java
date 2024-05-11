package com.polarbookshop.catalogservice.exception;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(String isbn) {
        super("Книга с ISBN " + isbn + " уже существует.");
    }

}
