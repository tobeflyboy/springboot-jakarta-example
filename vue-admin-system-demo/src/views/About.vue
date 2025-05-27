<template>
  <div class="about-container">
    <el-card shadow="always" style="width: 100%;">
      <template #header>
        <h2 style="margin: 0; font-size: 24px;">关于本系统</h2>
      </template>

      <el-row :gutter="20">
        <el-col :span="24">
          <p>这是一个基于 Vue 3 和 Element Plus 构建的现代化后台管理系统。</p>
          <p>本系统旨在为开发者提供一套结构清晰、易于扩展、界面友好的管理后台模板，适用于各类企业级管理平台开发。</p>
        </el-col>
      </el-row>

      <el-divider />

      <el-row :gutter="20">
        <el-col :span="24">
          <h3>技术栈</h3>
          <ul>
            <li><strong>前端框架：</strong>Vue 3 + Vite + Vue Router + Pinia</li>
            <li><strong>UI 框架：</strong>Element Plus</li>
            <li><strong>构建工具：</strong>Vite</li>
            <li><strong>HTTP 请求库：</strong>Axios</li>
            <li><strong>样式预处理器：</strong>SCSS / CSS Modules（可选）</li>
          </ul>
        </el-col>
      </el-row>

      <el-divider />

      <el-row :gutter="20">
        <el-col :span="24">
          <h3>功能特性</h3>
          <ul>
            <li>响应式布局，适配 PC 端与移动端</li>
            <li>模块化组件设计，便于复用与维护</li>
            <li>基础权限控制（可根据需求扩展）</li>
            <li>数据可视化支持（如 ECharts 集成）</li>
            <li>可接入真实后端接口，实现 CRUD 操作</li>
          </ul>
        </el-col>
      </el-row>

      <el-divider />

      <el-row :gutter="20">
        <el-col :span="24">
          <h3>开发者信息</h3>
          <p>作者：胡桃夹子</p>
          <p>Github：https://github.com/zhangsan/admin-system</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
// 当前页面不需要复杂逻辑，仅展示信息即可
</script>

<style scoped>
h3 {
  margin-top: 0;
}

ul {
  padding-left: 20px;
}
</style>