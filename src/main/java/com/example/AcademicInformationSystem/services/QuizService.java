package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    private Response response;

    public void setResponse(Response response){
        this.response = response;
    }

    public Quiz createQuiz(Quiz quiz, Response response){
        List<Quiz> existingQuiz = quizRepository.findByName(quiz.getName());

        if (existingQuiz !=null){
            response.setMessage("Data Is Already Exists");
            return null;
        }

        quiz.setDelete(false);
        response.setMessage("Success");
        response.setData(quiz);
        return quizRepository.save(quiz);
    }

    public List<Quiz> viewQuiz(){
        return quizRepository.findAllNotDeleted();
    }

    public Quiz updateQuiz(Long id, Quiz quiz, Response response) {
        Optional<Quiz> existingQuiz = quizRepository.findById(id);

        if (!existingQuiz.isPresent()) {
            response.setMessage("Quiz Not Found");
            return null;
        }

        Quiz existingData = existingQuiz.get();

        if (quiz.getName().isEmpty() || quiz.getName() == null){
            response.setMessage("Data Must Be Filled In");
            return null;
        }

        String newName = quiz.getName().trim();

        if (!newName.equalsIgnoreCase(existingData.getName())) {
            existingData.setName(newName);
        }else {
            response.setMessage("Data is already exists");
            return null;
        }

        // Save Quiz updated
        response.setMessage("Success");
        response.setData(existingQuiz);
        return quizRepository.save(existingData);
    }

    public Quiz softDelete(Long id, Response response){
        Optional<Quiz> existingQuiz = quizRepository.findById(id);

        if (!existingQuiz.isPresent()) {
            response.setMessage("Department Not Found");
            return null;
        }

        Quiz existingData = existingQuiz.get();

        existingData.setDelete(true);
        // Save Quiz deleted
        response.setMessage("Success");
        response.setData(existingQuiz);
        return quizRepository.save(existingData);
    }

}
