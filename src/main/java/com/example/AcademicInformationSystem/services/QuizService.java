package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    /**
     * Menambahkan Data Quiz.
     *
     * @param quiz           quiz yang akan ditambahkan.
     * @param response       pesan yang akan ditampilkan pada api,
     * @return               Data quiz jika berhasil ditambahkan dan null jika gagal.
     */
    public Quiz createQuiz(Quiz quiz, Response response){
        List<Quiz> existingQuiz = quizRepository.findByNameAndIsDeleteFalse(quiz.getName());

        if (existingQuiz.size()>=1){
            response.setMessage("Data Is Already Exists");
            return null;
        }

        response.setData(quiz);
        return quizRepository.save(quiz);
    }

    /**
     * Mendapatkan Data Quiz.
     *
     * @return         Data Quiz berdasarkan isDelete = false.
     */
    public List<Quiz> viewQuiz(){
        return quizRepository.findByIsDeleteIsFalse();
    }

    /**
     *
     * @param page      custom nomor halaman
     * @param limit     custom data yang ditampilkan pada setiap page
     * @return          data page Quiz berdasarkan isDelete = false.
     */
    public Page<Quiz> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Quiz> result =  quizRepository.findAllByIsDeleteIsFalseOrderByIdAsc(pageable);
        return new PageImpl(result.getContent(), PageRequest.of(page, limit), result.getTotalPages());
    }

    /**
     * Mengubah Data Quiz.
     *
     * @param id                ID Quiz yang akan diubah,
     * @param quiz              Data nama quiz yang akan diubah,
     * @param response          pesan untuk ditampilkan pada api,
     * @return                  Quiz jika berhasil diubah dan null jika gagal.
     *
     */
    public Quiz updateQuiz(Long id, Quiz quiz, Response response) {
        Optional<Quiz> existingQuiz = quizRepository.findById(id);

        if (!existingQuiz.isPresent()) {
            response.setMessage("Quiz Not Found");
            return null;
        } else if (quiz.getName().isEmpty() || quiz.getName() == null){
            response.setMessage("Data Must Be Filled In");
            return null;
        } else if (quiz.getName().equalsIgnoreCase(existingQuiz.get().getName())) {
            response.setMessage("Data is already exists");
            return null;
        }

        existingQuiz.get().setName(quiz.getName());

        // Save Quiz updated
        response.setMessage("Success");
        response.setData(existingQuiz);
        return quizRepository.save(existingQuiz.get());
    }

    /**
     * Menghapus secara halus Data Quiz.
     *
     * @param id        ID Quiz yang akan dihapus
     * @param response  pesan untuk ditampilkan pada api,
     * @return          Data Quiz jika berhasil dihapus, null jika gagal.
     */
    public Quiz softDelete(Long id, Response response){
        Optional<Quiz> existingQuiz = quizRepository.findById(id);

        if (!existingQuiz.isPresent()) {
            response.setMessage("Quiz Not Found");
            return null;
        }

        Quiz existingData = existingQuiz.get();
        existingData.setDelete(true);

        // Save Quiz deleted
        response.setData(existingQuiz);
        return quizRepository.save(existingData);
    }

}
