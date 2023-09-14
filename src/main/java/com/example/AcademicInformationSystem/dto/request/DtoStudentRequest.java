package com.example.AcademicInformationSystem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoStudentRequest {
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String gender;

    @JsonProperty("code_department")
    private Long codeDepartment;

    public DtoStudentRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCodeDepartment() {
        return codeDepartment;
    }

    public void setCodeDepartment(Long codeDepartment) {
        this.codeDepartment = codeDepartment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
