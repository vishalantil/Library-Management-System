package com.backend.library_management_system.Controller;

import com.backend.library_management_system.DTO.StudentRequestDto;
import com.backend.library_management_system.DTO.StudentResponseDto;
import com.backend.library_management_system.DTO.StudentUpdateEmailRequestDto;
import com.backend.library_management_system.Entity.Student;
import com.backend.library_management_system.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDto){
        studentService.addStudent(studentRequestDto);
        return "Student has been added";
    }

    @GetMapping("/find_by_email")
    // Issue in the postman
    public String findStudentByEmail(@RequestParam("email") String email){
        return studentService.findByEmail(email);
    }

    @GetMapping("/find_by_age")
    public List<Student> findByAge(@RequestParam int age){
        return studentService.findByAge(age);
    }

    @PutMapping("/update_email")
    public StudentResponseDto updateEmail(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        return studentService.updateEmail(studentUpdateEmailRequestDto);
    }

    @DeleteMapping("/delete")
    public String deleteStudent(@RequestParam int id){
        return studentService.deleteStudent(id);
    }
}
