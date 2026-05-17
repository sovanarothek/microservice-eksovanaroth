<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-md">

      <!-- Logo -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-primary">🍔 LomaFood</h1>
        <p class="text-gray-500 mt-2">Reset your password</p>
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
      <form @submit.prevent="handleReset" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">New Password</label>
          <input
            v-model="form.newPassword"
            type="password"
            placeholder="Min 6 characters"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary"
            required
            minlength="6"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Confirm Password</label>
          <input
            v-model="confirmPassword"
            type="password"
            placeholder="Repeat your password"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary"
            required
          />
        </div>

        <button
          type="submit"
          :disabled="auth.loading"
          class="w-full bg-primary text-white py-3 rounded-lg font-semibold hover:bg-orange-600 transition disabled:opacity-50"
        >
          {{ auth.loading ? 'Resetting...' : 'Reset Password' }}
        </button>
      </form>

      <p class="text-center text-sm text-gray-500 mt-6">
        <router-link to="/login" class="text-primary hover:underline">
          Back to Login
        </router-link>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const form = ref({ token: route.query.token || '', newPassword: '' })
const confirmPassword = ref('')
const error = ref('')
const success = ref('')

async function handleReset() {
  error.value = ''
  success.value = ''

  if (form.value.newPassword !== confirmPassword.value) {
    error.value = 'Passwords do not match'
    return
  }

  if (!form.value.token) {
    error.value = 'Invalid or missing reset token'
    return
  }

  try {
    const res = await auth.resetPassword(form.value)
    success.value = res.message
    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (e) {
    error.value = e.response?.data?.message || 'Password reset failed'
  }
}
</script>
