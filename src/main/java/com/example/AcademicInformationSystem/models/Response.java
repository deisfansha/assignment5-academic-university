package com.example.AcademicInformationSystem.models;

public class Response {

    private String message;
    private Object data;

    public Response(){

    }

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
