package com.example.studentREST;

import com.example.studentREST.entities.Address;
import com.example.studentREST.entities.Student;
import com.example.studentREST.exceptions.StudentNotFoundException;
import com.example.studentREST.repositories.StudentRepository;
import com.example.studentREST.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        List<Student> students = Arrays.asList(new Student(1L, "John", 10, 20, new Address()));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(students.size(), result.size());
        assertEquals(students.get(0).getName(), result.get(0).getName());
    }

    @Test
    void getStudentByID_ShouldReturnStudent_WhenStudentExists() {
        Student student = new Student(1L, "John", 10, 20, new Address());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentByID(1L);

        assertTrue(result.isPresent());
        assertEquals(student.getName(), result.get().getName());
    }

    @Test
    void getStudentByID_ShouldThrowException_WhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentByID(1L));
    }

    @Test
    void addStudent_ShouldSaveStudent() {
        Student student = new Student(1L, "John", 10, 20, new Address());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        String result = studentService.addStudent(student);

        assertEquals("Saved Succesfully", result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudent_ShouldUpdateStudent_WhenStudentExists() {
        Student existingStudent = new Student(1L, "John", 10, 20, new Address());
        Student updatedStudent = new Student(1L, "John Updated", 11, 21, new Address());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        String result = studentService.updateStudent(1L, updatedStudent);

        assertEquals("Student Updated Succesfully", result);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void deleteStudent_ShouldDeleteStudent_WhenStudentExists() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        boolean result = studentService.deleteStudent(1L);

        assertTrue(result);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void report_ShouldReturnClassCountReport() {
        List<Object[]> mockData = Arrays.asList(new Object[]{10, 5}, new Object[]{11, 3});
        when(studentRepository.findClassNumberAndStudentCount()).thenReturn(mockData);

        Map<Integer, Integer> report = studentService.report();

        assertEquals(2, report.size());
        assertEquals(5, report.get(10));
        assertEquals(3, report.get(11));
    }

    @Test
    void paginatedReport_ShouldReturnPaginatedClassCountReport() {
        Map<Integer, Integer> reportData = new HashMap<>();
        reportData.put(10, 5);
        reportData.put(11, 3);
        when(studentRepository.findClassNumberAndStudentCount()).thenReturn(Arrays.asList(new Object[]{10, 5}, new Object[]{11, 3}));

        Map<Integer, Integer> result = studentService.paginatedReport(0, 1);

        assertEquals(1, result.size());
    }
}
