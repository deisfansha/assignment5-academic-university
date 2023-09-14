package com.example.AcademicInformationSystem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoScoreRequest {
    @JsonProperty("student_course_id")
    private Long studentCourseId;
    @JsonProperty("quiz")
    private Long quizId;
    private Integer grade;

    public Long getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(Long studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
