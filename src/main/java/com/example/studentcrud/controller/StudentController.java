
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
        repository.save(student);
        model.addAttribute("message", "Student Added!");
        return "index";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam String rollNo, Model model) {
        repository.deleteById(rollNo);
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
        model.addAttribute("student", repository.findById(rollNo).orElse(null));
        return "displayOne";
    }
}
