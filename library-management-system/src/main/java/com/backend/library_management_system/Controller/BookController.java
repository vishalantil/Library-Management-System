package com.backend.library_management_system.Controller;

import com.backend.library_management_system.Entity.Book;
import com.backend.library_management_system.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book){

        try{
            bookService.addBooks(book);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage() + "Book not added.");
        }

        return "Book added Successfully.";
    }


}
