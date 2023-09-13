package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.CourseStudents;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Score;
import com.example.AcademicInformationSystem.models.Student;
import com.example.AcademicInformationSystem.repositories.CourseStudentRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import com.example.AcademicInformationSystem.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private QuizRepository quizRepository;

    public Boolean addScore(Score score, Response response) {
        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findById(score.getCourseStudents().getId());
        Optional<Quiz> existingQuiz = quizRepository.findById(score.getQuiz().getId());

        if (!existingCourseStudent.isPresent() || !existingCourseStudent.get().getActive()){
            response.setMessage("Course Student Not Found");
            return false;
        } else if (!existingQuiz.isPresent()) {
            response.setMessage("Quiz Not Found");
            return false;
        } else if (score.getGrade()<1 || score.getGrade()>100) {
            response.setMessage("Grade format does not match");
            return false;
        }

        score.setCourseStudents(existingCourseStudent.get());
        score.setQuiz(existingQuiz.get());
        scoreRepository.save(score);
        return true;
    }

    public Boolean updateScore(Long id, Score score, Response response) {
        Optional<Score> existingScore = scoreRepository.findById(id);

        if (!existingScore.isPresent() || !existingScore.get().getDeleted()){
            response.setMessage("Score Quiz Not Found");
            return false;
        } else if (score.getGrade()<1 || score.getGrade()>100) {
            response.setMessage("Grade format does not match");
            return false;
        }

        existingScore.get().setGrade(score.getGrade());
        scoreRepository.save(existingScore.get());
        return true;
    }

    public Score getById(Long id){
        return scoreRepository.findById(id).get();
    }

    public List<Score> getAll(){
        return scoreRepository.findAll();
    }

}
