package com.example.bugtracker.dto.request;

import com.example.bugtracker.entity.enums.Severity;
import jakarta.validation.constraints.Size;

public class UpdateBugRequest {

    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    private String description;
    private Severity severity;
    private Long assigneeId;


    public UpdateBugRequest() {}


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

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }
}