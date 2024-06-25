package com.backend.library_management_system.Repository;

import com.backend.library_management_system.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {

}
