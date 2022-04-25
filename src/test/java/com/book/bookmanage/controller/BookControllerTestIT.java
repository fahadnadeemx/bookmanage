package com.book.bookmanage.controller;

import com.book.bookmanage.entity.Book;
import com.book.bookmanage.repository.Bookrepository;
import com.book.bookmanage.service.BookService;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.port;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private Bookrepository bookrepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setup() {
        RestAssured.port = port;
        // always start with an empty database
        bookrepository.deleteAll();
        bookrepository.flush();

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }


    @Test
    public void test_createNewBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookname("Marvels");
        book.setAuthor("Fahad Nadeem");
        book.setPrice(1000);
        String url = "/books/save";
        final String baseUrl = "http://localhost:8080/books/save";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<Book> request = new HttpEntity<>(book, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Optional<Book> ex = bookrepository.findById(book.getId());
        assertEquals(book.getBookname(), ex.get().getBookname());

    }

    @Test
    public void test_updateBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookname("Marvels");
        book.setAuthor("Fahad Nadeem");
        book.setPrice(1000);

        List<Book> allBooks= List.of(book);

        bookrepository.saveAll(allBooks);

        Book book2= book;
        book2.setBookname("updated");
        book2.setAuthor("updated");
        book2.setPrice(200);


        String url = "/books/update/" + book2.getId();
        final String baseUrl = "http://localhost:8080/books/update/" + book2.getId() ;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<Book> request = new HttpEntity<>(book2, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Optional<Book> ex = bookrepository.findById(book2.getId());
        assertEquals(book2.getBookname(), ex.get().getBookname());

    }

    @Test
    public void test_getallbooks() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookname("Marvels");
        book.setAuthor("Fahad Nadeem");
        book.setPrice(1000);
        List<Book> allBooks= List.of(book);

        bookrepository.saveAll(allBooks);
        // when
        when().get("/books");

        assertEquals(1, bookrepository.findAll().size());

    }
    @Test
    public void test_deleteCategory() throws Exception {

        // given
        Book book = new Book();
        book.setId(1);
        book.setBookname("Marvels");
        book.setAuthor("Fahad Nadeem");
        book.setPrice(1000);

        List<Book> allBooks= List.of(book);

        bookrepository.saveAll(allBooks);

        // when
        when().delete("/books/delete/" + book.getId());

        assertEquals(1, bookrepository.findAll().size());

    }



}
