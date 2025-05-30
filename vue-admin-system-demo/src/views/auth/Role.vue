<template>

  <!-- 搜索框 -->
  <div class="card" style="margin-top: 5px; margin-bottom: 5px;">
    <el-input v-model.trim="role.roleName" placeholder="角色名称，模糊查询" :prefix-icon="Search" style="width: 180px; margin-right: 5px;"></el-input>
    <el-input v-model.trim="role.roleCode" placeholder="角色编码，精准查询" :prefix-icon="Search" style="width: 180px; margin-right: 5px;"></el-input>
    <el-button type="primary" icon="Search" @click="search">搜索</el-button>
  </div>

  <!-- 操作按钮 -->
  <div class="card" style="margin-bottom: 5px;">
    <el-button type="success" @click="showAddRoleDialog">新增角色</el-button>
  </div>

  <!-- 查询内容 -->
  <div class="card" style="margin-bottom: 5px;">
    <el-table :data="role.list" style="width: 100%" :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
      <el-table-column prop="roleName" label="角色名称"/>
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="createUserRealName" label="创建人"/>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" type="primary" @click="showEditRoleDialog(scope.row)" icon="Edit" circle title="编辑角色"></el-button>
          <el-button size="small" type="primary" @click="showRolePermissionDialog(scope.row)" icon="Connection" circle title="菜单授权"></el-button>
          <el-button size="small" type="danger" @click="deleteRole(scope.row)" icon="Delete" circle title="删除角色"></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页控件 -->
  <div class="card" style="margin-bottom: 5px;">
    <el-pagination
        v-model:current-page="role.pageNum"
        :page-size="role.pageSize"
        layout="total, prev, pager, next"
        :total="role.total"
        @current-change="loadRoles"
    />
  </div>

  <!-- 新增/编辑角色对话框 -->
  <el-dialog :title="role.form.title" v-model="role.formVisible" width="35%">
    <el-form ref="roleFormRef" :model="role.form" :rules="role.rules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="role.form.id"/>
      <el-form-item label="角色名称：" prop="roleName">
        <el-input v-model="role.form.roleName" placeholder="请输入角色名称"/>
      </el-form-item>
      <el-form-item label="角色编码：" prop="roleCode">
        <el-input v-model="role.form.roleCode" placeholder="请输入角色编码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="role.formVisible = false">取消</el-button>
        <el-button type="primary" @click="saveRole">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 角色权限对话框 -->
  <el-dialog title="角色授权" v-model="permission.visible" width="35%">
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
import { reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import request from '@/utils/request.js'
import { trim } from '@/utils/common.js'

const loading = ref(true)

// 🧩 角色相关状态
const role = reactive({
  roleName: null,
  roleCode: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  list: [],
  formVisible: false,
  form: {},
  rules: {
    roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
    roleCode: [{ required: true, message: '请输入角色编码', trigger: 'blur' }]
  }
})

// 🧩 权限相关状态
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

const roleFormRef = ref()
const treeRef = ref()

// 加载角色列表
const loadRoles = () => {
  request.post('/api/role/list?pageNum=' + role.pageNum, {
    roleCode: trim(role.roleCode),
    roleName: trim(role.roleName)
  }).then(res => {
    if (res.code === 200) {
      role.list = res.data.list
      role.total = res.data.total
      role.pageSize = res.data.pageSize
      role.pageNum = res.data.pageNum
    }
  })
}
loadRoles()

// 搜索
const search = () => {
  role.pageNum = 1
  loadRoles()
}

// 显示新增角色弹窗
const showAddRoleDialog = () => {
  role.formVisible = true
  role.form = { title: '新增角色' }
}

// 保存角色
const saveRole = () => {
  roleFormRef.value.validate((valid) => {
    if (valid) {
      const payload = {
        id: trim(role.form.id),
        roleName: trim(role.form.roleName),
        roleCode: trim(role.form.roleCode)
      }
      request.post('/api/role/save', payload).then(res => {
        if (res.code === 200) {
          search()
          role.formVisible = false
          ElMessage.success('保存角色成功!')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

// 编辑角色
const showEditRoleDialog = (item) => {
  role.form = {
    title: '编辑角色',
    id: item.id,
    roleName: item.roleName,
    roleCode: item.roleCode
  }
  role.formVisible = true
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
        ElMessage.success('删除角色成功!')
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}

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

// 全选
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
      ElMessage.success('权限保存成功！')
      permission.visible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>

<style scoped>
.scrollable-container {
  max-height: 500px;
  overflow-y: auto;
}
</style>