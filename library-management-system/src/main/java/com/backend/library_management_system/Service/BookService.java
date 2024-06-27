package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.BookRequestDto;
import com.backend.library_management_system.DTO.BookResponseDto;
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

    public BookResponseDto addBooks(BookRequestDto bookRequestDto){

          Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

          Book book = new Book();
          book.setTitle(bookRequestDto.getTitle());
          book.setGenre(bookRequestDto.getGenre());
          book.setPrice(bookRequestDto.getPrice());
          book.setIsIssued(false);
          book.setAuthor(author);

          author.getBooks().add(book);

          authorRepository.save(author);

          BookResponseDto bookResponseDto = new BookResponseDto();
          bookResponseDto.setPrice(book.getPrice());
          bookResponseDto.setTitle(book.getTitle());

          return bookResponseDto;
    }


}
