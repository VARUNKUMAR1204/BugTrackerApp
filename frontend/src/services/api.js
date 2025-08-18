import axios from 'axios';

const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? 'bugtrackerapp-production.up.railway.app/api'
  : 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const authAPI = {
  register: (userData) => api.post('/auth/register', userData),
  login: (credentials) => api.post('/auth/login', credentials),
};

//Bugs Relatedd
export const bugAPI = {
  getAllBugs: (filters = {}) => {
    const params = new URLSearchParams();
    if (filters.severity) params.append('severity', filters.severity);
    if (filters.status) params.append('status', filters.status);
    return api.get(`/bugs?${params.toString()}`);
  },
  
  getHighPriorityNewBugs: () => api.get('/bugs/high-priority-new'),
  
  getBugById: (id) => api.get(`/bugs/${id}`),
  
  createBug: (bugData) => api.post('/bugs', bugData),
  
  updateBugStatus: (id, status) => api.put(`/bugs/${id}/status`, { status }),
  
  updateBug: (id, bugData) => api.put(`/bugs/${id}`, bugData),
  
  deleteBug: (id) => api.delete(`/bugs/${id}`),
};

//Comments R
export const commentAPI = {
  getCommentsByBugId: (bugId) => api.get(`/comments/bug/${bugId}`),
  
  addComment: (bugId, commentData, authorId) => {
    const url = authorId 
      ? `/comments/bug/${bugId}?authorId=${authorId}` 
      : `/comments/bug/${bugId}`;
    return api.post(url, commentData);
  },
  
  deleteComment: (id) => api.delete(`/comments/${id}`),
};

export const userAPI = {
  getAllUsers: () => api.get('/users'),
  getUserById: (id) => api.get(`/users/${id}`),
};

//tset
export const testAPI = {
  hello: () => api.get('/test/hello'),
  completeWorkflow: () => api.post('/test/complete-workflow'),
  assignmentSummary: () => api.get('/test/assignment-summary'),
};

export default api;