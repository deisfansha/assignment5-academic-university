package com.example.AcademicInformationSystem.dto.response;

public class DtoStudentResponse {
    private String npm;
    private String name;
    private String gender;
    private String phoneNumber;
    private String nameDepartment;

    public DtoStudentResponse(String npm, String name, String gender, String phoneNumber, String nameDepartment) {
        this.npm = npm;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.nameDepartment = nameDepartment;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }
}
