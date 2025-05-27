<template>

  <!-- 搜索框 开始-->
  <div class="card" style="margin-top: 5px; margin-bottom: 5px;">
    <el-input style="width: 180px; margin-right: 5px;" v-model="data.roleName" placeholder="角色名称，模糊查询" :prefix-icon="Search"></el-input>
    <el-input style="width: 180px; margin-right: 5px;" v-model="data.roleCode" placeholder="角色编码，精准查询" :prefix-icon="Search"></el-input>
    <el-button type="primary" icon="Search" @click="search">搜索</el-button>
  </div>
  <!-- 搜索框 结束-->

  <!-- 操作按钮 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-button type="success" @click="showAddRoleDialog">新增角色</el-button>
  </div>
  <!-- 操作按钮 结束-->

  <!-- 查询内容 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-table :data="data.roles" style="width: 100%" :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
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
  <!-- 查询内容 结束-->

  <!-- 分页控件 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-pagination
        v-model:current-page="data.pageNum"
        :page-size="data.pageSize"
        layout="total, prev, pager, next"
        :total="data.total"
        @current-change="loadRoles"
    />
  </div>
  <!-- 分页控件 结束-->

  <!-- 新增角色与编辑角色，对话框 开始-->
  <el-dialog :title="data.roleForm.title" v-model="data.roleFormVisible" width="35%" distory-on-close>
    <el-form ref="roleFormRef" :model="data.roleForm" :rules="data.roleFormRules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="data.roleForm.id"/>
      <el-form-item label="角色名称：" prop="roleName">
        <el-input v-model="data.roleForm.roleName" placeholder="请输入角色名称"/>
      </el-form-item>
      <el-form-item label="角色编码：" prop="roleCode">
        <el-input v-model="data.roleForm.roleCode" placeholder="请输入角色编码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="data.roleFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveRole">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增角色与编辑角色，对话框 结束-->

  <!-- 角色菜单授权 开始-->
  <el-dialog title="角色授权" v-model="data.rolePermissionFormVisible" width="35%" distory-on-close>
    <template #header>
      <div class="dialog-header">
        <el-button @click="data.rolePermissionFormVisible = false">取消</el-button>
        <el-button type="info" @click="selectAll">全选</el-button>
        <el-button type="primary" @click="saveRolePermission">保存</el-button>
      </div>
    </template>
    <el-form :model="data.rolePermissionForm" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="data.rolePermissionForm.roleId"/>
      <div class="scrollable-container" v-loading="loading">
        <el-tree
            style="max-width: 600px;"
            :data="data.permissionTree"
            show-checkbox
            node-key="id"
            default-expand-all
            auto-expand-parent
            :props="data.defaultProps"
            :default-checked-keys="data.defaultCheckedKeys"
            ref="treeRef"
        >
          <template #default="{ node, data }">
            <div class="custom-tree-node">
          <span>
            <!-- 动态显示图标 -->
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
  <!-- 角色菜单授权 结束-->

</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage, ElNotification, ElMessageBox} from 'element-plus'
import {Search} from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import {trim} from "@/utils/common.js";
const loading = ref(true)

const data = reactive({
  roleCode: null,
  roleName: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  roles: [],
  roleFormVisible: false,
  roleForm: {},
  roleFormRules: {
    roleName: [
      {required: true, message: '请输入角色名称', trigger: 'blur'},
    ],
    roleCode: [
      {required: true, message: '请输入角色编码', trigger: 'blur'}
    ]
  },
  rolePermissionForm: [],
  rolePermissionFormVisible: false,
  defaultProps: {
    children: 'children',
    label: 'label',
  },
  permissionTree: []
})

const roleFormRef = ref();


const loadRoles = () => {
  request.post('/api/role/list?pageNum=' + data.pageNum, {
    roleCode: trim(data.roleCode),
    roleName: trim(data.roleName)
  }).then(res => {
    if (res.code === 200) {
      data.roles = res.data.list;
      data.total = res.data.total;
      data.pageSize = res.data.pageSize;
      data.pageNum = res.data.pageNum;
    }
  });
}

loadRoles();


const search = () => {
  data.pageNum = 1;
  loadRoles();
}

