import React from 'react';

const BugCard = ({ bug, onClick, onStatusChange }) => {
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

  const handleStatusChange = (e, newStatus) => {
    e.stopPropagation(); // Prevent card click
    onStatusChange(bug.id, newStatus);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="bug-card" onClick={onClick}>
      <div className="bug-card-header">
        <div className="bug-id">#{bug.id}</div>
        <div className="bug-badges">
          <span className={`severity-badge ${getSeverityClass(bug.severity)}`}>
            {bug.severity}
          </span>
          <span className={`status-badge ${getStatusClass(bug.status)}`}>
            {bug.status.replace('_', ' ')}
          </span>
        </div>
      </div>

      <div className="bug-card-content">
        <h3 className="bug-title">{bug.title}</h3>
        <p className="bug-description">
          {bug.description?.substring(0, 100)}
          {bug.description?.length > 100 ? '...' : ''}
        </p>
      </div>

      <div className="bug-card-footer">
        <div className="bug-meta">
          <span className="assignee">
            👤 {bug.assignee?.name || 'Unassigned'}
          </span>
          <span className="created-date">
            📅 {formatDate(bug.createdAt)}
          </span>
          {bug.commentCount > 0 && (
            <span className="comment-count">
                Comment Count: {bug.commentCount}
            </span>
          )}
        </div>

        <div className="status-actions" onClick={(e) => e.stopPropagation()}>
          <select 
            value={bug.status} 
            onChange={(e) => handleStatusChange(e, e.target.value)}
            className="status-select"
          >
            <option value="NEW">New</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="FIXED">Fixed</option>
            <option value="VERIFIED">Verified</option>
          </select>
        </div>
      </div>
    </div>
  );
};

export default BugCard;