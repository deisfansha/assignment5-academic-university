package com.example.AcademicInformationSystem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoScoreResponse {
    private String npm;
    @JsonProperty("name")
    private String nameStudent;
    @JsonProperty("department")
    private String nameDepartment;
    @JsonProperty("course")
    private String nameCourse;
    @JsonProperty("quiz")
    private String nameQuiz;
    private Integer grade;

    public DtoScoreResponse(String npm, String nameStudent, String nameDepartment, String nameCourse, String nameQuiz, Integer grade) {
        this.npm = npm;
        this.nameStudent = nameStudent;
        this.nameDepartment = nameDepartment;
        this.nameCourse = nameCourse;
        this.nameQuiz = nameQuiz;
        this.grade = grade;
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

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
