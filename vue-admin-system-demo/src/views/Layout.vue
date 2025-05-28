<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="220px" class="sidebar">
      <div class="logo">
        <img src="@/assets/imgs/logo.png" alt="Logo" />
        <span>管理系统</span>
      </div>

      <el-menu
          :default-active="activeMenu"
          router
          unique-opened
          background-color="#ffffff"
          text-color="#333"
          active-text-color="#409EFF"
          class="menu"
      >
        <menu-tree
            v-for="menu in data.permissions"
            :key="menu.id"
            :item="menu"
        />
      </el-menu>
    </el-aside>

    <!-- 右侧主内容 -->
    <el-container class="main-wrapper">
      <!-- 头部 -->
      <el-header class="header">
        <div class="left-area">
          <h2 class="page-title">{{ pageTitle }}</h2>
        </div>

        <div class="right-area">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              {{ data.realName }}
              <el-icon name="arrow-down" class="arrow-down"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主体内容 -->
      <el-main class="content">
        <RouterView/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import MenuTree from '@/components/layout/MenuTree.vue'
import request from '@/utils/request.js'

const router = useRouter()
const route = useRoute()

const data = reactive({
  permissions: [],
  realName: ''
})

// 当前激活菜单
const activeMenu = computed(() => {
  return route.path
})

// 页面标题
const pageTitle = computed(() => {
  return route.meta.title || route.name || '仪表盘'
})

// 获取用户权限菜单和信息
const binding = () => {
  request.post('/api/userMenus').then(res => {
    if (res.code === 200) {
      data.permissions = res.data
      const user = localStorage.getItem('session_user')
      if (user) {
        data.realName = JSON.parse(user).realName
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}

onMounted(() => {
  binding()
})

// 用户操作
const handleCommand = (command) => {
  switch (command) {
    case 'logout':
      localStorage.removeItem('token')
      localStorage.removeItem('expires_at')
      localStorage.removeItem('session_user')
      router.push('/login')
      break
    case 'profile':
      router.push('/profile')
      break
  }
}
</script>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .sidebar {
    background-color: #fff;
    box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;

    .logo {
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 20px 0;
      font-size: 18px;
      font-weight: bold;
      color: #333;

      img {
        width: 32px;
        height: 32px;
        margin-right: 10px;
      }
    }

    .menu {
      border-right: none;
      flex: 1;
      overflow-y: auto;
    }
  }

  .main-wrapper {
    flex-direction: column;
    overflow: hidden;
  }

  .header {
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #e6e6e6;
    padding: 0 20px;

    .page-title {
      margin: 0;
      font-size: 18px;
      font-weight: normal;
      color: #333;
    }

    .user-info {
      cursor: pointer;
      font-size: 14px;
      color: #666;

      &:hover {
        color: #409EFF;
      }

      .arrow-down {
        margin-left: 5px;
        transition: transform 0.3s ease;
      }

      &:hover .arrow-down {
        transform: rotate(180deg);
      }
    }
  }

  .content {
    background: #f5f7fa;
    padding: 20px;
    overflow: auto;
  }
}
</style>