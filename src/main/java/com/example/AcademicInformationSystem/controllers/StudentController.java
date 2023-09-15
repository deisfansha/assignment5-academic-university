package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.dto.request.DtoStudentRequest;
import com.example.AcademicInformationSystem.dto.response.DtoStudentResponse;
import com.example.AcademicInformationSystem.models.*;
import com.example.AcademicInformationSystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity saveStudent(@RequestBody DtoStudentRequest studentRequest){
        Student newStudent = studentService.createStudent(studentRequest, response);
        if (newStudent == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        List<DtoStudentResponse> studentList = studentService.viewStudent();
        if (studentList.isEmpty()){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(studentList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/page")
    public ResponseEntity pageViewAll(@RequestParam int page, @RequestParam int limit){
        Page<DtoStudentResponse> studentList = studentService.pageView(page, limit);
        if (studentList.isEmpty()){
            response.setMessage("Data Is Empty");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(studentList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody DtoStudentRequest studentRequest){
        Boolean updated = studentService.updateStudent(id, studentRequest, response);
        if (!updated){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletedDepartment(@PathVariable Long id){
        Student newStudent = studentService.softDelete(id, response);
        if (newStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
