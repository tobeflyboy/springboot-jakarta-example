<template>
  <!-- 操作按钮 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-button type="success" @click="addPermissionDialog">新增一级菜单</el-button>
  </div>
  <!-- 操作按钮 结束-->

  <!-- 查询内容 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-tree
        style="max-width: 600px"
        :data="data.permissionTree"
        node-key="id"
        :default-expanded-keys="[2, 3]"
        :default-checked-keys="[5]"
        default-expand-all
        :props="data.defaultProps"
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
          <div>
            <el-button type="primary" link @click="showAddPermissionDialog(data)">
              添加
            </el-button>
            <el-button type="primary" link @click="edit(data)">
              编辑
            </el-button>
            <el-button
                style="margin-left: 4px"
                type="danger"
                link
                @click="remove(node, data)"
            >
              删除
            </el-button>
          </div>
        </div>
      </template>
    </el-tree>
  </div>
  <!-- 查询内容 结束-->

  <!-- 新增菜单 开始-->
  <el-dialog :title="data.permissionForm.title" v-model="data.permissionFormVisible" width="35%" distory-on-close>
    <el-form ref="permissionFormRef" :model="data.permissionForm" :rules="data.permissionFormRules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="data.permissionForm.id">
      <el-form-item label="菜单名称：" prop="permissionName">
        <el-input v-model="data.permissionForm.permissionName" placeholder="请输入菜单名称"/>
      </el-form-item>
      <el-form-item label="菜单编码：" prop="permissionCode">
        <el-input v-model="data.permissionForm.permissionCode" placeholder="请输入菜单编码"/>
      </el-form-item>
      <el-form-item label="父菜单编码：" prop="parentPermissionCode">
        <el-input v-model="data.permissionForm.parentPermissionCode" readonly/>
      </el-form-item>
      <el-form-item label="icon图标：" prop="icon">
        <div @click="showIconDialog = true">
          <span v-if="data.permissionForm.icon">
            <el-icon><component :is="data.permissionForm.icon"/></el-icon>
          </span>
          <span v-else>请选择菜单图标</span>
        </div>
      </el-form-item>
      <el-form-item label="菜单URL：" prop="url">
        <el-input v-model="data.permissionForm.url" placeholder="请输入菜单URL"/>
      </el-form-item>
      <el-form-item label="菜单级别：">
        <el-input v-model="data.permissionForm.lev" type="number" readonly />
      </el-form-item>
      <el-form-item label="菜单排序：">
        <el-input v-model="data.permissionForm.sort" type="number"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="data.permissionFormVisible = false">取消</el-button>
        <el-button type="primary" @click="savePermission">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增菜单 结束-->

  <!-- Icon Dialog -->
  <el-dialog title="选择图标" v-model="showIconDialog" width="50%">
    <div style="display:flex; flex-wrap: wrap;">
      <div v-for="(icon, index) in icons" :key="index" style="margin: 5px;" @click="selectIcon(icon)">
        <el-icon style="font-size: 25px;"><component :is="icon"/></el-icon>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import {reactive, onMounted, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import request from "@/utils/request.js";
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import {trim} from "@/utils/common.js";

const data = reactive({
  defaultProps: {
    children: 'children',
    label: 'label',
  },
  permissionTree: [],
  permissionFormVisible: false,
  permissionForm: {},
  permissionFormRules: {
    permissionName: [
      {required: true, message: '请输入菜单名称', trigger: 'blur'}
    ],
    permissionCode: [
      {required: true, message: '请输入菜单编码', trigger: 'blur'},
    ],
    icon: [
      {required: true, message: '请选择菜单图标', trigger: 'blur'}
    ]
  }
});

// 转换树数据结构
function convertToTreeView(nodes) {
  return nodes.map((permission) => {
    return {
      id: permission.id,
      label: permission.permissionName,
      parentPermissionCode: permission.parentPermissionCode,
      permissionName: permission.permissionName,
      permissionCode: permission.permissionCode,
      icon: permission.icon,
      url: permission.url,
      lev: permission.lev,
      sort: permission.sort,
      children: permission.children ? convertToTreeView(permission.children) : null,
    };
  });
}

const loadPermissionTree = () => {
  request.post('/api/permission/tree').then(res => {
    if (res.code === 200) {
      data.permissionTree = convertToTreeView(res.data);
    } else {
      ElMessage.error(res.msg);
    }
  }).catch(error => {
    console.error('Failed to load permission tree:', error);
    ElMessage.error('加载权限树失败');
  });
};

onMounted(() => {
  loadPermissionTree();
});

// 定义一个响应式变量来控制图标选择对话框的显示
const showIconDialog = ref(false);
const permissionFormRef = ref();

// 获取所有图标名称
const icons = Object.keys(ElementPlusIconsVue).map(key => ElementPlusIconsVue[key]);

// 选择图标的方法
const selectIcon = (icon) => {
  data.permissionForm.icon = icon.name;
  showIconDialog.value = false; // 关闭图标选择对话框
};

const addPermissionDialog = () => {
  showAddPermissionDialog();
};

const showAddPermissionDialog = async (row) => {
  data.permissionForm = [];
  data.permissionForm.title = '新增菜单';
  if (row) {
    try {
      const res = await request.post('/api/permission/' + row.id);
      if (res.code === 200) {
        data.permissionForm.parentPermissionCode = res.data.permissionCode;
        data.permissionForm.lev = res.data.lev + 1;
      }
    } catch (error) {
      console.error('Error fetching permission data:', error);
      // 处理错误，例如显示错误消息给用户
    }
  } else {
    data.permissionForm.lev = 1;
  }
  data.permissionFormVisible = true;
}

const savePermission = () => {
  permissionFormRef.value.validate((valid) => {
    if (valid) {
      let permission = {
        id: trim(data.permissionForm.id),
        parentPermissionCode: trim(data.permissionForm.parentPermissionCode),
        permissionName: trim(data.permissionForm.permissionName),
        permissionCode: trim(data.permissionForm.permissionCode),
        icon: trim(data.permissionForm.icon),
        url: trim(data.permissionForm.url),
        lev: trim(data.permissionForm.lev),
        sort: trim(data.permissionForm.sort)
      }
      console.log(permission);
      request.post('/api/permission/save', permission).then(res => {
        if (res.code === 200) {
          loadPermissionTree();
          data.permissionFormVisible = false;
          ElMessage.success('新增菜单成功!');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
}


const edit = (row) => {
  data.permissionForm = [];
  data.permissionForm.title = '编辑菜单';
  data.permissionForm.id = row.id;
  data.permissionForm.parentPermissionCode = row.parentPermissionCode;
  data.permissionForm.permissionName = row.permissionName;
  data.permissionForm.permissionCode = row.permissionCode;
  data.permissionForm.icon = row.icon;
  data.permissionForm.url = row.url;
  data.permissionForm.lev = row.lev;
  data.permissionForm.sort = row.sort;
  data.permissionFormVisible = true;
};

const remove = (node, row) => {

  ElMessageBox.confirm(
      '您确认删除名称为【' + row.permissionName + '】的菜单吗?',
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.post('/api/permission/delete/' + row.id).then(res => {
      if (res.code === 200) {
        loadPermissionTree();
        ElMessage.success('删除菜单成功!');
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {
  })
};
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>