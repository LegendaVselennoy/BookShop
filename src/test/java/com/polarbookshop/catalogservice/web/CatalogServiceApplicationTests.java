package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Загружает полный контекст Web-приложения Spring и контейнер сервлетов, прослушивающий случайный порт
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

    // Утилита для выполнения REST-вызовов для тестирования
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book(null, "1231231231", "Title", "Author", 9.90, "Polarsophia", null, null, 0);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()                                             // Отправляет запрос
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();    // Проверяет, что текст HTTP-ответа не равен NULL
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn()); // Проверяет, что созданный объект соответствует ожиданиям
                });
    }

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        var bookIsbn = "1231231230";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

//        webTestClient
//                .get()
//                .uri("/books/" + bookIsbn)
//                .exchange()
//                .expectStatus().is2xxSuccessful()
//                .expectBody(Book.class).value(actualBook -> {
//                    assertThat(actualBook).isNotNull();
//                    assertThat(actualBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
//                });
    }


//	@Test
//	void whenPutRequestThenBookUpdated() {
//		var bookIsbn = "1231231232";
//		var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
//		Book createdBook = webTestClient
//				.post()
//				.uri("/books")
//				.bodyValue(bookToCreate)
//				.exchange()
//				.expectStatus().isCreated()
//				.expectBody(Book.class).value(book -> assertThat(book).isNotNull())
//				.returnResult().getResponseBody();
//		createdBook.setPrice(7.95);
//
//		webTestClient
//				.put()
//				.uri("/books/" + bookIsbn)
//				.bodyValue(createdBook)
//				.exchange()
//				.expectStatus().isOk()
//				.expectBody(Book.class).value(actualBook -> {
//					assertThat(actualBook).isNotNull();
//					assertThat(actualBook.getPrice()).isEqualTo(createdBook.getPrice());
//				});
//	}

    @Test
    void whenDeleteRequestThenBookDeleted() {
        var bookIsbn = "1231231233";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errorMessage ->
                        assertThat(errorMessage).isEqualTo("The book with ISBN " + bookIsbn + " was not found.")
                );
    }


}
