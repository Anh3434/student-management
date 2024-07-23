package com.example.student_management.services;

import com.example.student_management.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> getAllStudents();
    void createStudent(Student student);

    Optional<Student> getStudentById(Long id);

    void updateStudent(Student student);

    void deleteStudentById(Long id);
}
