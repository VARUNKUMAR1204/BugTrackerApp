package com.example.bugtracker.controller;

import com.example.bugtracker.dto.request.AddCommentRequest;
import com.example.bugtracker.dto.response.ApiResponse;
import com.example.bugtracker.dto.response.CommentResponse;
import com.example.bugtracker.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/bug/{bugId}")
    public ResponseEntity<?> getCommentsByBugId(@PathVariable Long bugId) {
        try {
            List<CommentResponse> comments = commentService.getCommentsByBugId(bugId);
            return ResponseEntity.ok(ApiResponse.success("Comments retrieved successfully", comments));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve comments: " + e.getMessage()));
        }
    }


    @PostMapping("/bug/{bugId}")
    public ResponseEntity<?> addComment(@PathVariable Long bugId,
                                        @Valid @RequestBody AddCommentRequest request,
                                        @RequestParam(required = false) Long authorId) {
        try {
            Optional<CommentResponse> comment = commentService.addComment(bugId, request, authorId);

            if (comment.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Comment added successfully", comment.get()));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Failed to add comment - bug not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to add comment: " + e.getMessage()));
        }
    }


    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getCommentsByAuthor(@PathVariable Long authorId) {
        try {
            List<CommentResponse> comments = commentService.getCommentsByAuthor(authorId);
            return ResponseEntity.ok(ApiResponse.success("Author comments retrieved successfully", comments));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve author comments: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            boolean deleted = commentService.deleteComment(id);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete comment: " + e.getMessage()));
        }
    }
}