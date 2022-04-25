package com.book.bookmanage;

import com.book.bookmanage.controller.BookController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookmanageApplicationTestIT {

    @Autowired
    BookController bookController;
    @Test
    void contextLoads() {
        assertNotNull(bookController);
    }

}
