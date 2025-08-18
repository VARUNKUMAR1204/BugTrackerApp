import React from 'react';
import BugCard from './BugCard';

const BugList = ({ bugs, users, onBugClick, onStatusChange }) => {
  if (bugs.length === 0) {
    return (
      <div className="empty-state">
        <h3>No bugs found</h3>
        <p>Create a new bug or adjust your filters</p>
      </div>
    );
  }

  return (
    <div className="bug-list">
      <div className="bug-list-header">
        <span className="bug-count">Showing {bugs.length} bugs</span>
      </div>
      
      <div className="bug-grid">
        {bugs.map(bug => (
          <BugCard
            key={bug.id}
            bug={bug}
            onClick={() => onBugClick(bug)}
            onStatusChange={onStatusChange}
          />
        ))}
      </div>
    </div>
  );
};

export default BugList;