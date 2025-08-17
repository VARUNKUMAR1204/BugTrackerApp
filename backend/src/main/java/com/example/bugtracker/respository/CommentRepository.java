package com.example.bugtracker.respository;

import com.example.bugtracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBugId(Long bugId);

    List<Comment> findByBugIdOrderByCreatedAtAsc(Long bugId);

    List<Comment> findByBugIdOrderByCreatedAtDesc(Long bugId);

    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);


    @Query("SELECT c FROM Comment c WHERE c.bug.id = :bugId ORDER BY c.createdAt DESC")
    List<Comment> findRecentCommentsByBugId(@Param("bugId") Long bugId);


    // Custom query to get comments with author and bug info
    @Query("SELECT c FROM Comment c JOIN FETCH c.author JOIN FETCH c.bug WHERE c.bug.id = :bugId ORDER BY c.createdAt ASC")
    List<Comment> findCommentsWithDetailsForBug(@Param("bugId") Long bugId);

    long countByBugId(Long bugId);

}
