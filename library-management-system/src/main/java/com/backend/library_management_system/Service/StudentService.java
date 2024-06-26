package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.StudentResponseDto;
import com.backend.library_management_system.DTO.StudentUpdateEmailRequestDto;
import com.backend.library_management_system.Entity.LibraryCard;
import com.backend.library_management_system.Entity.Student;
import com.backend.library_management_system.Enum.CardStatus;
import com.backend.library_management_system.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String findByEmail(String email){

        Student student = studentRepository.findByEmail(email);
        return student.getName();
    }

    public List<Student> findByAge(int age){

        List<Student> list = studentRepository.findByAge(age);
        return list;
    }

    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        Student updatedStudent = studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }

    public String deleteStudent(int id){

         Student student = studentRepository.findById(id).get();
          studentRepository.deleteById(id);

         return student.getName() + " " + "is deleted.";
    }

}
