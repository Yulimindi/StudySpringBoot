package com.group.libraryapp.controller.book;

import com.group.libraryapp.service.user.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public void saveBood() {
        bookService.saveBook();
    }
}
