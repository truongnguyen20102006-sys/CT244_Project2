package com.example.studentcrud.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

    @Test
    void newStudentShouldHaveDefaultValues() {
        Student student = new Student();

        assertThat(student.getRollNo()).isNull();
        assertThat(student.getName()).isNull();
        assertThat(student.getSemester()).isZero();
        assertThat(student.getCgpa()).isZero();
        assertThat(student.getPhoneNumber()).isNull();
    }

    @Test
    void gettersAndSettersShouldStoreAllStudentData() {
        Student student = new Student();
        student.setRollNo("SV001");
        student.setName("Nguyen Van A");
        student.setSemester(5);
        student.setCgpa(3.75);
        student.setPhoneNumber("0901234567");

        assertThat(student.getRollNo()).isEqualTo("SV001");
        assertThat(student.getName()).isEqualTo("Nguyen Van A");
        assertThat(student.getSemester()).isEqualTo(5);
        assertThat(student.getCgpa()).isEqualTo(3.75);
        assertThat(student.getPhoneNumber()).isEqualTo("0901234567");
    }
}
