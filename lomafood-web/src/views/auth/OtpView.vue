<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-md">

      <!-- Logo -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-primary">🍔 LomaFood</h1>
        <p class="text-gray-500 mt-2">Verify your email</p>
        <p class="text-sm text-gray-400 mt-1">OTP sent to <strong>{{ email }}</strong></p>
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
      <form @submit.prevent="handleVerify" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Enter OTP</label>
          <input
            v-model="otp"
            type="text"
            placeholder="Enter 6-digit OTP"
            maxlength="6"
            class="w-full border border-gray-300 rounded-lg px-4 py-3 text-center text-2xl tracking-widest focus:outline-none focus:ring-2 focus:ring-primary"
            required
          />
        </div>

        <!-- Submit -->
        <button
          type="submit"
          :disabled="auth.loading"
          class="w-full bg-primary text-white py-3 rounded-lg font-semibold hover:bg-orange-600 transition disabled:opacity-50"
        >
          {{ auth.loading ? 'Verifying...' : 'Verify OTP' }}
        </button>
      </form>

      <!-- Resend OTP -->
      <div class="text-center mt-4">
        <p class="text-sm text-gray-500">
          Didn't receive OTP?
          <button
            @click="handleResend"
            :disabled="resendCooldown > 0"
            class="text-primary font-semibold hover:underline disabled:opacity-50"
          >
            {{ resendCooldown > 0 ? `Resend in ${resendCooldown}s` : 'Resend OTP' }}
          </button>
        </p>
      </div>

      <!-- Back to login -->
      <p class="text-center text-sm text-gray-500 mt-4">
        <router-link to="/login" class="text-primary hover:underline">
          Back to Login
        </router-link>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const email = ref(route.query.email || '')
const otp = ref('')
const error = ref('')
const success = ref('')
const resendCooldown = ref(0)
let timer = null

async function handleVerify() {
  error.value = ''
  success.value = ''
  try {
    const res = await auth.verifyOtp({ email: email.value, otp: otp.value })
    success.value = res.message
    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (e) {
    error.value = e.response?.data?.message || 'OTP verification failed'
  }
}

async function handleResend() {
  error.value = ''
  success.value = ''
  try {
    const res = await auth.resendOtp(email.value)
    success.value = res.message
    startCooldown()
  } catch (e) {
    error.value = e.response?.data?.message || 'Failed to resend OTP'
  }
}

function startCooldown() {
  resendCooldown.value = 60
  timer = setInterval(() => {
    resendCooldown.value--
    if (resendCooldown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

onMounted(() => startCooldown())
onUnmounted(() => clearInterval(timer))
</script>
