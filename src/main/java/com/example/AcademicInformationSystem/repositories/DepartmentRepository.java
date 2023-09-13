package com.example.AcademicInformationSystem.repositories;

import com.example.AcademicInformationSystem.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository  extends JpaRepository <Department, Long>{
    @Query("SELECT d from Department d where d.isDelete = false")
    List<Department> findAllNotDeleted();

    @Query("SELECT d FROM Department d WHERE d.isDelete = false AND d.name = :name")
    Department findByName(String name);

}
