package com.example.bugtracker.respository;

import com.example.bugtracker.entity.Bug;
import com.example.bugtracker.entity.enums.Severity;
import com.example.bugtracker.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug,Long> {
    List<Bug> findBySeverity(Severity severity);

    List<Bug> findByStatus(Status status);

    List<Bug> findBySeverityAndStatus(Severity severity, Status status);

    List<Bug> findAllByOrderByCreatedAtDesc();

    @Query("SELECT b FROM Bug b WHERE b.severity = 'HIGH' AND b.status = 'NEW' ORDER BY b.createdAt DESC")
    List<Bug> findHighPriorityNewBugs();

    List<Bug> findBySeverityOrderByCreatedAtDesc(Severity severity);

    List<Bug> findByStatusOrderByCreatedAtDesc(Status status);

    List<Bug> findByAssigneeId(Long assigneeId);
}
