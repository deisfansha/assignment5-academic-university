package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Scores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository <Scores, Long> {
    List<Scores> findByStudentId(Long studentId);
    List<Scores> findByCorurseId(Long courseId);
    List<Scores> findByQuizId(Long quizId);

}
