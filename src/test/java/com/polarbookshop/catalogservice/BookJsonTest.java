package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Определяет тестовый класс, ориентированный на сериализацию JSON
 */
@JsonTest
public class BookJsonTest {

    // Служебный класс для подтверждения сериализации и десериализации JSON.
    @Autowired
    private JacksonTester<Book> json;

    // Проверка синтаксического анализа из Java в JSON с использованием формата JsonPath для навигации по объекту JSON
    @Test
    void testSerialize() throws Exception {

        var book = new Book(null,"1234567890", "Title", "Author", 9.90,"Polarsophia",null,null,0);
        var jsonContent = json.write(book);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    // Определяет объект JSON с помощью функции текстового блока Java
    @Test
    void testDeserialize() throws Exception {

        var content = """
                {
                  "isbn":"1234567890",
                  "title":"Title",
                  "author":"Author",
                  "price":9.90
                }
                """;

        // Проверяет синтаксический анализ из JSON в Java
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(null,"1234567890", "Title", "Author", 9.90,"Polarsophia",null,null,0));
    }
}
