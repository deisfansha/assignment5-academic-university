package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository <Department, Long>{
    List<Department> findByIsDeleteIsFalse();
    Optional<Department> findByIdAndIsDeleteIsFalse(Long idDepartment);
    Optional<Department> findByNameAndIsDeleteIsFalse(String name);
    Page<Department> findAllByIsDeleteIsFalseOrderByIdAsc(Pageable pageable);
}
