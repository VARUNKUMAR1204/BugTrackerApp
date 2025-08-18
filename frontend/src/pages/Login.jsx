import React, { useState } from 'react';
import { authAPI } from '../services/api';

const Login = ({ onLogin }) => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (isLogin) {
       
        const response = await authAPI.login({
          email: formData.email,
          password: formData.password
        });
        
        if (response.data.success) {
          onLogin(response.data.data);
        } else {
          setError(response.data.message || 'Login failed');
        }
      } else {
       
        const registerResponse = await authAPI.register({
          name: formData.name,
          email: formData.email,
          password: formData.password
        });
        
        if (registerResponse.data.success) {
          
          const loginResponse = await authAPI.login({
            email: formData.email,
            password: formData.password
          });
          
          if (loginResponse.data.success) {
            onLogin(loginResponse.data.data);
          }
        }
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Authentication failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h1> Bug Tracker</h1>
          <p>Track bugs, manage workflow, stay organized</p>
        </div>
        
        <div className="login-tabs">
          <button 
            className={`tab ${isLogin ? 'active' : ''}`}
            onClick={() => setIsLogin(true)}
          >
            Login
          </button>
          <button 
            className={`tab ${!isLogin ? 'active' : ''}`}
            onClick={() => setIsLogin(false)}
          >
            Register
          </button>
        </div>

        <form onSubmit={handleSubmit} className="login-form">
          {!isLogin && (
            <div className="form-group">
              <label>Name</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                placeholder="Enter your name"
                required={!isLogin}
              />
            </div>
          )}
          
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="Enter your email"
              required
            />
          </div>
          
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="Enter your password"
              required
              minLength="6"
            />
          </div>

          {error && <div className="error-message">{error}</div>}
          
          <button type="submit" className="login-button" disabled={loading}>
            {loading ? 'Please wait...' : (isLogin ? 'Login' : 'Register')}
          </button>
        </form>
        
        <div className="login-footer">
          <p>
            {isLogin ? "Don't have an account? " : "Already have an account? "}
            <span 
              className="link" 
              onClick={() => setIsLogin(!isLogin)}
            >
              {isLogin ? 'Register here' : 'Login here'}
            </span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;