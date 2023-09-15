package com.example.AcademicInformationSystem.controllers;

import com.example.AcademicInformationSystem.dto.response.DtoStudentResponse;
import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AcademicInformationSystem.services.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmenController {

    @Autowired
    private DepartmentService departmentService;

    private Response response = new Response();
    @PostMapping("")
    public ResponseEntity saveDepartment(@RequestBody Department department){
        Department newDepartment = departmentService.createDepartment(department, response);
        if (newDepartment == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity viewAll(){
        List<Department> departmentList = departmentService.getAll();
        if (departmentList.isEmpty()){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(departmentList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/page")
    public ResponseEntity pageViewAll(@RequestParam int page, @RequestParam int limit){
        Page<Department> departmentList = departmentService.pageView(page, limit);
        if (departmentList.isEmpty()){
            response.setMessage("Data Is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            response.setMessage("Success");
            response.setData(departmentList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatedDepartment(@PathVariable Long id, @RequestBody Department department){
        Department newDepartment = departmentService.updateDepartment(id, department, response);
        if (newDepartment == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletedDepartment(@PathVariable Long id){
        Department newDepartment = departmentService.softDelete(id, response);
        if (newDepartment == null){
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
