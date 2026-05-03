import axios from 'axios';

const API = axios.create({
  baseURL: '/api',
});

API.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

API.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      localStorage.removeItem('token');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authAPI = {
  signup: (data) => API.post('/auth/signup', data),
  login: (data) => API.post('/auth/login', data),
  refresh: (data) => API.post('/auth/refresh', data),
};

export const complaintsAPI = {
  getAll: () => API.get('/complaints'),
  getById: (id) => API.get(`/complaints/${id}`),
  create: (data) => API.post('/complaints/create', data),
  delete: (id) => API.delete(`/complaints/${id}`),
};

export const votesAPI = {
  create: (data) => API.post('/votes/create', data),
  getById: (id) => API.get(`/votes/${id}`),
  update: (id, data) => API.put(`/votes/${id}`, data),
  delete: (id) => API.delete(`/votes/${id}`),
};

export const usersAPI = {
  getAll: () => API.get('/v1/auth/getAll'),
  getById: (id) => API.get(`/v1/auth/getById/${id}`),
  update: (data) => API.put('/v1/auth/update', data),
  delete: (id) => API.delete(`/v1/auth/${id}`),
};

export const leaderboardAPI = {
  get: () => API.get('/leaderboard'),
};

export const statsAPI = {
  get: () => API.get('/flat/stats'),
};

export default API;
