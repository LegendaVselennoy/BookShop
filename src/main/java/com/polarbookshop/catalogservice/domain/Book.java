package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

/**
 * Модель предметной области реализована как запись, неизменяемый объект.
 *
 * @param isbn Однозначная идентификация книги.
 */
//@Getter
public record Book(

        @Id
        Long id,

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
        Double price,

        String publisher,

        @CreatedDate
        Instant createdDate,  // Когда была создана сущность

        @LastModifiedDate
        Instant lastModifiedDate,  // Дата последнего изменения сущности

        @Version
        int version
) {
        public static Book of(
                String isbn, String title, String author, Double price,String publisher) {
                return new Book(null, isbn, title, author, price, publisher,null, null, 0);
        }
}
