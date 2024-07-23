package com.example.student_management.controllers;

import com.example.student_management.models.Student;
import com.example.student_management.services.IStudentService;
import com.example.student_management.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllStudents(ModelMap modelMap) {
        List<Student> students = studentService.getAllStudents();
        modelMap.addAttribute("students", studentService.getAllStudents());
        return "home";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createStudentForm(ModelMap model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute("student") Student student) {
        studentService.createStudent(student);
        return "redirect:/students";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editStudentForm(@PathVariable Long id, ModelMap model) {
        //model.addAttribute("id", id);
        model.addAttribute("student", studentService.getStudentById(id).get());
        return "edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateStudent(@PathVariable("id") Long id,
                                @ModelAttribute Student student,
                                ModelMap model) {

        Student existingStudent = studentService.getStudentById(id).orElse(null);
        if (existingStudent == null) {
            throw new InvalidParameterException("Can't find student with id:" + id);
        }

        existingStudent.setActive(student.isActive());
        existingStudent.setName(student.getName());
        existingStudent.setDateOfBirth(student.getDateOfBirth());

        studentService.updateStudent(existingStudent);
        return "redirect:/students";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}
