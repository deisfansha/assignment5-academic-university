package com.example.AcademicInformationSystem.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String npm;
    private String gender;
    private String phoneNumber;
    @JsonIgnore
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Student(){
    }

    public Student(Long id, String name, String npm, String gender, String phoneNumber, boolean isDelete, Department department) {
        this.id = id;
        this.name = name;
        this.npm = npm;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.isDelete = isDelete;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
