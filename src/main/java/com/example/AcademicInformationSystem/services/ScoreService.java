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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    /**
     * Menambahkan Data Score Mahasiswa.
     *
     * @param scoreRequest   Data skor mahasiswa yang akan ditambahkan.
     * @param response       Pesan yang akan ditampilkan pada api,
     * @return               Data skor mahasiswa jika berhasil ditambahkan dan bernilai true
     *                       false jika gagal.
     */
    public Boolean addScore(DtoScoreRequest scoreRequest, Response response) {
        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findByIdAndIsDeletedIsFalseAndIsActiveIsTrue(scoreRequest.getStudentCourseId());
        Optional<Quiz> existingQuiz = quizRepository.findByIdAndIsDeleteIsFalse(scoreRequest.getQuizId());

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

    /**
     * Mengubah Skor Mahasiswa.
     *
     * @param id                ID Skor Mahasiswa yang akan diubah,
     * @param scoreRequest        Mengubah skor quiz mahasiswa,
     * @param response          pesan untuk ditampilkan pada api,
     * @return                  Data Skor Mahasiswa jika berhasil diubah dan bernilai true, false jika gagal.
     *
     */
    public Boolean updateScore(Long id, DtoScoreRequest scoreRequest, Response response) {
        Optional<Score> existingScore = scoreRepository.findById(id);

        if (!existingScore.isPresent() || !existingScore.get().getDeleted()){
            response.setMessage("Score Quiz Not Found");
            return false;
        } else if (scoreRequest.getGrade()<1 || scoreRequest.getGrade()>100) {
            response.setMessage("Grade format does not match");
            return false;
        }

        existingScore.get().setGrade(scoreRequest.getGrade());
        scoreRepository.save(existingScore.get());

        String npm = existingScore.get().getCourseStudents().getStudent().getNpm();
        String nameStudent = existingScore.get().getCourseStudents().getStudent().getName();
        String department = existingScore.get().getCourseStudents().getStudent().getDepartment().getName();
        String course = existingScore.get().getCourseStudents().getCourse().getName();
        String quiz = existingScore.get().getQuiz().getName();
        response.setData(new DtoScoreResponse(npm, nameStudent, department, course, quiz, existingScore.get().getGrade()));
        return true;
    }

    /**
     * Mendapatkan Data Skor Mahasiswa.
     *
     * @return         Data Mahasiswa berdasarkan isDelete = false.
     */
    public List<DtoScoreResponse> getAll(){
        List<Score> score = scoreRepository.findAll();
        List<DtoScoreResponse> scoreList = new ArrayList<>();

        for (Score scoreData: score){
            DtoScoreResponse scoreResponse = new DtoScoreResponse(
                    scoreData.getCourseStudents().getStudent().getNpm(),
                    scoreData.getCourseStudents().getStudent().getName(),
                    scoreData.getCourseStudents().getStudent().getDepartment().getName(),
                    scoreData.getCourseStudents().getCourse().getName(),
                    scoreData.getQuiz().getName(),scoreData.getGrade());
            scoreList.add(scoreResponse);
        }
        return scoreList;
    }

    /**
     *
     * @param page      custom nomor halaman
     * @param limit     custom data yang ditampilkan pada setiap page
     * @return          data page Skor quiz mahasiswa.
     */
    public Page<DtoScoreResponse> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Score> result =  scoreRepository.findAllByOrderByIdAsc(pageable);
        List<DtoScoreResponse> scoreList = new ArrayList<>();
        for (Score scoreData: result.getContent()){
            DtoScoreResponse scoreResponse = new DtoScoreResponse(
                    scoreData.getCourseStudents().getStudent().getNpm(),
                    scoreData.getCourseStudents().getStudent().getName(),
                    scoreData.getCourseStudents().getStudent().getDepartment().getName(),
                    scoreData.getCourseStudents().getCourse().getName(),
                    scoreData.getQuiz().getName(),scoreData.getGrade());
            scoreList.add(scoreResponse);
        }
        return new PageImpl(scoreList, PageRequest.of(page, limit), result.getTotalPages());
    }

}
