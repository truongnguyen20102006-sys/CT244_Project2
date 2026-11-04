package com.example.studentcrud.repository;

import com.example.studentcrud.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldFindNoStudentWhenRepositoryIsEmpty() {
        assertThat(repository.findAll()).isEmpty();
        assertThat(repository.findById("missing")).isEmpty();
        assertThat(repository.existsById("missing")).isFalse();
    }

    @Test
    void shouldUpdateStudentWithSameRollNo() {
        Student student = createStudent("SV001", "Nguyen Van A");
        repository.save(student);

        Student updated = createStudent("SV001", "Nguyen Van B");
        repository.save(updated);

        assertThat(repository.findAll()).hasSize(1);
        assertThat(repository.findById("SV001").orElseThrow().getName())
                .isEqualTo("Nguyen Van B");
    }

    @Test
    void shouldSaveFindUpdateAndDeleteStudent() {
        Student student = createStudent("SV001", "Nguyen Van A");

        repository.save(student);

        Optional<Student> found = repository.findById("SV001");
        assertThat(found).isPresent();
        assertThat(found.orElseThrow().getName()).isEqualTo("Nguyen Van A");

        student.setName("Nguyen Van B");
        repository.save(student);
        assertThat(repository.findById("SV001").orElseThrow().getName()).isEqualTo("Nguyen Van B");

        List<Student> students = repository.findAll();
        assertThat(students).hasSize(1);

        repository.deleteById("SV001");
        assertThat(repository.findById("SV001")).isEmpty();
    }

    private Student createStudent(String rollNo, String name) {
        Student student = new Student();
        student.setRollNo(rollNo);
        student.setName(name);
        student.setSemester(5);
        student.setCgpa(3.75);
        student.setPhoneNumber("0901234567");
        return student;
    }
}
