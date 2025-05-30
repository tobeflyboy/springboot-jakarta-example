<template>
  <el-card shadow="never">
    <!-- 搜索区域 -->
    <el-row :gutter="15" style="margin-bottom: 15px;">
      <el-col :span="4">
        <el-input v-model.trim="queryParams.roleName" placeholder="角色名称，模糊查询" clearable @keyup.enter="debouncedSearch">
          <template #prefix>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-input v-model.trim="queryParams.roleCode" placeholder="角色编码，精准查询" clearable @keyup.enter="debouncedSearch">
          <template #prefix>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-space>
          <el-button type="primary" icon="Search" @click="search">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-space>
      </el-col>
    </el-row>

    <!-- 工具栏 -->
    <el-space style="margin-bottom: 15px;">
      <el-button type="success" icon="Plus" @click="showAddRoleDialog">新增角色</el-button>
    </el-space>

    <!-- 表格区域 -->
    <el-table :data="roleList" stripe style="width: 100%" :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
      <el-table-column prop="roleName" label="角色名称"/>
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="createUserRealName" label="创建人"/>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-space>
            <el-button size="small" type="primary" icon="Edit" circle @click="showEditRoleDialog(scope.row)" title="编辑角色"></el-button>
            <el-button size="small" type="primary" icon="Connection" circle @click="showRolePermissionDialog(scope.row)" title="菜单授权"></el-button>
            <el-button size="small" type="danger" icon="Delete" circle @click="deleteRole(scope.row)" title="删除角色"></el-button>
          </el-space>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        background
        @size-change="loadRoles"
        @current-change="loadRoles"
        style="margin-top: 15px;"
    />
  </el-card>

  <!-- 新增/编辑角色弹窗 -->
  <el-dialog :title="form.title" v-model="formVisible" width="40%">
    <el-form ref="roleFormRef" :model="form" :rules="rules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="form.id"/>
      <el-form-item label="角色名称：" prop="roleName">
        <el-input v-model="form.roleName" placeholder="请输入角色名称"/>
      </el-form-item>
      <el-form-item label="角色编码：" prop="roleCode">
        <el-input v-model="form.roleCode" placeholder="请输入角色编码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="text-align: right; padding-right: 30px;">
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="saveRole">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 角色权限弹窗 -->
  <el-dialog title="角色授权" v-model="permission.visible" width="40%">
    <template #header>
      <div class="dialog-header">
        <el-button @click="permission.visible = false">取消</el-button>
        <el-button type="info" @click="selectAll">全选</el-button>
        <el-button type="primary" @click="saveRolePermission">保存</el-button>
      </div>
    </template>
    <el-form label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="permission.roleId"/>
      <div class="scrollable-container" v-loading="loading.value">
        <el-tree
            :data="permission.tree"
            show-checkbox
            node-key="id"
            default-expand-all
            auto-expand-parent
            :props="permission.defaultProps"
            :default-checked-keys="permission.checkedKeys"
            ref="treeRef"
        >
          <template #default="{ node, data }">
            <div class="custom-tree-node">
              <span>
                <el-icon v-if="data.icon">
                  <component :is="data.icon"/>
                </el-icon>
                {{ node.label }}
              </span>
            </div>
          </template>
        </el-tree>
      </div>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Search} from '@element-plus/icons-vue'
import request from '@/utils/request.js'
import {trim} from '@/utils/common.js'
import debounce from 'lodash-es/debounce'

// 查询参数
const queryParams = reactive({
  roleName: null,
  roleCode: null,
  pageNum: 1,
  pageSize: 10
})

// 角色列表数据
const roleList = ref([])
const total = ref(0)

// 新增/编辑表单
const formVisible = ref(false)
const form = ref({})
const rules = {
  roleName: [{required: true, message: '请输入角色名称', trigger: 'blur'}],
  roleCode: [{required: true, message: '请输入角色编码', trigger: 'blur'}]
}
const roleFormRef = ref()

// 权限相关
const permission = reactive({
  visible: false,
  roleId: '',
  tree: [],
  checkedKeys: [],
  defaultProps: {
    children: 'children',
    label: 'label'
  }
})
const loading = ref(true)
const treeRef = ref()

