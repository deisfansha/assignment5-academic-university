package com.example.AcademicInformationSystem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoStudentCourseResponse {

    @JsonProperty("npm")
    private String npm;
    @JsonProperty("name_student")
    private String nameStudent;
    @JsonProperty("department")
    private String nameDepartment;
    @JsonProperty("course")
    private String nameCourse;
    @JsonProperty("status_active")
    private Boolean active;

    public DtoStudentCourseResponse() {
    }

    public DtoStudentCourseResponse(String npm, String nameStudent, String nameDepartment, String nameCourse, Boolean active) {
        this.npm = npm;
        this.nameStudent = nameStudent;
        this.nameDepartment = nameDepartment;
        this.nameCourse = nameCourse;
        this.active = active;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
