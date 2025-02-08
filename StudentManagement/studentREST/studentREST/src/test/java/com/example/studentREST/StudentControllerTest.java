package com.example.studentREST;

import com.example.studentREST.controllers.StudentController;
import com.example.studentREST.entities.Address;
import com.example.studentREST.entities.Student;
import com.example.studentREST.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void getAllStudents_ShouldReturnStudentList() throws Exception {
        List<Student> students = Arrays.asList(new Student(1L, "John", 10, 20, new Address()));
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students/dev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John")));
    }

    @Test
    void addStudent_ShouldReturnSuccessMessage() throws Exception {
        Student student = new Student(1L, "John", 10, 20, new Address());
        when(studentService.addStudent(any(Student.class))).thenReturn("Saved Successfully");

        mockMvc.perform(post("/students/dev")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Updated\"," +
                                "\"classNumber\":11," +
                                "\"age\":21," +
                                "\"address\":" +
                                "{\"street\":\"123 Main St\"," +
                                "\"city\":\"Springfield\"," +
                                "\"pin\":\"627001\"," +
                                "\"state\":\"IL\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved Successfully"));
    }

    @Test
    void getStudentByID_ShouldReturnStudent_WhenStudentExists() throws Exception {
        Student student = new Student(1L, "John", 10, 20, new Address());
        when(studentService.getStudentByID(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/dev/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    void updateStudent_ShouldReturnUpdateMessage() throws Exception {
        when(studentService.updateStudent(any(Long.class), any(Student.class))).thenReturn("Student Updated Successfully");

        mockMvc.perform(post("/students/dev/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Updated\"," +
                                "\"classNumber\":11," +
                                "\"age\":21," +
                                "\"address\":" +
                                "{\"street\":\"123 Main St\"," +
                                "\"city\":\"Springfield\"," +
                                "\"pin\":\"627001\"," +
                                "\"state\":\"IL\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student Updated Successfully"));
    }

    @Test
    void reportGeneration_ShouldReturnClassCountReport() throws Exception {
        Map<Integer, Integer> reportData = new HashMap<>();
        reportData.put(10, 5);
        reportData.put(11, 3);
        when(studentService.report()).thenReturn(reportData);

        mockMvc.perform(get("/students/dev/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.10", is(5)))
                .andExpect(jsonPath("$.11", is(3)));
    }

    @Test
    void getPaginatedResults_ShouldReturnPaginatedClassCountReport() throws Exception {
        Map<Integer, Integer> paginatedData = new HashMap<>();
        paginatedData.put(10, 5);
        when(studentService.paginatedReport(0, 1)).thenReturn(paginatedData);

        mockMvc.perform(get("/students/dev/report/paginated")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.10", is(5)));
    }

    @Test
    void deleteStudent_ShouldReturnTrue_WhenStudentExists() throws Exception {
        when(studentService.deleteStudent(1L)).thenReturn(true);

        mockMvc.perform(delete("/students/dev/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
