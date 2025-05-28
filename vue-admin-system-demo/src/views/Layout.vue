<template>
  <div class="layout-wrapper">
    <!-- 头部区域 -->
    <header class="header">
      <div class="logo-container">
        <img src="@/assets/imgs/logo.png" alt="Logo" class="logo"/>
        <span class="system-title">后台管理系统</span>
      </div>
      <nav class="navigation">
        <span @click="router.push('/manager/home')">首页</span> /
        <span v-if="currentMeta">{{ currentMeta.name }}</span>
      </nav>
      <el-dropdown style="cursor: pointer;">
        <div class="user-info">
          <img src="@/assets/imgs/default-avatar.jpg" alt="User Avatar" class="avatar"/>
          <span>{{ data.realName }}</span>
          <el-icon class="arrow-down">
            <ArrowDown/>
          </el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
            <el-dropdown-item>修改密码</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </header>

    <!-- 主体内容 -->
    <div class="main-content">
      <!-- 菜单区域 -->
      <aside class="sidebar">
        <el-menu
            router
            unique-opened
            :default-active="router.currentRoute.value.path"
            background-color="#ffffff"
            text-color="#333"
            active-text-color="#4a90e2"
            class="menu"
        >
          <!-- 动态菜单 -->
          <menu-tree
              v-for="menu in data.permissions"
              :key="menu.id"
              :item="menu"
          />
        </el-menu>
      </aside>

      <!-- 内容区域 -->
      <main class="content">
        <RouterView/>
      </main>
    </div>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from 'vue-router'
import {computed, reactive} from 'vue'
import request from '@/utils/request.js'
import {ElMessage} from 'element-plus'
import MenuTree from '@/components/layout/MenuTree.vue'

const data = reactive({
  permissions: [],
  realName: ''
})

const route = useRoute()
const router = useRouter()

const currentMeta = computed(() => route.meta)

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('expires_at')
  localStorage.removeItem('session_user')
  location.href = '/login'
}

const binding = () => {
  request.post('/api/userMenus').then(res => {
    if (res.code === 200) {
      data.permissions = res.data
      let user = localStorage.getItem('session_user');
      if (user) {
        data.realName = JSON.parse(user).realName
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
binding()
</script>

<style scoped lang="scss">
.layout-wrapper {
  height: 100vh;
  background-color: #f8f9fa;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    padding: 0 20px;
    background-color: #ffffff;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
    z-index: 1000;

    .logo-container {
      display: flex;
      align-items: center;

      .logo {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 10px;
      }

      .system-title {
        font-size: 20px;
        font-weight: bold;
        color: #2b2d42;
      }
    }

    .navigation {
      span {
        cursor: pointer;

        &:hover {
          color: #4a4e69;
        }
      }
    }

    .user-info {
      display: flex;
      align-items: center;

      .avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 10px;
      }

      span {
        margin-right: 5px;
      }

      .arrow-down {
        transition: transform 0.3s ease;
      }

      &:hover .arrow-down {
        transform: rotate(180deg);
      }
    }
  }

  .main-content {
    display: flex;
    height: calc(100% - 60px);

    .sidebar {
      width: 240px;
      background-color: #ffffff;
      box-shadow: 0 0 8px rgba(0, 0, 0, 0.12);
      overflow-y: auto;

      .menu {
        min-height: calc(100vh - 60px);
      }
    }

    .content {
      flex: 1;
      padding: 20px;
      background-color: #f8f9fa;
      overflow-y: auto;
    }
  }
}
</style>