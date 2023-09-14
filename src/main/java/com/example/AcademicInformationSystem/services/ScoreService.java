package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.dto.request.DtoScoreRequest;
import com.example.AcademicInformationSystem.dto.response.DtoScoreResponse;
import com.example.AcademicInformationSystem.models.CourseStudents;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Score;
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

    public Boolean addScore(DtoScoreRequest scoreRequest, Response response) {
        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findById(scoreRequest.getStudentCourseId());
        Optional<Quiz> existingQuiz = quizRepository.findById(scoreRequest.getQuizId());

        if (!existingCourseStudent.isPresent() || !existingCourseStudent.get().getActive()){
            response.setMessage("Course Student Not Found");
            return false;
        } else if (!existingQuiz.isPresent()) {
            response.setMessage("Quiz Not Found");
            return false;
        } else if (scoreRequest.getGrade() <1 || scoreRequest.getGrade()>100) {
            response.setMessage("Grade format does not match");
            return false;
        }

        Optional<Score> existingScore = scoreRepository.findFirstByCourseStudentsIdAndQuizId(existingCourseStudent.get().getId(), existingQuiz.get().getId());
        if (existingScore.isPresent()) {
            response.setMessage("Data is already exists");
            return false;
        }

        Score newScore = new Score();
        newScore.setCourseStudents(existingCourseStudent.get());
        newScore.setQuiz(existingQuiz.get());
        newScore.setGrade(scoreRequest.getGrade());
        scoreRepository.save(newScore);

        String npm = existingCourseStudent.get().getStudent().getNpm();
        String nameStudent = existingCourseStudent.get().getStudent().getName();
        String department = existingCourseStudent.get().getStudent().getDepartment().getName();
        String course = existingCourseStudent.get().getCourse().getName();
        String quiz = existingQuiz.get().getName();
        response.setData(new DtoScoreResponse(npm, nameStudent, department, course, quiz, newScore.getGrade()));
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
