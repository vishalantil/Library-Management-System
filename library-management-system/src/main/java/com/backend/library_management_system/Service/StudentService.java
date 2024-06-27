package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.StudentRequestDto;
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

    public void addStudent(StudentRequestDto studentRequestDto){

        // Set the values of card
        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setDepartment(studentRequestDto.getDepartment());

        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);

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
