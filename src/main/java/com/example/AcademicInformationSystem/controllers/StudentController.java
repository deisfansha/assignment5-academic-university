package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.models.*;
import com.example.AcademicInformationSystem.services.StudentService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveStudent(@RequestBody Student student){
        Student newStudent = studentService.createStudent(student, response);
        studentService.setResponse(response);
        if (newStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        List<Student> studentList = studentService.viewStudent();
        if (studentList.isEmpty()){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(studentList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletedDepartment(@PathVariable Long id){
        Student newStudent = studentService.softDelete(id, response);
        studentService.setResponse(response);
        if (newStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/enroll")
    public ResponseEntity enrollInCourse(@RequestBody EnrollRequest enrollRequest) {
        boolean enroll = studentService.enrollStudentInCourse(enrollRequest, response);
        studentService.setResponse(response);
        if (enroll){
            response.setMessage("Success");
            response.setData(enrollRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/scores/{studentId}/{courseId}/{quizId}")
    public ResponseEntity createScore(@PathVariable Long studentId,  @PathVariable Long courseId, @PathVariable Long quizId, ScoreRequest scoreRequest) {
        boolean score = studentService.inputScores(studentId, courseId, quizId, scoreRequest, response);
        studentService.setResponse(response);
        if (score){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
