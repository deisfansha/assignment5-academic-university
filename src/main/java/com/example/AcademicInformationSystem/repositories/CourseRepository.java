package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository <Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.isDelete = false AND c.name = :name")
    List<Course> findByName(String name);

    @Query("SELECT c from Course c where c.isDelete = false")
    List<Course> findAllNotDeleted();

    @Query("SELECT c FROM Course c WHERE c.isDelete = false AND c.id = :id")
    List<Course> findByIdCourse(@Param("id")Long id);
}
