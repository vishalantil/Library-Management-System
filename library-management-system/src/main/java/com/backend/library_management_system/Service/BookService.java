package com.backend.library_management_system.Service;

import com.backend.library_management_system.Entity.Author;
import com.backend.library_management_system.Entity.Book;
import com.backend.library_management_system.Repository.AuthorRepository;
import com.backend.library_management_system.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public String addBooks(Book book) throws Exception {

        Author author;

        try {
            author = authorRepository.findById(book.getAuthor().getId()).get();
        }
        catch(Exception e){
            return "Book not added.";
        }

        List<Book> booksWritten = author.getBooks();
        booksWritten.add(book);

        authorRepository.save(author);
        return "Book added.";
    }


}
