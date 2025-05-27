<template>

  <!-- 搜索框 开始-->
  <div class="card" style="margin-top: 5px; margin-bottom: 5px;">
    <el-input style="width: 180px; margin-right: 5px;" v-model="data.username" placeholder="账号，精准查询" :prefix-icon="Search"></el-input>
    <el-input style="width: 180px; margin-right: 5px;" v-model="data.realName" placeholder="姓名，模糊查询" :prefix-icon="Search"></el-input>
    <el-select style="width: 180px; margin-right: 5px;" v-model="data.status" placeholder="用户状态">
      <el-option
          v-for="(value,key) in data.userStatus"
          :key="key"
          :label="value"
          :value="key"
      />
    </el-select>
    <el-button type="primary" icon="Search" @click="search">搜索</el-button>
  </div>
  <!-- 搜索框 结束-->

  <!-- 操作按钮 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-button type="success" @click="showAddUserDialog">新增用户</el-button>
    <el-button type="info" @click="showExportUser">批量导出</el-button>
    <el-upload style="display: inline-block; margin-left: 10px;" action="http://localhost:8080/demo/api/user/import" :show-file-list="false" :on-success="uploadSuccess">
      <el-button type="info">批量导入</el-button>
    </el-upload>
  </div>
  <!-- 操作按钮 结束-->

  <!-- 查询内容 开始-->
  <div class="card" style="margin-bottom: 5px;">
    <el-table :data="data.tableData" style="width: 100%" :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
      <el-table-column prop="username" label="账号"/>
      <el-table-column prop="realName" label="姓名"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="statusDesc" label="用户状态"/>
      <el-table-column prop="roleName" label="所属角色"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="createUserRealName" label="创建人"/>
      <el-table-column prop="updateTime" label="更新时间"/>
      <el-table-column prop="updateUserRealName" label="更新人"/>
      <el-table-column prop="lastLoginTime" label="最后登录时间"/>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" type="primary" @click="showEditUserDialog(scope.row)" icon="Edit" circle title="编辑用户"></el-button>
          <el-button size="small" type="warning" @click="showResetPwdDialog(scope.row)" icon="Key" circle title="重置密码"></el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row)" icon="Delete" circle title="删除用户"></el-button>
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
        @current-change="load"
    />
  </div>
  <!-- 分页控件 结束-->

  <!-- 新增用户对话框 开始-->
  <el-dialog title="新增用户" v-model="data.addUserFormVisible" width="35%" distory-on-close>
    <el-form ref="addUserFormRef" :model="data.addUserForm" :rules="data.addUserFormRules" label-width="auto" style="padding: 0 5% 0 4%">
      <el-form-item label="账号名称：" prop="username">
        <el-input v-model="data.addUserForm.username" placeholder="请输入6-12位的英文字母或字符"/>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="realName">
        <el-input v-model="data.addUserForm.realName" placeholder="请输入姓名"/>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="data.addUserForm.password" placeholder="请输入6-12位的英文字母或字符" type="password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="确认密码：" prop="confirmPassword">
        <el-input v-model="data.addUserForm.confirmPassword" placeholder="请再次输入密码" type="password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="data.addUserForm.email" placeholder="请输入邮箱地址"/>
      </el-form-item>
      <el-form-item label="所属角色：" prop="roleId">
        <el-select v-model="data.addUserForm.roleId" placeholder="请选择角色">
          <el-option
              v-for="item in data.roles"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="data.addUserFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addUser">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增用户对话框 结束-->

  <!-- 重置密码对话框 开始-->
  <el-dialog title="重置密码" v-model="data.resetPwdFormVisible" width="35%" distory-on-close>
    <el-form ref="resetPwdFormRef" :model="data.resetPwdForm" :rules="data.resetPwdFormRules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="data.resetPwdForm.userId">
      <el-form-item label="账号名称：" prop="username">
        <span>{{ data.resetPwdForm.username }}</span>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="realName">
        <span>{{ data.resetPwdForm.realName }}</span>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="data.resetPwdForm.password" placeholder="请输入6-12位的英文字母或字符" type="password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="确认密码：" prop="confirmPassword">
        <el-input v-model="data.resetPwdForm.confirmPassword" placeholder="请再次输入密码" type="password" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="data.resetPwdFormVisible = false">取消</el-button>
        <el-button type="primary" @click="resetPwd">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 重置密码对话框 结束-->

  <!-- 编辑用户对话框 开始-->
  <el-dialog title="编辑用户" v-model="data.editUserFormVisible" width="35%" distory-on-close>
    <el-form ref="editUserFormRef" :model="data.editUserForm" :rules="data.editUserFormRules" label-width="auto" style="padding: 0 5% 0 4%">
      <input type="hidden" v-model="data.editUserForm.userId">
      <el-form-item label="账号名称：" prop="username">
        <span>{{ data.editUserForm.username }}</span>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="realName">
        <span>{{ data.editUserForm.realName }}</span>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="data.editUserForm.email" placeholder="请输入邮箱地址"/>
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-select v-model="data.editUserForm.status" placeholder="请选择状态">
          <el-option
              v-for="(value,key) in data.userStatus"
              :key="key"
              :label="value"
              :value="key"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属角色：" prop="roleId">
        <el-select v-model="data.editUserForm.roleId" placeholder="请选择角色">
          <el-option
              v-for="item in data.roles"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer" style="padding-right: 30px;">
        <el-button @click="data.editUserFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editUser">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 编辑用户对话框 结束-->
</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage, ElNotification, ElMessageBox} from 'element-plus'
import {Search} from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import {trim} from "@/utils/common.js";

