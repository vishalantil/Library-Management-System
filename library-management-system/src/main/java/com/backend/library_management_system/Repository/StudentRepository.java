package com.backend.library_management_system.Repository;

import com.backend.library_management_system.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}