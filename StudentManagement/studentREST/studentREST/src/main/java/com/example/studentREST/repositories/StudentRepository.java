package com.example.studentREST.repositories;

import com.example.studentREST.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    @Query("SELECT s.classNumber, COUNT(s) FROM Student s GROUP BY s.classNumber")
    List<Object[]> findClassNumberAndStudentCount();
}
