package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveCourse(@RequestBody Course course){
        Course newCourse = courseService.createCourse(course, response);
        courseService.setResponse(response);
        if (newCourse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        List<Course> courseList = courseService.viewCourse();
        if (courseList.isEmpty()){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(courseList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatedCourse(@PathVariable Long id, @RequestBody Course course){
        Course newCourse = courseService.updateCourse(id, course, response);
        courseService.setResponse(response);
        if (newCourse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletedCourse(@PathVariable Long id){
        Course newCourse = courseService.softDelete(id, response);
        courseService.setResponse(response);
        if (newCourse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/quizes/{id}")
    public ResponseEntity addQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        boolean newCourse = courseService.addQuizToCourse(id, quiz, response);
        courseService.setResponse(response);
        if (newCourse){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
