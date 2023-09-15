package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveQuiz(@RequestBody Quiz quiz){
        Quiz newQuiz = quizService.createQuiz(quiz, response);
        if (newQuiz == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        List<Quiz> QuizList = quizService.viewQuiz();
        if (QuizList == null){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(QuizList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/page")
    public ResponseEntity pageViewAll(@RequestParam int page, @RequestParam int limit){
        Page<Quiz> quizList = quizService.pageView(page, limit);
        if (quizList.isEmpty()){
            response.setMessage("Data Is Empty");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(quizList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatedQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        Quiz newQuiz = quizService.updateQuiz(id, quiz, response);
        if (newQuiz == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletedQuiz(@PathVariable Long id){
        Quiz newQuiz = quizService.softDelete(id, response);
        if (newQuiz == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
