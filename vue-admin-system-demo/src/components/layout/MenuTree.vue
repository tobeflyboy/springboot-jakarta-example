<template>
  <div>
    <el-sub-menu v-if="hasChildren(item)" :index="item.id" :key="item.id">
      <template #title>
        <el-icon v-if="item.icon">
          <component :is="resolveIcon(item.icon)" />
        </el-icon>
        <span>{{ item.permissionName }}</span>
      </template>
      <!-- 递归渲染子菜单 -->
      <menu-tree
          v-for="child in item.children"
          :key="child.id"
          :item="child"
      />
    </el-sub-menu>

    <!-- 叶子菜单 -->
    <el-menu-item v-else :index="item.url">
      <el-icon v-if="item.icon">
        <component :is="resolveIcon(item.icon)" />
      </el-icon>
      <span>{{ item.permissionName }}</span>
    </el-menu-item>
  </div>
</template>

<script setup>

defineProps(['item']);

// 判断是否有子菜单
const hasChildren = (item) => {
  return item.children && Array.isArray(item.children) && item.children.length > 0
}

// 直接返回图标名称（如 "HomeFilled"），由 Element Plus 全局注册
const resolveIcon = (iconName) => {
  if (!iconName) return null
  return iconName
}
</script>