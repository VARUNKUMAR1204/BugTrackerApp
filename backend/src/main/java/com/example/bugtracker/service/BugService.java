package com.example.bugtracker.service;


import com.example.bugtracker.dto.request.CreateBugRequest;
import com.example.bugtracker.dto.request.UpdateBugRequest;
import com.example.bugtracker.dto.response.BugResponse;
import com.example.bugtracker.entity.Bug;
import com.example.bugtracker.entity.User;
import com.example.bugtracker.entity.enums.Severity;
import com.example.bugtracker.entity.enums.Status;
import com.example.bugtracker.respository.BugRepository;
import com.example.bugtracker.respository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BugService {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    public List<BugResponse> getAllBugs(Severity severity, Status status) {
        List<Bug> bugs;

        if (severity != null && status != null) {
            bugs = bugRepository.findBySeverityAndStatus(severity, status);
        } else if (severity != null) {
            bugs = bugRepository.findBySeverityOrderByCreatedAtDesc(severity);
        } else if (status != null) {
            bugs = bugRepository.findByStatusOrderByCreatedAtDesc(status);
        } else {
            bugs = bugRepository.findAllByOrderByCreatedAtDesc();
        }

        return bugs.stream()
                .map(bug -> {
                    long commentCount = commentRepository.countByBugId(bug.getId());
                    return new BugResponse(bug, commentCount);
                })
                .collect(Collectors.toList());
    }

    // Getting bugs for specific user
    public List<BugResponse> getBugsByUser(Long userId) {
        List<Bug> bugs = bugRepository.findByAssigneeId(userId);
        return bugs.stream()
                .map(bug -> {
                    long commentCount = commentRepository.countByBugId(bug.getId());
                    return new BugResponse(bug, commentCount);
                })
                .collect(Collectors.toList());
    }

    // Getti high priority new bugs
    public List<BugResponse> getHighPriorityNewBugs() {
        List<Bug> bugs = bugRepository.findHighPriorityNewBugs();
        return bugs.stream()
                .map(bug -> {
                    long commentCount = commentRepository.countByBugId(bug.getId());
                    return new BugResponse(bug, commentCount);
                })
                .collect(Collectors.toList());
    }

    // Get bug by ID
    public Optional<BugResponse> getBugById(Long id) {
        return bugRepository.findById(id)
                .map(bug -> {
                    long commentCount = commentRepository.countByBugId(bug.getId());
                    return new BugResponse(bug, commentCount);
                });
    }

    // Create new bug
    public BugResponse createBug(CreateBugRequest request) {
        Bug bug = new Bug();
        bug.setTitle(request.getTitle());
        bug.setDescription(request.getDescription());
        bug.setSeverity(request.getSeverity());
        bug.setStatus(Status.NEW);


        if (request.getAssigneeId() != null) {
            Optional<User> assignee = userService.findById(request.getAssigneeId());
            assignee.ifPresent(bug::setAssignee);
        }

        Bug savedBug = bugRepository.save(bug);
        return new BugResponse(savedBug, 0);
    }


    public Optional<BugResponse> updateBug(Long id, UpdateBugRequest request) {
        return bugRepository.findById(id)
                .map(bug -> {
                    if (request.getTitle() != null) {
                        bug.setTitle(request.getTitle());
                    }
                    if (request.getDescription() != null) {
                        bug.setDescription(request.getDescription());
                    }
                    if (request.getSeverity() != null) {
                        bug.setSeverity(request.getSeverity());
                    }
                    if (request.getAssigneeId() != null) {
                        Optional<User> assignee = userService.findById(request.getAssigneeId());
                        assignee.ifPresent(bug::setAssignee);
                    }

                    Bug savedBug = bugRepository.save(bug);
                    long commentCount = commentRepository.countByBugId(savedBug.getId());
                    return new BugResponse(savedBug, commentCount);
                });
    }


    public Optional<BugResponse> updateBugStatus(Long id, Status newStatus) {
        return bugRepository.findById(id)
                .map(bug -> {
                    bug.setStatus(newStatus);
                    Bug savedBug = bugRepository.save(bug);
                    long commentCount = commentRepository.countByBugId(savedBug.getId());
                    return new BugResponse(savedBug, commentCount);
                });
    }

    public boolean deleteBug(Long id) {
        if (bugRepository.existsById(id)) {
            bugRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Optional<Bug> findById(Long id) {
        return bugRepository.findById(id);
    }
}
