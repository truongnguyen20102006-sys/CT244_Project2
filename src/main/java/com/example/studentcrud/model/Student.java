package com.example.studentcrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(length = 20) // Giới hạn độ dài mã số sinh viên (Ví dụ: SV12345) để tối ưu chỉ mục (Index) trong MySQL
    private String rollNo;

    @Column(nullable = false, length = 100) // Bắt buộc phải có tên sinh viên và giới hạn 100 ký tự
    private String name;

    private int semester;

    private double cgpa;

    @Column(length = 11) // Giới hạn độ dài số điện thoại
    private String phoneNumber;

    // Getters and Setters
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
