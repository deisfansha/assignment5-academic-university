package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository <Score, Long> {
}
