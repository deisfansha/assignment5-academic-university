package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository <Course, Long> {
    Optional<Course> findByName(String name);
    Optional<Course> findByIdAndIsDeleteIsFalse(Long courseId);
    List<Course> findByIsDeleteIsFalse();
    @Query("SELECT c FROM Course c WHERE c.isDelete = false AND c.id = :id")
    List<Course> findByIdCourse(@Param("id")Long id);
    Page<Course> findAllByIsDeleteIsFalseOrderByNameAsc(Pageable pageable);


}
