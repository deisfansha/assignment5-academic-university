package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.models.CourseStudents;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Student;
import com.example.AcademicInformationSystem.services.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-students")
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;
    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveStudentCourse(@RequestBody CourseStudents courseStudents){
        Boolean added = courseStudentService.addStudentCourse(courseStudents, response);
        if (added){
            response.setData(courseStudents);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        if (courseStudentService.getAll() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Success", null));
        }else {
            response.setMessage("Success");
            response.setData(courseStudentService.getAll());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateActived(@PathVariable Long id, @RequestBody CourseStudents courseStudents){
        Boolean updated = courseStudentService.updateActive(id, courseStudents, response);
        if (!updated){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(courseStudentService.getById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
