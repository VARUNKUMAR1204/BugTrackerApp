package com.example.bugtracker.controller;


import com.example.bugtracker.dto.request.CreateBugRequest;
import com.example.bugtracker.dto.request.UpdateBugRequest;
import com.example.bugtracker.dto.request.UpdateStatusRequest;
import com.example.bugtracker.dto.response.ApiResponse;
import com.example.bugtracker.dto.response.BugResponse;
import com.example.bugtracker.entity.enums.Severity;
import com.example.bugtracker.entity.enums.Status;
import com.example.bugtracker.service.BugService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bugs")
@CrossOrigin(origins = "*")
public class BugController {

    @Autowired
    private BugService bugService;


    @GetMapping
    public ResponseEntity<?> getAllBugs(
            @RequestParam(required = false) Severity severity,
            @RequestParam(required = false) Status status) {
        try {
            List<BugResponse> bugs = bugService.getAllBugs(severity, status);
            return ResponseEntity.ok(ApiResponse.success("Bugs retrieved successfully", bugs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve bugs: " + e.getMessage()));
        }
    }

    // Get high priority new bugs
    @GetMapping("/high-priority-new")
    public ResponseEntity<?> getHighPriorityNewBugs() {
        try {
            List<BugResponse> bugs = bugService.getHighPriorityNewBugs();
            return ResponseEntity.ok(ApiResponse.success("High priority new bugs retrieved successfully", bugs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve high priority bugs: " + e.getMessage()));
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBugsByUser(@PathVariable Long userId) {
        try {
            List<BugResponse> bugs = bugService.getBugsByUser(userId);
            return ResponseEntity.ok(ApiResponse.success("User bugs retrieved successfully", bugs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve user bugs: " + e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBugById(@PathVariable Long id) {
        try {
            Optional<BugResponse> bug = bugService.getBugById(id);
            if (bug.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Bug retrieved successfully", bug.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve bug: " + e.getMessage()));
        }
    }


    @PostMapping
    public ResponseEntity<?> createBug(@Valid @RequestBody CreateBugRequest request) {
        try {
            BugResponse bug = bugService.createBug(request);
            return ResponseEntity.ok(ApiResponse.success("Bug created successfully", bug));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create bug: " + e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBug(@PathVariable Long id, @Valid @RequestBody UpdateBugRequest request) {
        try {
            Optional<BugResponse> bug = bugService.updateBug(id, request);
            if (bug.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Bug updated successfully", bug.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update bug: " + e.getMessage()));
        }
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateBugStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest request) {
        try {
            Optional<BugResponse> bug = bugService.updateBugStatus(id, request.getStatus());
            if (bug.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Bug status updated successfully", bug.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update bug status: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBug(@PathVariable Long id) {
        try {
            boolean deleted = bugService.deleteBug(id);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success("Bug deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete bug: " + e.getMessage()));
        }
    }
}

