import { defineStore } from 'pinia'
import api from '../services/api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: JSON.parse(localStorage.getItem('user') || 'null'),
    accessToken: localStorage.getItem('accessToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    loading: false,
    error: null
  }),

  getters: {
    isLoggedIn: (state) => !!state.accessToken,
    userRole: (state) => state.user?.role || null
  },

  actions: {
    async register(data) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/auth/register', data)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'Registration failed'
        throw e
      } finally {
        this.loading = false
      }
    },

    async verifyOtp(data) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/auth/verify-otp', data)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'OTP verification failed'
        throw e
      } finally {
        this.loading = false
      }
    },

    async resendOtp(email) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post(`/auth/resend-otp?email=${email}`)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'Resend OTP failed'
        throw e
      } finally {
        this.loading = false
      }
    },

    async login(data) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/auth/login', data)
        const { accessToken, refreshToken } = res.data.data
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        localStorage.setItem('accessToken', accessToken)
        localStorage.setItem('refreshToken', refreshToken)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'Login failed'
        throw e
      } finally {
        this.loading = false
      }
    },

    async forgotPassword(email) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post(`/auth/forgot-password?email=${email}`)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'Failed to send reset link'
        throw e
      } finally {
        this.loading = false
      }
    },

    async resetPassword(data) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/auth/reset-password', data)
        return res.data
      } catch (e) {
        this.error = e.response?.data?.message || 'Password reset failed'
        throw e
      } finally {
        this.loading = false
      }
    },

    async logout() {
      this.loading = true
      try {
        await api.post('/auth/logout')
      } finally {
        this.accessToken = null
        this.refreshToken = null
        this.user = null
        localStorage.clear()
        this.loading = false
      }
    }
  }
})
