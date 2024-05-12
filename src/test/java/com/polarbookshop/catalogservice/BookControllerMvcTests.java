package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.service.BookService;
import com.polarbookshop.catalogservice.web.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;

/**
 * Определяет тестовый класс, который фокусируется на компонентах Spring MVC, явно ориентируясь на BookController
 */
@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    // Служебный класс для тестирования веб-слоя в имитационной среде
    @Autowired
    private MockMvc mockMvc;

    // Добавляет макет BookService в контекст приложения Spring
    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "737373123940";
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);   // Определяет ожидаемое поведение макета компонента BookService
        mockMvc                                            // MockMvc используется для выполнения HTTP-запроса GET и проверки результата.
                .perform(get("/books" + isbn))
                .andExpect(status().isNotFound());         // Ожидает, что ответ будет иметь статус "404 Not Found"
    }

}
