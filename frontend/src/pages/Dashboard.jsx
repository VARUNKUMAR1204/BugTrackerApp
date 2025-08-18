import React, { useState, useEffect } from 'react';
import BugList from '../components/BugList';
import CreateBugModal from '../components/CreateBugModal';
import BugModal from '../components/BugModal';
import FilterPanel from '../components/FilterPanel';
import { bugAPI, userAPI } from '../services/api';

const Dashboard = ({ user }) => {
  const [bugs, setBugs] = useState([]);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [selectedBug, setSelectedBug] = useState(null);
  const [filters, setFilters] = useState({
    severity: '',
    status: ''
  });

  // Loads the bugs and users 
  useEffect(() => {
    loadInitialData();
  }, []);

  // Reloads bugs when filters change
  useEffect(() => {
    loadBugs();
  }, [filters]);

  const loadInitialData = async () => {
    try {
      const [bugsResponse, usersResponse] = await Promise.all([
        bugAPI.getAllBugs(),
        userAPI.getAllUsers()
      ]);

      if (bugsResponse.data.success) {
        setBugs(bugsResponse.data.data);
      }
      if (usersResponse.data.success) {
        setUsers(usersResponse.data.data);
      }
    } catch (error) {
      console.error('Error loading initial data:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadBugs = async () => {
    try {
      const response = await bugAPI.getAllBugs(filters);
      if (response.data.success) {
        setBugs(response.data.data);
      }
    } catch (error) {
      console.error('Error loading bugs:', error);
    }
  };

  const handleCreateBug = async (bugData) => {
    try {
      const response = await bugAPI.createBug(bugData);
      if (response.data.success) {
        setBugs([response.data.data, ...bugs]);
        setShowCreateModal(false);
      }
    } catch (error) {
      console.error('Error creating bug:', error);
      alert('Failed to create bug');
    }
  };

  const handleStatusChange = async (bugId, newStatus) => {
    try {
      const response = await bugAPI.updateBugStatus(bugId, newStatus);
      if (response.data.success) {
        setBugs(bugs.map(bug => 
          bug.id === bugId ? response.data.data : bug
        ));
        
        
        if (selectedBug && selectedBug.id === bugId) {
          setSelectedBug(response.data.data);
        }
      }
    } catch (error) {
      console.error('Error updating bug status:', error);
      alert('Failed to update status');
    }
  };

  const handleHighPriorityFilter = async () => {
    try {
      const response = await bugAPI.getHighPriorityNewBugs();
      if (response.data.success) {
        setBugs(response.data.data);
        setFilters({ severity: 'HIGH', status: 'NEW' });
      }
    } catch (error) {
      console.error('Error loading high priority bugs:', error);
    }
  };

  if (loading) {
    return <div className="loading">Loading bugs...</div>;
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Bug Dashboard</h1>
        <div className="dashboard-actions">
          <button 
            className="btn-primary" 
            onClick={() => setShowCreateModal(true)}
          >
            + Add New Bug
          </button>
          <button 
            className="btn-warning" 
            onClick={handleHighPriorityFilter}
          >
             High Priority + New
          </button>
        </div>
      </div>

      <FilterPanel 
        filters={filters}
        onFiltersChange={setFilters}
        bugCount={bugs.length}
      />

      <BugList 
        bugs={bugs}
        users={users}
        onBugClick={setSelectedBug}
        onStatusChange={handleStatusChange}
      />

      {showCreateModal && (
        <CreateBugModal
          users={users}
          onClose={() => setShowCreateModal(false)}
          onSubmit={handleCreateBug}
        />
      )}

      {selectedBug && (
        <BugModal
          bug={selectedBug}
          users={users}
          currentUser={user}
          onClose={() => setSelectedBug(null)}
          onStatusChange={handleStatusChange}
        />
      )}
    </div>
  );
};

export default Dashboard;
