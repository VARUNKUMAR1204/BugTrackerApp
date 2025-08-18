import React, { useState, useEffect } from 'react';
import CommentSection from './CommentSection';
import { bugAPI } from '../services/api';

const BugModal = ({ bug, users, currentUser, onClose, onStatusChange }) => {
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadComments();
  }, [bug.id]);

  const loadComments = async () => {
    try {
      const response = await bugAPI.getAllBugs(); // This will reload the bug with comment count
      setLoading(false);
    } catch (error) {
      console.error('Error loading comments:', error);
      setLoading(false);
    }
  };

  const getSeverityClass = (severity) => {
    switch (severity) {
      case 'HIGH': return 'severity-high';
      case 'MEDIUM': return 'severity-medium';
      case 'LOW': return 'severity-low';
      default: return '';
    }
  };

  const getStatusClass = (status) => {
    switch (status) {
      case 'NEW': return 'status-new';
      case 'IN_PROGRESS': return 'status-progress';
      case 'FIXED': return 'status-fixed';
      case 'VERIFIED': return 'status-verified';
      default: return '';
    }
  };

  const handleStatusChange = (newStatus) => {
    onStatusChange(bug.id, newStatus);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content large" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <div className="bug-modal-title">
            <h2>Bug #{bug.id}: {bug.title}</h2>
            <div className="bug-modal-badges">
              <span className={`severity-badge ${getSeverityClass(bug.severity)}`}>
                {bug.severity}
              </span>
              <span className={`status-badge ${getStatusClass(bug.status)}`}>
                {bug.status.replace('_', ' ')}
              </span>
            </div>
          </div>
          <button className="close-button" onClick={onClose}>&times;</button>
        </div>

        <div className="modal-body">
          <div className="bug-details">
            <div className="bug-info-grid">
              <div className="bug-info-item">
                <strong>Assigned to:</strong>
                <span>{bug.assignee?.name || 'Unassigned'}</span>
              </div>
              <div className="bug-info-item">
                <strong>Created:</strong>
                <span>{formatDate(bug.createdAt)}</span>
              </div>
              <div className="bug-info-item">
                <strong>Last updated:</strong>
                <span>{formatDate(bug.updatedAt)}</span>
              </div>
            </div>

            {bug.description && (
              <div className="bug-description">
                <h4>Description:</h4>
                <p>{bug.description}</p>
              </div>
            )}

            <div className="status-change-section">
              <h4>Quick Status Change:</h4>
              <div className="status-buttons">
                <button 
                  className={`status-btn status-btn-new ${bug.status === 'NEW' ? 'active' : ''}`}
                  onClick={() => handleStatusChange('NEW')}
                >
                  New
                </button>
                <button 
                  className={`status-btn status-btn-progress ${bug.status === 'IN_PROGRESS' ? 'active' : ''}`}
                  onClick={() => handleStatusChange('IN_PROGRESS')}
                >
                  In Progress
                </button>
                <button 
                  className={`status-btn status-btn-fixed ${bug.status === 'FIXED' ? 'active' : ''}`}
                  onClick={() => handleStatusChange('FIXED')}
                >
                  Fixed
                </button>
                <button 
                  className={`status-btn status-btn-verified ${bug.status === 'VERIFIED' ? 'active' : ''}`}
                  onClick={() => handleStatusChange('VERIFIED')}
                >
                  Verified
                </button>
              </div>
            </div>

            <CommentSection 
              bugId={bug.id}
              currentUser={currentUser}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default BugModal;