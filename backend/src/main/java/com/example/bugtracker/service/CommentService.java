package com.example.bugtracker.service;

import com.example.bugtracker.dto.request.AddCommentRequest;
import com.example.bugtracker.dto.response.CommentResponse;
import com.example.bugtracker.entity.Bug;
import com.example.bugtracker.entity.Comment;
import com.example.bugtracker.entity.User;
import com.example.bugtracker.respository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BugService bugService;

    @Autowired
    private UserService userService;


    public List<CommentResponse> getCommentsByBugId(Long bugId) {
        List<Comment> comments = commentRepository.findCommentsWithDetailsForBug(bugId);
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }


    public Optional<CommentResponse> addComment(Long bugId, AddCommentRequest request, Long authorId) {
        Optional<Bug> bugOpt = bugService.findById(bugId);


        if (authorId == null) {
            List<User> users = userService.getAllUsers().stream()
                    .map(userResponse -> userService.findById(userResponse.getId()).orElse(null))
                    .filter(user -> user != null)
                    .collect(Collectors.toList());

            if (!users.isEmpty()) {
                authorId = users.get(0).getId();
            } else {
                return Optional.empty();
            }
        }

        Optional<User> authorOpt = userService.findById(authorId);

        if (bugOpt.isPresent() && authorOpt.isPresent()) {
            Comment comment = new Comment();
            comment.setBug(bugOpt.get());
            comment.setAuthor(authorOpt.get());
            comment.setBody(request.getBody());

            Comment savedComment = commentRepository.save(comment);
            return Optional.of(new CommentResponse(savedComment));
        }

        return Optional.empty();
    }


    public List<CommentResponse> getCommentsByAuthor(Long authorId) {
        List<Comment> comments = commentRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
