package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository <Score, Long> {
    Optional<Score> findFirstByCourseStudentsIdAndQuizId(Long courseStudentId, Long quizId);
    Page<Score> findAllByOrderByIdAsc(Pageable pageable);
}
