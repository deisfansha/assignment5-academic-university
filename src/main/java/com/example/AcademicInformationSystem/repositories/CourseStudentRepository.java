package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.CourseStudents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseStudentRepository extends JpaRepository<CourseStudents, Long> {
    CourseStudents findByIsDeletedIsTrueOrderById();
    Optional<CourseStudents> findFirstByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}