const data = reactive({
  username: '',
  realName: '',
  status: '',
  userStatus: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  roles: [],
  addUserFormVisible: false,
  addUserForm: {},
  addUserFormRules: {
    username: [
      {required: true, message: '请输入账号名称', trigger: 'blur'},
      {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
    ],
    realName: [
      {required: true, message: '请输入真实姓名', trigger: 'blur'}
    ],
    password: [
      {required: true, message: '请输入密码', trigger: 'blur'},
      {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
    ],
    confirmPassword: [
      {required: true, message: '请再次输入密码', trigger: 'blur'},
      {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'},
      {
        validator: (rule, value, callback) => {
          if (value !== data.addUserForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        }, trigger: 'blur'
      }
    ],
    email: [
      {required: true, message: '请输入邮箱地址', trigger: 'blur'},
      {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
    ],
    roleId: [
      {required: true, message: '请选择角色', trigger: 'change'}
    ]
  },
  editUserFormVisible: false,
  editUserForm: {},
  editUserFormRules: {
    status: [
      {required: true, message: '请选择状态', trigger: 'blur'}
    ],
    email: [
      {required: true, message: '请输入邮箱地址', trigger: 'blur'},
      {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
    ],
    roleId: [
      {required: true, message: '请选择角色', trigger: 'change'}
    ]
  },
  resetPwdFormVisible: false,
  resetPwdForm: {},
  resetPwdFormRules: {
    password: [
      {required: true, message: '请输入密码', trigger: 'blur'},
      {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
    ],
    confirmPassword: [
      {required: true, message: '请再次输入密码', trigger: 'blur'},
      {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'},
      {
        validator: (rule, value, callback) => {
          if (value !== data.resetPwdForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        }, trigger: 'blur'
      }
    ]
  }
})

const addUserFormRef = ref();
const editUserFormRef = ref();
const resetPwdFormRef = ref();

// 初始化用户状态下拉框option
const queryUserStatus = () => {
  request.post('/api/user/status-enum').then(res => {
    if (res.code === 200) {
      data.userStatus = res.data;
    }
  })
}
queryUserStatus();

const load = () => {
  request.post('/api/user/list?pageNum=' + data.pageNum, {
    username: trim(data.username),
    realName: trim(data.realName),
    status: data.status
  }).then(res => {
    if (res.code === 200) {
      data.tableData = res.data.list;
      data.total = res.data.total;
      data.pageSize = res.data.pageSize;
      data.pageNum = res.data.pageNum;
    }
  });
}

load();


const search = () => {
  data.pageNum = 1;
  load();
}

const loadRoles = () => {
  request.post('/api/role/all-list').then(res => {
    if (res.code === 200) {
      // 清空 roles 数组以避免重复添加
      data.roles.length = 0;
      res.data.forEach(item => {
        data.roles.push({
          label: item.roleName,
          value: item.id
        })
      });
    }
  });
}
loadRoles();

const showAddUserDialog = () => {
  data.addUserFormVisible = true;
  data.addUserForm = {};
}

const addUser = () => {
  addUserFormRef.value.validate((valid) => {
    if (valid) {
      let user = {
        username: trim(data.addUserForm.username),
        realName: trim(data.addUserForm.realName),
        password: trim(data.addUserForm.password),
        email: trim(data.addUserForm.email),
        roleId: trim(data.addUserForm.roleId)
      }
      request.post('/api/user/add', user).then(res => {
        if (res.code === 200) {
          search();
          data.addUserFormVisible = false;
          ElMessage.success('新增用户成功!');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
}

const showResetPwdDialog = (user) => {
  data.resetPwdForm.userId = user.userId;
  data.resetPwdForm.username = user.username;
  data.resetPwdForm.realName = user.realName;
  data.resetPwdFormVisible = true;
}

const resetPwd = () => {
  resetPwdFormRef.value.validate((valid) => {
    if (valid) {
      let user = {
        userId: trim(data.resetPwdForm.userId),
        newPassword: trim(data.resetPwdForm.password)
      }
      request.post('/api/user/reset-pwd', user).then(res => {
        if (res.code === 200) {
          data.resetPwdFormVisible = false;
          ElMessage.success('重置密码成功!');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
}

const showEditUserDialog = (user) => {
  data.editUserForm.userId = user.userId;
  data.editUserForm.username = user.username;
  data.editUserForm.realName = user.realName;
  data.editUserForm.status = user.status.toString();
  data.editUserForm.email = user.email;
  data.editUserForm.roleId = user.roleId;
  data.editUserFormVisible = true;
}

const editUser = () => {
  editUserFormRef.value.validate((valid) => {
    if (valid) {
      let user = {
        userId: trim(data.editUserForm.userId),
        status: trim(data.editUserForm.status),
        email: trim(data.editUserForm.email),
        roleId: trim(data.editUserForm.roleId)
      }
      request.post('/api/user/edit', data.editUserForm).then(res => {
        if (res.code === 200) {
          search();
          data.editUserFormVisible = false;
          ElMessage.success('编辑用户成功!');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  })
}

const deleteUser = (user) => {
  ElMessageBox.confirm(
      '您确认删除名称为【' + user.realName + '】的用户吗?',
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.post('/api/user/delete/' + user.userId).then(res => {
      if (res.code === 200) {
        search();
        data.editUserFormVisible = false;
        ElMessage.success('删除用户成功!');
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {
  })

}

const showExportUser = () => {
  let url = `http://localhost:8080/demo/api/user/export?username=${data.username}&realName=${data.realName}&status=${data.status}`;
  console.log(url);
  window.open(url);
}

const uploadSuccess = (response, file, fileList) => {
  console.log(response);
  if (response.code === 200) {
    ElMessage.success('上传成功!');
    load();
  } else {
    ElMessage.error(response.msg);
  }
}

</script>
