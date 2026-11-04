package com.example.studentcrud.controller;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository repository;

    @Test
    void homeShouldReturnIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void addStudentShouldSaveAndShowSuccessMessage() throws Exception {
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);

        mockMvc.perform(post("/add")
                        .param("rollNo", "SV001")
                        .param("name", "Nguyen Van A")
                        .param("semester", "5")
                        .param("cgpa", "3.75")
                        .param("phoneNumber", "0901234567"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Student Added!"));

        verify(repository).save(captor.capture());
        Student saved = captor.getValue();
        assertThat(saved.getRollNo()).isEqualTo("SV001");
        assertThat(saved.getName()).isEqualTo("Nguyen Van A");
        assertThat(saved.getSemester()).isEqualTo(5);
        assertThat(saved.getCgpa()).isEqualTo(3.75);
        assertThat(saved.getPhoneNumber()).isEqualTo("0901234567");
    }

    @Test
    void deleteStudentShouldDeleteAndShowSuccessMessage() throws Exception {
        mockMvc.perform(post("/delete").param("rollNo", "SV001"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Student Deleted!"));

        verify(repository).deleteById("SV001");
    }

    @Test
    void editStudentShouldReturnEditViewWhenStudentExists() throws Exception {
        Student student = createStudent();
        when(repository.findById("SV001")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/edit/SV001"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("student", student));
    }

    @Test
    void editStudentShouldReturnIndexWhenStudentDoesNotExist() throws Exception {
        when(repository.findById("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/edit/missing"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Student not found!"));
    }

    @Test
    void updateStudentShouldSaveWhenStudentExists() throws Exception {
        when(repository.existsById("SV001")).thenReturn(true);
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);

        mockMvc.perform(post("/update")
                        .param("rollNo", "SV001")
                        .param("name", "Nguyen Van A")
                        .param("semester", "6")
                        .param("cgpa", "3.80")
                        .param("phoneNumber", "0901234567"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Student Updated!"));

        verify(repository).save(captor.capture());
        Student updated = captor.getValue();
        assertThat(updated.getRollNo()).isEqualTo("SV001");
        assertThat(updated.getName()).isEqualTo("Nguyen Van A");
        assertThat(updated.getSemester()).isEqualTo(6);
        assertThat(updated.getCgpa()).isEqualTo(3.80);
        assertThat(updated.getPhoneNumber()).isEqualTo("0901234567");
    }

    @Test
    void updateStudentShouldReturnIndexWhenStudentDoesNotExist() throws Exception {
        when(repository.existsById("missing")).thenReturn(false);

        mockMvc.perform(post("/update").param("rollNo", "missing"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Student not found!"));

        verify(repository, never()).save(any(Student.class));
    }

    @Test
    void displayAllShouldReturnAllStudents() throws Exception {
        List<Student> students = List.of(createStudent());
        when(repository.findAll()).thenReturn(students);

        mockMvc.perform(get("/display"))
                .andExpect(status().isOk())
                .andExpect(view().name("display"))
                .andExpect(model().attribute("students", students));
    }

    @Test
    void displayByIdShouldReturnStudent() throws Exception {
        Student student = createStudent();
        when(repository.findById("SV001")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/display/SV001"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayOne"))
                .andExpect(model().attribute("student", student));
    }

    @Test
    void displayByIdShouldReturnNullWhenStudentDoesNotExist() throws Exception {
        when(repository.findById("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/display/missing"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayOne"))
                .andExpect(model().attributeDoesNotExist("student"));
    }

    private Student createStudent() {
        Student student = new Student();
        student.setRollNo("SV001");
        student.setName("Nguyen Van A");
        student.setSemester(5);
        student.setCgpa(3.75);
        student.setPhoneNumber("0901234567");
        return student;
    }
}
