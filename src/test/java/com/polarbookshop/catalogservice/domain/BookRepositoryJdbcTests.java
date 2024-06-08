package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Определяет тестовый класс, ориентированный на JDBC-компоненты Spring Data
 */
@DataJdbcTest
@Import(DataConfig.class) // Импортирует конфигурацию данных (необходимо для включения аудита)
/**
 * Отключает поведение по умолчанию, полагающееся на встроенную тестовую базу данных,
 * так как мы хотим использовать Testcontainers
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("application-integration") // Позволяет профилю «интеграции» загружать конфигурацию из applicationintegration.yml
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate; // Объект более низкого уровня для взаимодействия с базой данных

    @Test
    void findBookByIsbnWhenExisting() {

        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", 12.90,"Polarsophia");

        jdbcAggregateTemplate.insert(book);
        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());

    }
}
