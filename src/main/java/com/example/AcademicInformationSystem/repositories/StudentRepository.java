package com.example.AcademicInformationSystem.repositories;


import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    @Query("SELECT s from Student s where s.isDelete = false")
    List<Student> findAllNotDeleted();

    @Query("SELECT s FROM Student s WHERE s.isDelete = false AND s.phoneNumber = :phoneNumber")
    List<Student> findByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findLast();

}
