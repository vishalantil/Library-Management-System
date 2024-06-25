package com.backend.library_management_system.Service;

import com.backend.library_management_system.Entity.LibraryCard;
import com.backend.library_management_system.Entity.Student;
import com.backend.library_management_system.Enum.CardStatus;
import com.backend.library_management_system.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(Student student){

        // Set the values of card
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setValidtill("03/2025");
        card.setStudent(student);

        // Set the card attribute in student
        student.setCard(card);

        studentRepository.save(student);
    }
}
