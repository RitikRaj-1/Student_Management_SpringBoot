package com.example.studentREST.services;

import com.example.studentREST.exceptions.StudentNotFoundException;
import com.example.studentREST.repositories.StudentRepository;
import com.example.studentREST.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> getAllStudents(){
        return repo.findAll();
    }

    public Optional<Student> getStudentByID(Long id){
        Optional<Student> byId = repo.findById(id);
        if(byId.isPresent()) return byId;
        else throw new StudentNotFoundException("Student " + id + " is not found");
    }

        public String addStudent(Student student){
        repo.save(student);
        return "Saved Succesfully";
    }

    public String updateStudent(Long id, Student student){
        Optional<Student> tempstudent = repo.findById(id);

        if(tempstudent.isPresent()){
            Student updateStudent = tempstudent.get();
            updateStudent.setName(student.getName());
            updateStudent.setClassNumber(student.getClassNumber());
            updateStudent.setAge(student.getAge());
            updateStudent.setAddress(student.getAddress());
            repo.save(updateStudent);
            return "Student Updated Succesfully";
        }
        else throw new StudentNotFoundException("Student " + id + " not found");

    }

    public boolean deleteStudent(Long id){
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        else throw new StudentNotFoundException("Student " + id + " not found");
    }

    public Map<Integer, Integer> report() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Object[]> ls = repo.findClassNumberAndStudentCount();

        for (Object[] result : ls) {
            Integer classNumber = (Integer) result[0];
            Integer studentCount = ((Number) result[1]).intValue();
            map.put(classNumber, studentCount);
        }
        return map;
    }

    public Map<Integer, Integer> paginatedReport(int page, int size) {
        Map<Integer, Integer> studentCountByClass = report();


        Pageable pageable = PageRequest.of(page, size);

        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(studentCountByClass.entrySet());
        int totalEntries = entryList.size();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), totalEntries);

        Map<Integer, Integer> paginatedResult = new HashMap<>();

        for (int i = start; i < end; i++) {
            Map.Entry<Integer, Integer> entry = entryList.get(i);
            paginatedResult.put(entry.getKey(), entry.getValue());
        }
        return paginatedResult;
    }
}
