package com.example.studentREST.controllers;

import com.example.studentREST.entities.Address;
import com.example.studentREST.entities.Student;
import com.example.studentREST.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.*;
import java.util.Map;
import java.util.Optional;


@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/students/dev")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping(path = "/students/dev")
    public String addStudent(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                errors.add(errorMessage);
            });
            return String.join(", ", errors); // You can customize this to your needs
        }

        studentService.addStudent(student);
        return "Saved Successfully";
    }
    @GetMapping(path = "/students/dev/{id}")
    public Optional<Student> getStudentByID(@PathVariable Long id){

        return studentService.getStudentByID(id);
    }

    @PostMapping(path = "/students/dev/{id}")
    public String update(@PathVariable Long id, @Valid @RequestBody Student student, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                errors.add(errorMessage);
            });
            return String.join(", ", errors); // You can customize this to your needs
        }

        studentService.updateStudent(id, student);
        return "Student Updated Successfully";
    }
    @GetMapping(path = "/students/dev/report")
    public Map<Integer, Integer> ReportGenereation(){
        return studentService.report();
    }

    @GetMapping(path = "/students/dev/report/paginated")
    public Map<Integer, Integer> getPaginatedResults(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size){

        return studentService.paginatedReport(page, size);
    }


    @DeleteMapping(path = "/students/dev/{id}")
    public boolean delete(@PathVariable Long id){

        return studentService.deleteStudent(id);
    }
}
