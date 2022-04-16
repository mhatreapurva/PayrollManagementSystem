package com.pms.service;

import com.pms.model.Student;
import com.pms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student stu) {
        return studentRepository.insert(stu);
    }

    public Student update(Student stu) {
        return studentRepository.save(stu);
    }


    public void deleteStudent(String studentID) {
        studentRepository.deleteById(studentID);
    }
}