// 构建权限树
function buildPermissionTree(permissions = [], checkedList = []) {
  return permissions.map(p => {
    const node = {
      id: p.id,
      label: p.permissionName,
      parentPermissionCode: p.parentPermissionCode,
      permissionName: p.permissionName,
      permissionCode: p.permissionCode,
      icon: p.icon,
      url: p.url,
      lev: p.lev,
      sort: p.sort,
      children: p.children ? buildPermissionTree(p.children, checkedList) : undefined
    }
    if (p.checked === 1) checkedList.push(p.id)
    return node
  })
}

// 获取所有节点ID
function getAllNodeIds(nodes) {
  let ids = []
  nodes.forEach(node => {
    ids.push(node.id)
    if (node.children) {
      ids = ids.concat(getAllNodeIds(node.children))
    }
  })
  return ids
}

// 加载角色列表
const loadRoles = () => {
  request.post(`/api/role/list?pageNum=${queryParams.pageNum}`, {
    roleCode: trim(queryParams.roleCode),
    roleName: trim(queryParams.roleName)
  }).then(res => {
    if (res.code === 200) {
      roleList.value = res.data.list
      total.value = res.data.total
      queryParams.pageSize = res.data.pageSize
    }
  })
}

// 防抖搜索
const debouncedSearch = debounce(loadRoles, 500)

// 搜索
const search = () => {
  queryParams.pageNum = 1
  debouncedSearch()
}

// 重置搜索
const resetSearch = () => {
  queryParams.roleCode = ''
  queryParams.roleName = ''
  search()
}

// 显示新增角色对话框
const showAddRoleDialog = () => {
  form.value = {title: '新增角色'}
  formVisible.value = true
}

// 显示编辑角色对话框
const showEditRoleDialog = (item) => {
  form.value = {
    title: '编辑角色',
    id: item.id,
    roleName: item.roleName,
    roleCode: item.roleCode
  }
  formVisible.value = true
}

// 保存角色
const saveRole = () => {
  roleFormRef.value.validate(valid => {
    if (valid) {
      const payload = {
        id: trim(form.value.id),
        roleName: trim(form.value.roleName),
        roleCode: trim(form.value.roleCode)
      }
      request.post('/api/role/save', payload).then(res => {
        if (res.code === 200) {
          search()
          formVisible.value = false
          ElMessage.success('保存成功')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

// 删除角色
const deleteRole = (item) => {
  ElMessageBox.confirm(`确认删除角色【${item.roleName}】？`, '删除确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.post(`/api/role/delete/${item.id}`).then(res => {
      if (res.code === 200) {
        search()
        ElMessage.success('删除成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}

// 打开权限弹窗
const showRolePermissionDialog = (item) => {
  permission.tree = []
  permission.checkedKeys = []
  permission.roleId = item.id
  permission.visible = true
  loading.value = true

  request.post(`/api/role_permission/${item.id}`).then(res => {
    if (res.code === 200) {
      permission.tree = buildPermissionTree(res.data, permission.checkedKeys)
      loading.value = false
    } else {
      ElMessage.error(res.msg)
    }
  }).catch(err => {
    console.error('加载权限失败:', err)
    ElMessage.error('加载权限失败')
  })
}

// 全选权限
const selectAll = () => {
  const allIds = getAllNodeIds(permission.tree)
  treeRef.value.setCheckedKeys(allIds)
}

// 保存权限
const saveRolePermission = () => {
  const checkedIds = treeRef.value.getCheckedKeys()
  const halfCheckedIds = treeRef.value.getHalfCheckedKeys()
  const allSelectedIds = [...checkedIds, ...halfCheckedIds].map(id => id.toString())

  request.post('/api/role_permission/save', {
    roleId: permission.roleId,
    permissionIdList: allSelectedIds
  }).then(res => {
    if (res.code === 200) {
      ElMessage.success('权限保存成功')
      permission.visible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 初始化加载
onMounted(() => {
  loadRoles()
})
</script>