package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.dto.request.DtoScoreRequest;
import com.example.AcademicInformationSystem.dto.response.DtoScoreResponse;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveStudentScore(@RequestBody DtoScoreRequest scores){
        Boolean added = scoreService.addScore(scores, response);
        if (added){
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateActived(@PathVariable Long id, @RequestBody DtoScoreRequest scoreRequest){
        Boolean updated = scoreService.updateScore(id, scoreRequest, response);
        if (!updated){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        if (scoreService.getAll() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Data Is Empty", null));
        }else {
            response.setMessage("Success");
            response.setData(scoreService.getAll());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/page")
    public ResponseEntity pageViewAll(@RequestParam int page, @RequestParam int limit){
        Page<DtoScoreResponse> scoreList = scoreService.pageView(page, limit);
        if (scoreList.isEmpty()){
            response.setMessage("Data Is Empty");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(scoreList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
