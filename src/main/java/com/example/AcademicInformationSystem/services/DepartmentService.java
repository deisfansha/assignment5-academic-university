package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.dto.response.DtoStudentResponse;
import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department, Response response){
        Department existingDepartment = departmentRepository.findByName(department.getName());
        if (department.getName().isEmpty()){
            response.setMessage("Name Department Must Be Filled In");
            return null;
        } else if (existingDepartment !=null){
            response.setMessage("Data Is Already Exists");
            return null;
        }

        response.setMessage("Success");
        response.setData(department);
        return departmentRepository.save(department);
    }

    public List<Department> getAll(){
        return departmentRepository.findByIsDeleteIsFalse();
    }

    public Page<Department> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Department> result =  departmentRepository.findAll(pageable);
        return new PageImpl(result.getContent(), PageRequest.of(page, limit), result.getTotalPages());
    }

    public Department updateDepartment(Long id, Department department, Response response) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        if (department.getName().isEmpty()){
            response.setMessage("Name Department Is Must Be Filled In");
            return null;
        } else if (!existingDepartment.isPresent()){
            response.setMessage("Department Not Found");
            return null;
        } else if (department.getName().equalsIgnoreCase(existingDepartment.get().getName())) {
            response.setMessage("Data is already exists");
            return null;
        }

        existingDepartment.get().setName(department.getName());

        // Save department updated
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingDepartment.get());
    }

    public Department softDelete(Long id, Response response){
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (!existingDepartment.isPresent()){
            response.setMessage("Department Not Found");
            return null;
        }

        existingDepartment.get().setDelete(true);
        // Save department deleted
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingDepartment.get());
    }

}
