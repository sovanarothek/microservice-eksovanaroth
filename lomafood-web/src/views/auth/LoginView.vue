<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-md">

      <!-- Logo -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-primary">🍔 LomaFood</h1>
        <p class="text-gray-500 mt-2">Sign in to your account</p>
      </div>

      <!-- Error -->
      <div v-if="error" class="bg-red-50 text-red-600 p-3 rounded-lg mb-4 text-sm">
        {{ error }}
      </div>

      <!-- Form -->
      <form @submit.prevent="handleLogin" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
          <input
            v-model="form.email"
            type="email"
            placeholder="you@example.com"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary"
            required
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Password</label>
          <input
            v-model="form.password"
            type="password"
            placeholder="••••••••"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary"
            required
          />
        </div>

        <!-- Forgot Password -->
        <div class="text-right">
          <router-link to="/forgot-password" class="text-sm text-primary hover:underline">
            Forgot password?
          </router-link>
        </div>

        <!-- Submit -->
        <button
          type="submit"
          :disabled="auth.loading"
          class="w-full bg-primary text-white py-3 rounded-lg font-semibold hover:bg-orange-600 transition disabled:opacity-50"
        >
          {{ auth.loading ? 'Signing in...' : 'Sign In' }}
        </button>
      </form>

      <!-- Google Login -->
      <div class="mt-4">
        <button
          @click="handleGoogleLogin"
          class="w-full border border-gray-300 py-3 rounded-lg font-semibold flex items-center justify-center gap-2 hover:bg-gray-50 transition"
        >
          <img src="https://www.google.com/favicon.ico" class="w-5 h-5" />
          Continue with Google
        </button>
      </div>

      <!-- Register -->
      <p class="text-center text-sm text-gray-500 mt-6">
        Don't have an account?
        <router-link to="/register" class="text-primary font-semibold hover:underline">
          Sign Up
        </router-link>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const form = ref({ email: '', password: '' })
const error = ref('')

async function handleLogin() {
  error.value = ''
  try {
    await auth.login(form.value)
    router.push('/home')
  } catch (e) {
    error.value = e.response?.data?.message || 'Login failed'
  }
}

function handleGoogleLogin() {
  // Google OAuth2 - to be implemented
  alert('Google login coming soon!')
}
</script>
