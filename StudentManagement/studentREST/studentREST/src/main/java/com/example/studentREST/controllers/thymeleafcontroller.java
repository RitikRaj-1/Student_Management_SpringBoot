package com.example.studentREST.controllers;

import com.example.studentREST.entities.Address;
import com.example.studentREST.entities.Student;
import com.example.studentREST.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class thymeleafcontroller {

    @Autowired
    private StudentService studentService;
    @PostMapping(path = "/students/{id}")
    public String editStudent(@PathVariable Long id,
                              @ModelAttribute("student") Student student,
                              Model model){
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping(path = "/students")
    public String listStudents(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }


    @GetMapping(path = "/students/addstudent")
    public String newStudent(Model model){
        Student student = new Student();
        student.setAddress(new Address());  // Initialize an empty address for the form
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping(path = "/students")
    public String addStudent(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError("classNumber") != null) {
                if(bindingResult.getFieldError("classNumber").getCode().equals("typeMismatch"))
                    model.addAttribute("classNumberError", "Class number must be a valid integer.");
            }
            if (bindingResult.getFieldError("age") != null) {
                if(bindingResult.getFieldError("age").getCode().equals("typeMismatch"))
                    model.addAttribute("ageError", "Age must be a valid integer.");
            }            model.addAttribute("student", student);
            return "create_student";
        }

        studentService.addStudent(student);
        return "redirect:/students";
    }

    @PostMapping(path = "/students/update/{id}")
    public String updateStudent(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError("classNumber") != null) {
                if(bindingResult.getFieldError("classNumber").getCode().equals("typeMismatch"))
                    model.addAttribute("classNumberError", "Class number must be a valid integer.");
            }
            if (bindingResult.getFieldError("age") != null) {
                if(bindingResult.getFieldError("age").getCode().equals("typeMismatch"))
                    model.addAttribute("ageError", "Age must be a valid integer.");
            }            model.addAttribute("student", student);
            return "edit_student";
        }

        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping(path = "/students/edit/{id}")
    public String update(@PathVariable Long id, Model model) {
        Optional<Student> optionalStudent = studentService.getStudentByID(id);
        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            return "edit_student";
        } else {
            return "redirect:/students";
        }
    }

    @GetMapping(path="/students/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model){
        Optional<Student> optionalStudent = studentService.getStudentByID(id);
        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            return "view_student";
        } else {
            return "redirect:/students";
        }

    }

    @GetMapping(path = "/students/{id}")
    public String delete(@PathVariable Long id){

        studentService.deleteStudent(id);
        return "redirect:/students";
    }


    @GetMapping(path="/students/report")
    public String report(Model model) {
        Map<Integer, Integer> classReport = studentService.report();
        model.addAttribute("classReport", classReport);
        return "student_report";
    }
    @GetMapping(path = "/students/report/paginated")
    public String paginatedReport(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            Model model) {

        Map<Integer, Integer> paginatedReport = studentService.paginatedReport(page-1, size);
        model.addAttribute("classReport", paginatedReport);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("hasNextPage", paginatedReport.size() == size);

        return "student_report_paginated";
    }

}
