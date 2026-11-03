package com.example.studentcrud.controller;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student, Model model) {

        // Kiểm tra dữ liệu nhập
        if (student.getRollNo() == null || student.getRollNo().trim().isEmpty()
                || student.getName() == null || student.getName().trim().isEmpty()
                || student.getSemester() <= 0
                || student.getCgpa() < 0 || student.getCgpa() > 10
                || student.getPhoneNumber() == null
                || !student.getPhoneNumber().matches("\\d{10,11}")) {

            model.addAttribute("message", "Invalid student information!");
            return "index";
        }

        student.setRollNo(student.getRollNo().trim());
        student.setName(student.getName().trim());
        student.setPhoneNumber(student.getPhoneNumber().trim());

        // Kiểm tra mã sinh viên đã tồn tại
        if (repository.existsById(student.getRollNo())) {
            model.addAttribute("message", "Roll number already exists!");
            return "index";
        }

        repository.save(student);

        model.addAttribute("message", "Student Added!");
        return "index";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam String rollNo, Model model) {

        if (rollNo == null || rollNo.trim().isEmpty()
                || !repository.existsById(rollNo.trim())) {
            model.addAttribute("message", "Student not found!");
            return "index";
        }

        repository.deleteById(rollNo.trim());
        model.addAttribute("message", "Student Deleted!");
        return "index";
    }

    @GetMapping("/edit/{rollNo}")
    public String editStudent(@PathVariable String rollNo, Model model) {
        Student student = repository.findById(rollNo).orElse(null);

        if (student == null) {
            model.addAttribute("message", "Student not found!");
            return "index";
        }

        model.addAttribute("student", student);
        return "edit";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student, Model model) {

        if (student.getRollNo() == null || student.getRollNo().trim().isEmpty()
                || student.getName() == null || student.getName().trim().isEmpty()
                || student.getSemester() <= 0
                || student.getCgpa() < 0 || student.getCgpa() > 10
                || student.getPhoneNumber() == null
                || !student.getPhoneNumber().matches("\\d{10,11}")) {

            model.addAttribute("message", "Invalid student information!");
            return "index";
        }

        student.setRollNo(student.getRollNo().trim());
        student.setName(student.getName().trim());
        student.setPhoneNumber(student.getPhoneNumber().trim());

        if (!repository.existsById(student.getRollNo())) {
            model.addAttribute("message", "Student not found!");
            return "index";
        }

        repository.save(student);

        model.addAttribute("message", "Student Updated!");
        return "index";
    }

    @GetMapping("/display")
    public String displayAll(Model model) {
        model.addAttribute("students", repository.findAll());
        return "display";
    }

    @GetMapping("/display/{rollNo}")
    public String displayById(@PathVariable String rollNo, Model model) {

        Student student = repository.findById(rollNo).orElse(null);

        if (student == null) {
            model.addAttribute("message", "Student not found!");
            return "displayOne";
        }

        model.addAttribute("student", student);
        return "displayOne";
    }
}