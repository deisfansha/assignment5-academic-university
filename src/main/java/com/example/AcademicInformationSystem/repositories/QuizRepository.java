package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuizRepository extends JpaRepository <Quiz, Long> {
    @Query("SELECT q FROM Quiz q WHERE q.isDelete = false")
    List<Quiz> findAllNotDeleted();

    @Query("SELECT q FROM Quiz q WHERE q.isDelete = false AND q.name = :name")
    List<Quiz> findByName(@Param("name") String name);

}
