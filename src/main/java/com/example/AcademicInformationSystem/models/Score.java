package com.example.AcademicInformationSystem.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "score_quiz")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_student_course", referencedColumnName = "id")
    private CourseStudents courseStudents;
    @ManyToOne
    @JoinColumn(name = "id_quiz", referencedColumnName = "id")
    private Quiz quiz;
    private Integer grade;
    private Boolean isDeleted = true;

    public Score(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseStudents getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(CourseStudents courseStudents) {
        this.courseStudents = courseStudents;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
