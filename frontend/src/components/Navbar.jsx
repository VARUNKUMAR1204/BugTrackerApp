import React from 'react';

const Navbar = ({ user, onLogout }) => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <h2>Bug Tracker</h2>
      </div>
      <div className="navbar-user">
        <span className="welcome-text">Welcome, {user?.name}</span>
        <button className="logout-button" onClick={onLogout}>Logout</button>
      </div>
    </nav>
  );
};

export default Navbar;