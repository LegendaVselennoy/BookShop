package com.polarbookshop.catalogservice.domain;

/**
 * Модель предметной области реализована как запись, неизменяемый объект.
 * @param isbn Однозначная идентификация книги.
 */
public record Book(
    String isbn,
    String title,
    String author,
    Double price
) {}
