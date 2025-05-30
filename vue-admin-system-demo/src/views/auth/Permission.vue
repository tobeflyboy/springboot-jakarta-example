<template>
  <div class="permission-page">
    <!-- 工具栏 -->
    <el-card class="toolbar" shadow="never">
      <el-button type="success" @click="addPermissionDialog">新增一级菜单</el-button>
    </el-card>

    <!-- 权限树 -->
    <el-card class="tree-container" shadow="hover">
      <el-tree
          :data="data.permissionTree"
          node-key="id"
          default-expand-all
          :props="data.defaultProps"
      >
        <template #default="{ node, data }">
          <span class="tree-node-label">
            <el-icon v-if="data.icon">
              <component :is="data.icon" />
            </el-icon>
            {{ node.label }}
          </span>
          <div class="tree-node-actions">
            <el-button link type="primary" @click="showAddPermissionDialog(data)">添加</el-button>
            <el-button link type="primary" @click="edit(data)">编辑</el-button>
            <el-button link type="danger" @click="remove(node, data)">删除</el-button>
          </div>
        </template>
      </el-tree>
    </el-card>

    <!-- 菜单表单弹窗 -->
    <el-dialog
        :title="permissionForm.title"
        v-model="permissionFormVisible"
        width="35%"
        center
        destroy-on-close
    >
      <el-form ref="formRef" :model="permissionForm" :rules="rules" label-width="120px">
        <input type="hidden" v-model="permissionForm.id" />

        <el-form-item label="菜单名称：" prop="permissionName">
          <el-input v-model="permissionForm.permissionName" placeholder="请输入菜单名称" />
        </el-form-item>

        <el-form-item label="菜单编码：" prop="permissionCode">
          <el-input v-model="permissionForm.permissionCode" placeholder="请输入菜单编码" />
        </el-form-item>

        <el-form-item label="父菜单编码：" prop="parentPermissionCode">
          <el-input v-model="permissionForm.parentPermissionCode" readonly />
        </el-form-item>

        <el-form-item label="图标：" prop="icon">
          <el-input
              v-model="permissionForm.icon"
              placeholder="请选择图标"
              readonly
              @click="showIconDialog = true"
          />
          <div v-if="permissionForm.icon" style="margin-top: 8px;">
            <el-icon><component :is="permissionForm.icon" /></el-icon>
          </div>
        </el-form-item>

        <el-form-item label="菜单URL：" prop="url">
          <el-input v-model="permissionForm.url" placeholder="请输入菜单URL" />
        </el-form-item>

        <el-form-item label="菜单级别：">
          <el-input v-model.number="permissionForm.lev" type="number" readonly />
        </el-form-item>

        <el-form-item label="排序序号：">
          <el-input v-model.number="permissionForm.sort" type="number" />
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="permissionFormVisible = false">取消</el-button>
          <el-button type="primary" @click="savePermission">保存</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 图标选择弹窗 -->
    <el-dialog title="选择图标" v-model="showIconDialog" width="60%">
      <el-input v-model="iconSearch" placeholder="输入关键字搜索图标" style="margin-bottom: 10px;" clearable />
      <div class="icon-list">
        <div
            v-for="(icon, index) in filteredIcons"
            :key="index"
            class="icon-item"
            @click="selectIcon(icon.name)"
        >
          <el-icon><component :is="icon.component" /></el-icon>
          <span>{{ icon.name }}</span>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox, ElNotification } from 'element-plus'
import request from '@/utils/request.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// ====================== 数据定义 ======================
const data = reactive({
  defaultProps: {
    children: 'children',
    label: 'label',
  },
  permissionTree: [],
})

const permissionFormVisible = ref(false)
const showIconDialog = ref(false)
const iconSearch = ref('')
const formRef = ref()

const permissionForm = ref({
  id: '',
  title: '',
  parentPermissionCode: '',
  permissionName: '',
  permissionCode: '',
  icon: '',
  url: '',
  lev: 1,
  sort: ''
})

const rules = {
  permissionName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  permissionCode: [{ required: true, message: '请输入菜单编码', trigger: 'blur' }],
  icon: [{ required: true, message: '请选择图标', trigger: 'change' }]
}

// ====================== 图标相关 ======================
const icons = Object.keys(ElementPlusIconsVue).map(key => ({
  name: key,
  component: ElementPlusIconsVue[key]
}))

const filteredIcons = computed(() => {
  if (!iconSearch.value) return icons
  const keyword = iconSearch.value.toLowerCase()
  return icons.filter(icon => icon.name.toLowerCase().includes(keyword))
})

const selectIcon = (iconName) => {
  permissionForm.value.icon = iconName
  showIconDialog.value = false
}

// ====================== 权限树操作 ======================
const convertToTreeView = (nodes) =>
    nodes.map(permission => ({
      id: permission.id,
      label: permission.permissionName,
      ...permission,
      children: permission.children ? convertToTreeView(permission.children) : []
    }))

const loadPermissionTree = () => {
  request.post('/api/permission/tree').then(res => {
    if (res.code === 200) {
      data.permissionTree = convertToTreeView(res.data)
    } else {
      ElMessage.error(res.msg)
    }
  }).catch(err => {
    console.error('加载失败:', err)
    ElMessage.error('加载权限树失败')
  })
}

onMounted(() => {
  loadPermissionTree()
})

// ====================== 表单操作 ======================
const addPermissionDialog = () => {
  showAddPermissionDialog()
}

const showAddPermissionDialog = async (row) => {
  permissionForm.value = {
    id: '',
    title: '新增菜单',
    parentPermissionCode: row?.permissionCode || '',
    permissionName: '',
    permissionCode: '',
    icon: '',
    url: '',
    lev: row ? row.lev + 1 : 1,
    sort: ''
  }
  permissionFormVisible.value = true
}

const savePermission = () => {
  formRef.value.validate(valid => {
    if (valid) {
      const params = {
        id: permissionForm.value.id,
        parentPermissionCode: permissionForm.value.parentPermissionCode,
        permissionName: permissionForm.value.permissionName.trim(),
        permissionCode: permissionForm.value.permissionCode.trim(),
        icon: permissionForm.value.icon.trim(),
        url: permissionForm.value.url.trim(),
        lev: permissionForm.value.lev,
        sort: permissionForm.value.sort
      }

      request.post('/api/permission/save', params).then(res => {
        if (res.code === 200) {
          ElMessage.success('保存成功')
          permissionFormVisible.value = false
          loadPermissionTree()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const edit = (row) => {
  permissionForm.value = {
    id: row.id,
    title: '编辑菜单',
    parentPermissionCode: row.parentPermissionCode,
    permissionName: row.permissionName,
    permissionCode: row.permissionCode,
    icon: row.icon,
    url: row.url,
    lev: row.lev,
    sort: row.sort
  }
  permissionFormVisible.value = true
}

const remove = (node, row) => {
  ElMessageBox.confirm(`确认删除菜单【${row.permissionName}】？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.post(`/api/permission/delete/${row.id}`).then(res => {
      if (res.code === 200) {
        ElMessage.success('删除成功')
        loadPermissionTree()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}
</script>

<style lang="scss" scoped>
.permission-page {
  padding: 20px;
}

.toolbar {
  margin-bottom: 10px;
}

.tree-container {
  padding: 10px;
}

.tree-node-label {
  display: flex;
  align-items: center;
  gap: 6px;
}

.tree-node-actions {
  display: flex;
  gap: 10px;
}

.icon-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 10px;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.2s;

  &:hover {
    background-color: #f5f7fa;
  }

  span {
    font-size: 12px;
    margin-top: 4px;
  }
}
</style>