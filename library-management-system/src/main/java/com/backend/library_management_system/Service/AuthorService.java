package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.AuthorRequestDto;
import com.backend.library_management_system.DTO.AuthorResponseDto;
import com.backend.library_management_system.Entity.Author;
import com.backend.library_management_system.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto){

        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setAge(authorRequestDto.getAge());
        author.setEmail(authorRequestDto.getEmail());
        author.setMobNo(authorRequestDto.getMobNo());

        authorRepository.save(author);

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setAge(author.getAge());
        authorResponseDto.setName(author.getName());

        return authorResponseDto;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
}
