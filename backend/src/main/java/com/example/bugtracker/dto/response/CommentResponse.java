package com.example.bugtracker.dto.response;

import com.example.bugtracker.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String body;
    private UserResponse author;
    private LocalDateTime createdAt;


    public CommentResponse() {}

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.author = new UserResponse(comment.getAuthor());
        this.createdAt = comment.getCreatedAt();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public void setAuthor(UserResponse author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
