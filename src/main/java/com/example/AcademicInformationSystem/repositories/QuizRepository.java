package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository <Quiz, Long> {
    List<Quiz> findByIsDeleteIsFalse();
    Optional<Quiz> findByIdAndIsDeleteIsFalse(Long quizId);
    List<Quiz> findByNameAndIsDeleteFalse(String name);

}
