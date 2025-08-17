package com.example.bugtracker.dto.response;

import com.example.bugtracker.entity.Bug;
import com.example.bugtracker.entity.enums.Severity;
import com.example.bugtracker.entity.enums.Status;

import java.time.LocalDateTime;

public class BugResponse {
    private Long id;
    private String title;
    private String description;
    private Severity severity;
    private Status status;
    private UserResponse assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long commentCount;


    public BugResponse() {}

    public BugResponse(Bug bug) {
        this.id = bug.getId();
        this.title = bug.getTitle();
        this.description = bug.getDescription();
        this.severity = bug.getSeverity();
        this.status = bug.getStatus();
        this.assignee = bug.getAssignee() != null ? new UserResponse(bug.getAssignee()) : null;
        this.createdAt = bug.getCreatedAt();
        this.updatedAt = bug.getUpdatedAt();
        this.commentCount = 0;
    }

    public BugResponse(Bug bug, long commentCount) {
        this(bug);
        this.commentCount = commentCount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserResponse getAssignee() {
        return assignee;
    }

    public void setAssignee(UserResponse assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
