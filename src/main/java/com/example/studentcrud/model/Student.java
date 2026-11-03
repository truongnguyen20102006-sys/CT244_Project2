package com.example.studentcrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @NotBlank(message = "Roll number cannot be empty")
    @Size(max = 20, message = "Roll number cannot exceed 20 characters")
    @Column(length = 20)
    private String rollNo;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Min(value = 1, message = "Semester must be greater than 0")
    private int semester;

    @DecimalMin(value = "0.0", message = "CGPA must be at least 0")
    @DecimalMax(value = "10.0", message = "CGPA cannot exceed 10")
    private double cgpa;

    @Pattern(regexp = "^$|^[0-9]{10,11}$",
            message = "Phone number must contain 10 or 11 digits")
    @Column(length = 11)
    private String phoneNumber;

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}