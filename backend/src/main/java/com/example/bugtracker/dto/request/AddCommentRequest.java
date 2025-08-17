package com.example.bugtracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AddCommentRequest {

    @NotBlank(message = "Comment body is required")
    private String body;


    public AddCommentRequest() {}

    public AddCommentRequest(String body) {
        this.body = body;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}