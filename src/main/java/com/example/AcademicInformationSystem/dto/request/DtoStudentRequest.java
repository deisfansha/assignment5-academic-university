package com.example.AcademicInformationSystem.dto.request;

public class DtoStudentRequest {
    private String name;
    private String phone_number;
    private String gender;
    private Long code_department;

    public DtoStudentRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Long getCode_department() {
        return code_department;
    }

    public void setCode_department(Long code_department) {
        this.code_department = code_department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
