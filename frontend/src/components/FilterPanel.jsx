import React from 'react';

const FilterPanel = ({ filters, onFiltersChange, bugCount }) => {
  const handleFilterChange = (filterType, value) => {
    onFiltersChange({
      ...filters,
      [filterType]: value
    });
  };

  const clearFilters = () => {
    onFiltersChange({
      severity: '',
      status: ''
    });
  };

  return (
    <div className="filter-panel">
      <div className="filter-group">
        <label>Severity:</label>
        <select 
          value={filters.severity}
          onChange={(e) => handleFilterChange('severity', e.target.value)}
        >
          <option value="">All Severities</option>
          <option value="HIGH">High</option>
          <option value="MEDIUM">Medium</option>
          <option value="LOW">Lo</option>
        </select>
      </div>

      <div className="filter-group">
        <label>Status:</label>
        <select 
          value={filters.status}
          onChange={(e) => handleFilterChange('status', e.target.value)}
        >
          <option value="">All Statuses</option>
          <option value="NEW">New</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="FIXED">Fixed</option>
          <option value="VERIFIED">Verified</option>
        </select>
      </div>

      <div className="filter-results">
        <span>{bugCount} bugs found</span>
        {(filters.severity || filters.status) && (
          <button className="clear-filters" onClick={clearFilters}>Clear Filters
          </button>
        )}
      </div>
    </div>
  );
};

export default FilterPanel;
