import React, { useState, useEffect } from 'react';
import { commentAPI } from '../services/api';

const CommentSection = ({ bugId, currentUser }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    loadComments();
  }, [bugId]);

  const loadComments = async () => {
    try {
      const response = await commentAPI.getCommentsByBugId(bugId);
      if (response.data.success) {
        setComments(response.data.data);
      }
    } catch (error) {
      console.error('Error loading comments:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmitComment = async (e) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    setSubmitting(true);
    try {
      const response = await commentAPI.addComment(
        bugId, 
        { body: newComment },
        currentUser?.id
      );
      
      if (response.data.success) {
        setComments([...comments, response.data.data]);
        setNewComment('');
      }
    } catch (error) {
      console.error('Error adding comment:', error);
      alert('Failed to add comment');
    } finally {
      setSubmitting(false);
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('en-US', {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return <div className="comments-loading">Loading comments...</div>;
  }

  return (
    <div className="comment-section">
      <h4>Comments ({comments.length})</h4>
      
      <div className="comments-list">
        {comments.length === 0 ? (
          <div className="no-comments">
            <p>No comments yet. Be the first to add one!</p>
          </div>
        ) : (
          comments.map(comment => (
            <div key={comment.id} className="comment">
              <div className="comment-header">
                <span className="comment-author">{comment.author.name}</span>
                <span className="comment-date">{formatDate(comment.createdAt)}</span>
              </div>
              <div className="comment-body">
                {comment.body}
              </div>
            </div>
          ))
        )}
      </div>

      <form onSubmit={handleSubmitComment} className="comment-form">
        <div className="comment-input-group">
          <textarea
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Add a comment..."
            rows="3"
            disabled={submitting}
          />
          <button 
            type="submit" 
            className="btn-primary"
            disabled={!newComment.trim() || submitting}
          >
            {submitting ? 'Adding..' : 'Add Comment'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default CommentSection;