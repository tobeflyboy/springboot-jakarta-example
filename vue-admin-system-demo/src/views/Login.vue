<template>
  <div class="login-wrapper">
    <!-- 登录表单容器 -->
    <div class="login-container">
      <!-- 品牌头部 -->
      <div class="login-header">
        <h1 class="title">Admin System</h1>
        <h3 class="subtitle">Professional Management Platform</h3>
      </div>

      <!-- 登录表单 -->
      <el-form
          ref="loginFormRef"
          :model="data.loginForm"
          :rules="data.loginFormRules"
          label-width="auto"
          class="login-form"
      >
        <el-form-item size="large" prop="username">
          <el-input
              v-model="data.loginForm.username"
              placeholder="请输入账号"
              prefix-icon="User"
              class="custom-input"
          />
        </el-form-item>

        <el-form-item size="large" prop="password">
          <el-input
              v-model="data.loginForm.password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              type="password"
              show-password
              autocomplete="off"
              class="custom-input"
          />
        </el-form-item>

        <el-button
            type="primary"
            size="large"
            class="login-btn"
            @click="login"
            :loading="data.loading"
        >
          {{ data.loading ? '登录中...' : '登录' }}
        </el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request.js'
import { trim } from '@/utils/common.js'

const data = reactive({
  loginForm: {
    // 这里写死账号与密码，只是为了方便测试
    username: 'admin',
    password: '123456'
  },
  loading: false,
  loginFormRules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ]
  }
})

const loginFormRef = ref()

let lastLoginTime = 0;

const login = () => {
  const now = Date.now();
  if (now - lastLoginTime < 5000) {
    ElMessage.warning('操作太频繁，请稍后再试');
    return;
  }
  lastLoginTime = now;

  loginFormRef.value.validate((valid) => {
    if (valid) {
      data.loading = true;
      const user = {
        username: trim(data.loginForm.username),
        password: trim(data.loginForm.password)
      }
      request.post('/api/login', user).then(res => {
        if (res.code === 200) {
          ElMessage.success('登录成功!')
          localStorage.setItem('token', res.data.token)
          localStorage.setItem('expires_at', res.data.expiresAt)
          localStorage.setItem('session_user', JSON.stringify(res.data.user) || {})
          location.href = '/'
        } else {
          ElMessage.error(res.msg)
        }
      }).finally(()=>{
        data.loading = false;
      })
    }
  })
}


function handleEnter(e) {
  if (e.key === 'Enter') {
    login()
  }
}

onMounted(() => {
  window.addEventListener('keydown', handleEnter)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleEnter)
})
</script>

<style scoped lang="scss">
.login-wrapper {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background: repeating-linear-gradient(
            45deg,
            rgba(108, 117, 125, 0.03) 0,
            rgba(108, 117, 125, 0.03) 1px,
            transparent 1px,
            transparent 8px
    );
  }
}

.login-container {
  position: relative;
  z-index: 1;
  background: rgba(255, 255, 255, 0.98);
  border-radius: 16px;
  padding: 40px 50px;
  width: 100%;
  max-width: 440px;
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  opacity: 0;
  animation: fadeInUp 0.8s ease forwards;

  &:hover {
    transform: translateY(-2px);
  }
}

.login-header {
  text-align: center;
  margin-bottom: 40px;

  .title {
    font-size: 28px;
    color: #2b2d42;
    margin: 0 0 12px;
    font-weight: 600;
    letter-spacing: 0.5px;
    text-shadow: 0 2px 4px rgba(0,0,0,0.05);
  }

  .subtitle {
    color: #6c757d;
    font-size: 16px;
    font-weight: 400;
    margin: 0;
    letter-spacing: 0.3px;
  }
}

:deep(.custom-input) {
  .el-input__wrapper {
    border-radius: 8px;
    background: #f8f9fa;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.03);
    transition: all 0.2s ease;

    &:hover {
      background: #fff;
      box-shadow: 0 3px 6px rgba(0, 0, 0, 0.05);
    }

    &.is-focus {
      background: #fff;
      box-shadow: 0 4px 8px rgba(43, 45, 66, 0.1);
    }
  }

  .el-input__inner {
    color: #495057;
    font-weight: 500;
  }

  .el-icon {
    color: #6c757d;
  }
}

.login-btn {
  width: 100%;
  background: #4a4e69;
  border: none;
  border-radius: 8px;
  font-weight: 500;
  letter-spacing: 0.5px;
  transition: all 0.2s ease;
  height: 46px;
  margin-top: 10px;

  &:hover {
    background: #5c617e;
    box-shadow: 0 4px 12px rgba(74, 78, 105, 0.15);
    transform: translateY(-1px);
  }

  &:active {
    transform: translateY(0);
  }
}

@media (max-width: 768px) {
  .login-container {
    padding: 30px;
    margin: 0 20px;
    border-radius: 12px;

    .login-header {
      margin-bottom: 30px;

      .title {
        font-size: 24px;
      }

      .subtitle {
        font-size: 14px;
      }
    }
  }

  .login-btn {
    height: 44px;
  }
}
@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>