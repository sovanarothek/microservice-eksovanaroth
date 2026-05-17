<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-md">

      <!-- Logo -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-primary">🍔 LomaFood</h1>
        <p class="text-gray-500 mt-2">Forgot your password?</p>
        <p class="text-sm text-gray-400 mt-1">Enter your email to receive a reset link</p>
      </div>

      <!-- Error -->
      <div v-if="error" class="bg-red-50 text-red-600 p-3 rounded-lg mb-4 text-sm">
        {{ error }}
      </div>

      <!-- Success -->
      <div v-if="success" class="bg-green-50 text-green-600 p-3 rounded-lg mb-4 text-sm">
        {{ success }}
      </div>

      <!-- Form -->
      <form @submit.prevent="handleForgot" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
          <input
            v-model="email"
            type="email"
            placeholder="you@example.com"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary"
            required
          />
        </div>

        <button
          type="submit"
          :disabled="auth.loading"
          class="w-full bg-primary text-white py-3 rounded-lg font-semibold hover:bg-orange-600 transition disabled:opacity-50"
        >
          {{ auth.loading ? 'Sending...' : 'Send Reset Link' }}
        </button>
      </form>

      <p class="text-center text-sm text-gray-500 mt-6">
        Remember your password?
        <router-link to="/login" class="text-primary font-semibold hover:underline">
          Sign In
        </router-link>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const email = ref('')
const error = ref('')
const success = ref('')

async function handleForgot() {
  error.value = ''
  success.value = ''
  try {
    const res = await auth.forgotPassword(email.value)
    success.value = res.message
    email.value = ''
  } catch (e) {
    error.value = e.response?.data?.message || 'Failed to send reset link'
  }
}
</script>