const showAddRoleDialog = () => {
  data.roleFormVisible = true;
  data.roleForm = {};
  data.roleForm.title = '新增角色';
}

const saveRole = () => {
  roleFormRef.value.validate((valid) => {
    if (valid) {
      let role = {
        id: trim(data.roleForm.id),
        roleName: trim(data.roleForm.roleName),
        roleCode: trim(data.roleForm.roleCode)
      }
      request.post('/api/role/save', role).then(res => {
        if (res.code === 200) {
          search();
          data.roleFormVisible = false;
          ElMessage.success('保存角色成功!');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
}

const showEditRoleDialog = (role) => {
  data.roleForm = [];
  data.roleForm.title = '编辑角色';
  data.roleForm.id = role.id;
  data.roleForm.roleName = role.roleName;
  data.roleForm.roleCode = role.roleCode;
  data.roleFormVisible = true;
}

const deleteRole = (role) => {
  ElMessageBox.confirm(
      '您确认删除名称为【' + role.roleName + '】的角色吗?',
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.post('/api/role/delete/' + role.id).then(res => {
      if (res.code === 200) {
        search();
        ElMessage.success('删除角色成功!');
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {
  })
}

// 转换树数据结构
function convertToTreeView(nodes, checkedIds = []) {
  return nodes.map((permission) => {
    const node = {
      id: permission.id,
      label: permission.permissionName,
      parentPermissionCode: permission.parentPermissionCode,
      permissionName: permission.permissionName,
      permissionCode: permission.permissionCode,
      icon: permission.icon,
      url: permission.url,
      lev: permission.lev,
      sort: permission.sort,
      children: permission.children ? convertToTreeView(permission.children, checkedIds) : null,
    };
    if (permission.checked === 1) {
      checkedIds.push(permission.id);
    }
    return node;
  });
}

const showRolePermissionDialog = (role) => {
  console.log(role);
  data.permissionTree = [];
  const checkedIds = [];
  data.rolePermissionForm.roleId = role.id;
  data.rolePermissionFormVisible = true;
  loading.value = true;
  request.post('/api/role_permission/' + role.id).then(res => {
    if (res.code === 200) {
      data.permissionTree = convertToTreeView(res.data, checkedIds);
      data.defaultCheckedKeys = checkedIds; // 设置默认选中的节点ID
      loading.value = false;
    } else {
      ElMessage.error(res.msg);
    }
  }).catch(error => {
    console.error('Failed to load permission tree:', error);
    ElMessage.error('加载权限树失败');
  });
}

const treeRef = ref(null); // 创建对 el-tree 组件的引用

const selectAll = () => {
  if (treeRef.value) {
    // 获取所有节点
    const allNodes = data.permissionTree.reduce((acc, node) => acc.concat(node, node.children ? node.children : []), []);
    // 使用 setCheckedNodes 方法选中所有节点
    treeRef.value.setCheckedNodes(allNodes);
  }
}

const saveRolePermission = () => {
  console.log(data.rolePermissionForm);
  if (treeRef.value) {
    // 获取所有完全选中的节点的 id
    const checkedIds = treeRef.value.getCheckedKeys();
    // 获取所有部分选中的节点的 id
    const halfCheckedIds = treeRef.value.getHalfCheckedKeys();

    // 合并两个列表并确保所有 id 都是字符串形式
    const allSelectedIds = [...checkedIds, ...halfCheckedIds].map(id => id.toString());

    console.log('完全选中的节点ID:', checkedIds);
    console.log('部分选中的节点ID:', halfCheckedIds);
    console.log('所有选中的节点ID:', allSelectedIds);

    // 在这里处理保存逻辑，比如发送请求到服务器
    // 示例：发送包含所有选中节点的数据到后端
    request.post('/api/role_permission/save', {
      roleId: data.rolePermissionForm.roleId,
      permissionIdList: allSelectedIds
    }).then(res => {
      if (res.code === 200) {
        ElMessage.success('保存权限成功!');
        data.rolePermissionFormVisible = false;
      } else {
        ElMessage.error(res.msg);
      }
    });
  }

}

</script>
<style scoped>
.scrollable-container {
  max-height: 500px; /* 根据需要调整这个值 */
  overflow-y: auto;
}
</style>
