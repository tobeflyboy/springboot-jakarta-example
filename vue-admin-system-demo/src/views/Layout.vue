<template>
  <div class="layout-wrapper">
    <!-- 头部区域开始 -->
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
          <el-icon class="arrow-down"><arrow-down/></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
            <el-dropdown-item>修改密码</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </header>
    <!-- 头部区域结束 -->

    <!-- 下方区域开始 -->
    <div class="main-content">
      <!-- 菜单区域开始 -->
      <aside class="sidebar">
        <el-menu router :default-active="router.currentRoute.value.path" class="menu">
          <el-menu-item index="/manager/dashboard">
            <el-icon><HomeFilled/></el-icon>
            <span>Dashboard</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Setting/></el-icon>
              <span>权限管理</span>
            </template>
            <el-menu-item index="/manager/permission">
              <el-icon><Files/></el-icon>
              <span>资源管理</span>
            </el-menu-item>
            <el-menu-item index="/manager/role">
              <el-icon><Finished/></el-icon>
              <span>角色管理</span>
            </el-menu-item>
            <el-menu-item index="/manager/user">
              <el-icon><UserFilled/></el-icon>
              <span>用户管理</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/manager/about">
            <el-icon><InfoFilled/></el-icon>
            <span>关于</span>
          </el-menu-item>
        </el-menu>
      </aside>
      <!-- 菜单区域结束 -->

      <!-- 数据渲染区域开始 -->
      <main class="content">
        <RouterView/>
      </main>
      <!-- 数据渲染区域结束 -->
    </div>
    <!-- 下方区域结束 -->
  </div>
</template>

<script setup>
import {useRoute} from 'vue-router';
import {computed, reactive} from 'vue';
import request from '@/utils/request.js'
import router from "@/router/index.js";
import { ElMessage } from 'element-plus'

const data = reactive({
  realName: null
})

// 获取当前路由对象
const route = useRoute();

// 计算属性来获取当前路由的 meta 数据
const currentMeta = computed(() => route.meta);

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('expires_at');
  localStorage.removeItem('session_user');
  location.href = '/login';
}

const getUserInfo = () => {
  request.post('/api/userInfo').then(res => {
    if (res.code === 200) {
      data.realName = res.data.realName;
    } else {
      ElMessage.error(res.msg)
    }
  })
}
getUserInfo()
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