package com.example.studentREST;

import com.example.studentREST.entities.Student;
import com.example.studentREST.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        // Add test data
        studentRepository.save(new Student(null, "Alice", 10, 15, null));
        studentRepository.save(new Student(null, "Bob", 10, 16, null));
        studentRepository.save(new Student(null, "Charlie", 11, 14, null));
    }

    @Test
    void testFindClassNumberAndStudentCount() {
        List<Object[]> result = studentRepository.findClassNumberAndStudentCount();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);

        Object[] class10 = result.get(0);
        assertThat(class10[0]).isEqualTo(10);
        assertThat(class10[1]).isEqualTo(2L);

        Object[] class11 = result.get(1);
        assertThat(class11[0]).isEqualTo(11);
        assertThat(class11[1]).isEqualTo(1L);
    }
}
