package com.example.AcademicInformationSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Department extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonIgnore
    private boolean isDelete = false;

    public Department(){

    }

    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isDelete = false;
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
        this.name = name.trim();
    }

    @JsonIgnore
    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
