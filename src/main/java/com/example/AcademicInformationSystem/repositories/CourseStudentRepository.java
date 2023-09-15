package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.CourseStudents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudents, Long> {
    List<CourseStudents> findAllByIsDeletedIsFalseAndIsActiveTrue();
    Optional<CourseStudents> findByIdAndIsDeletedIsFalseAndIsActiveIsTrue(Long studentCourseId);
    Optional<CourseStudents> findFirstByStudent_IdAndCourse_Id(Long studentId, Long courseId);
    Page<CourseStudents> findAllByIsDeletedIsFalseOrderByIdAsc(Pageable pageable);
}
