package com.shreyas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }


    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void addStudents(@RequestBody Student studentdetails) {
        studentService.addStudents(studentdetails);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudents(@PathVariable("studentId") Integer id) {
        studentService.deleteStudents(id);
    }

    record NewStudentRequest(
            String name,
            String email,
            LocalDate dob,
            Integer age
    ) {

    }

    @PutMapping("{studentId}")
    public void updateStudents(@PathVariable("studentId") Integer id, @RequestBody NewStudentRequest updatestudent) {
        studentService.updateStudents(id, updatestudent);
    }
}
