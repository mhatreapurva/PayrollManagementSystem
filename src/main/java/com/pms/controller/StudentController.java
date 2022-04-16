package com.pms.controller;

import com.pms.model.Student;
import com.pms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/")
    public List<Student> getAll(){
        return studentService.getAll();
    }
    @PostMapping("/")
    public Student addStudent(@RequestBody Student stu){
        return studentService.addStudent(stu);
    }

    @PutMapping("/")
    public Student updateStudent(@RequestBody Student stu){
        return studentService.update(stu);
    }

    @DeleteMapping("/{studentID}")
    public void deleteStudent(@PathVariable String studentID){
        studentService.deleteStudent(studentID);
    }


}
