package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/**
 * Модель предметной области реализована как запись, неизменяемый объект.
 *
 * @param isbn Однозначная идентификация книги.
 */
public record Book(

        @NotBlank(message = "Книга ISBN должна быть определена.")
        @Pattern(
                regexp = "^(\\d{10}|[0-9]{13}$)",
                message = "Формат ISBN должен быть действительным."
        )
        String isbn,

        @NotBlank(
                message = "Название книги должно быть определено."
        )
        String title,

        @NotBlank(message = "Автор книги должен быть определен.")
        String author,

        @NotNull(message = "Цена книги должна быть определена.")
        @Positive(
                message = "Цена книги должна быть больше нуля."
        )
        Double price
) {
}
