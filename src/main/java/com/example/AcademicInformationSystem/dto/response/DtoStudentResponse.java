package com.example.AcademicInformationSystem.dto.response;

public class DtoStudentResponse {
    private String npm;
    private String name;
    private String gender;
    private String phone_number;
    private String name_department;

    public DtoStudentResponse(String npm, String name, String gender, String phone_number, String name_department) {
        this.npm = npm;
        this.name = name;
        this.gender = gender;
        this.phone_number = phone_number;
        this.name_department = name_department;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName_department() {
        return name_department;
    }

    public void setName_department(String name_department) {
        this.name_department = name_department;
    }
}
