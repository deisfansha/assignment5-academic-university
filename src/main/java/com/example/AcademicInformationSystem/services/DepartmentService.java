package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    private Response response;

    public void setResponse(Response response){
        this.response = response;
    }

    public Department createDepartment(Department department, Response response){
        Department existingDepartment = departmentRepository.findByName(department.getName());

        if (!validateInput(department.getName())){
            return null;
        }

        if (existingDepartment !=null){
            response.setMessage("Data Is Already Exists");
            return null;
        }
        department.setDelete(false);
        response.setMessage("Success");
        response.setData(department);
        return departmentRepository.save(department);
    }

    public List<Department> viewDepartment(){
        return departmentRepository.findAllNotDeleted();
    }

    public Department updateDepartment(Long id, Department department, Response response) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        if (!existingDepartment.isPresent()) {
            response.setMessage("Department Not Found");
            return null;
        }

        Department existingData = existingDepartment.get();

        if (!validateInput(department.getName())){
            return null;
        }

        String newName = department.getName().trim();

        if (!newName.equalsIgnoreCase(existingData.getName())) {
            existingData.setName(newName);
        }else {
            response.setMessage("Data is already exists");
            return null;
        }

        // Save department updated
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingData);
    }

    public Department softDelete(Long id, Response response){
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        if (!validateFounded(existingDepartment)){
            return null;
        }

        Department existingData = existingDepartment.get();

        existingData.setDelete(true);
        // Save department deleted
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingData);
    }

    private boolean validateInput(String name){
        if (name.isEmpty()){
            response.setMessage("Data Must Be Filled In");
            return false;
        }
        if (!name.matches("^[a-zA-Z -]*$")){
            response.setMessage("Can only input the alphabet");
            return false;
        }
        return true;
    }

    private boolean validateFounded(Optional<Department> existingDepartment){
        if (!existingDepartment.isPresent()) {
            response.setMessage("Department Not Found");
            return false;
        }
        return true;
    }

}
