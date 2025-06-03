<template>
  <el-card shadow="hover" class="main-container">
    <!-- 工具栏 -->
    <el-button type="success" @click="addPermissionDialog" class="primary-btn">
      <el-icon>
        <Plus/>
      </el-icon>
      <span>新增一级菜单</span>
    </el-button>
    <el-divider/>

    <!-- 权限树 -->
    <el-tree
        :data="data.permissionTree"
        node-key="id"
        default-expand-all
        :props="data.defaultProps"
        class="custom-tree"
    >
      <template #default="{ node, data }">
        <div class="tree-node-content">
          <div class="tree-node-label">
            <el-icon v-if="data.icon" class="node-icon">
              <component :is="data.icon"/>
            </el-icon>
            <span class="node-text">{{ node.label }}</span>
          </div>

          <div class="tree-node-actions-wrapper">
            <div class="tree-node-actions">
              <el-button link type="primary" @click="showAddPermissionDialog(data)">
                <el-icon>
                  <Plus/>
                </el-icon>
              </el-button>
              <el-button link type="primary" @click="edit(data)">
                <el-icon>
                  <Edit/>
                </el-icon>
              </el-button>
              <el-button link type="danger" @click="remove(node, data)">
                <el-icon>
                  <Delete/>
                </el-icon>
              </el-button>
            </div>
          </div>
        </div>
      </template>
    </el-tree>

    <!-- 菜单表单弹窗 -->
    <el-dialog
        :title="permissionForm.title"
        v-model="permissionFormVisible"
        width="40%"
        center
        destroy-on-close
        class="custom-dialog"
    >
      <el-form ref="formRef" :model="permissionForm" :rules="rules" label-width="130px">
        <input type="hidden" v-model="permissionForm.id"/>

        <el-form-item label="菜单名称：" prop="permissionName">
          <el-input v-model="permissionForm.permissionName" placeholder="请输入菜单名称"/>
        </el-form-item>

        <el-form-item label="菜单编码：" prop="permissionCode">
          <el-input v-model="permissionForm.permissionCode" placeholder="请输入菜单编码"/>
        </el-form-item>

        <el-form-item label="父菜单编码：" prop="parentPermissionCode">
          <el-input v-model="permissionForm.parentPermissionCode" readonly/>
        </el-form-item>

        <el-form-item label="图标：" prop="icon">
          <el-input
              v-model="permissionForm.icon"
              placeholder="请选择图标"
              readonly
              @click="showIconDialog = true"
          />
          <div v-if="permissionForm.icon" style="margin-top: 8px;">
            <el-icon>
              <component :is="permissionForm.icon"/>
            </el-icon>
          </div>
        </el-form-item>

        <el-form-item label="菜单URL：" prop="url">
          <el-input v-model="permissionForm.url" placeholder="请输入菜单URL"/>
        </el-form-item>

        <el-form-item label="菜单级别：">
          <el-input v-model.number="permissionForm.lev" type="number" readonly/>
        </el-form-item>

        <el-form-item label="排序序号：">
          <el-input v-model.number="permissionForm.sort" type="number"/>
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
    <el-dialog title="选择图标" v-model="showIconDialog" width="80%" class="icon-dialog">
      <el-input
          v-model="iconSearch"
          placeholder="搜索图标..."
          clearable
          prefix-icon="Search"
          class="icon-search-input"
      />

      <div class="icon-grid">
        <div
            v-for="(icon, index) in filteredIcons"
            :key="index"
            class="icon-item"
            :class="{ 'selected': permissionForm.icon === icon.name }"
            @click="selectIcon(icon.name)"
        >
          <div class="icon-preview">
            <el-icon :size="28">
              <component :is="icon.component"/>
            </el-icon>
          </div>
          <span class="icon-name">{{ icon.name }}</span>
        </div>
      </div>
    </el-dialog>
  </el-card>
</template>

<script setup>
import {reactive, ref, computed, onMounted} from 'vue'
import {ElMessage, ElMessageBox, ElNotification} from 'element-plus'
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
  permissionName: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
  permissionCode: [{required: true, message: '请输入菜单编码', trigger: 'blur'}],
  icon: [{required: true, message: '请选择图标', trigger: 'change'}]
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

<style scoped>
/* 主题变量 */
:root {
  --primary-color: #409EFF;
  --primary-light: #ecf5ff;
  --text-color: #303133;
  --border-color: #ebeef5;
}

/* 主容器 */
.main-container {
  border-radius: 12px;
  overflow: hidden;
}

/* 树节点 */
.tree-node-content {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0 12px;
}

.tree-node-label {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-grow: 1;

  .node-icon {
    color: var(--primary-color);
  }

  .node-text {
    font-weight: 500;
    color: var(--text-color);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

.tree-node-actions-wrapper {
  flex-shrink: 0;
  width: 160px;
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-left: 12px;
}
.icon-dialog {
  .el-dialog__body {
    padding: 0;
  }

  .icon-search-input {
    padding: 15px 20px 0;

    .el-input__wrapper {
      border-radius: 8px;
      box-shadow: 0 0 0 1px var(--border-color) inset;
    }
  }

  .icon-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
    gap: 12px;
    padding: 15px;
    max-height: 500px;
    overflow-y: auto;
  }

  .icon-item {
    padding: 12px;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s;
    text-align: center;
    border: 2px solid transparent;

    &:hover {
      background: var(--primary-light);
      transform: translateY(-2px);

      .icon-preview {
        transform: scale(1.2);
      }
    }

    &.selected {
      border-color: var(--primary-color);
      background: var(--primary-light);

      .icon-preview {
        color: var(--primary-color);
      }
    }
  }

  .icon-preview {
    margin-bottom: 8px;
    color: #606266;
    transition: all 0.2s;
    display: inline-flex;
  }

  .icon-name {
    font-size: 12px;
    color: #909399;
    display: block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>