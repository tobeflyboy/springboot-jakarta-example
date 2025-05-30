<template>
  <el-card shadow="never">
    <!-- 搜索区域 -->
    <el-row :gutter="15" style="margin-bottom: 15px;">
      <el-col :span="4">
        <el-input
            v-model="data.username"
            placeholder="账号，精准查询"
            clearable
            @keyup.enter="search"
        >
          <template #prefix>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-input
            v-model="data.realName"
            placeholder="姓名，模糊查询"
            clearable
            @keyup.enter="search"
        >
          <template #prefix>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-select
            v-model="data.status"
            placeholder="用户状态"
            clearable
            style="width: 100%"
        >
          <el-option
              v-for="(value, key) in data.userStatus"
              :key="key"
              :label="value"
              :value="key"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" icon="Search" @click="search">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-col>
    </el-row>

    <!-- 操作工具栏 -->
    <el-space style="margin-bottom: 15px;">
      <el-button type="success" icon="Plus" @click="showAddUserDialog">新增用户</el-button>
      <el-button type="warning" icon="Download" @click="showExportUser">批量导出</el-button>

      <!-- 使用自定义上传 -->
      <el-upload
          :show-file-list="false"
          :http-request="handleImport"
          accept=".xlsx,.xls"
      >
        <el-button type="info" icon="Upload">批量导入</el-button>
      </el-upload>
    </el-space>

    <!-- 数据表格 -->
    <el-table
        :data="data.tableData"
        stripe
        v-loading="data.loading"
        style="width: 100%"
        :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }"
    >
      <el-table-column prop="username" label="账号" min-width="100" fixed="left"/>
      <el-table-column prop="realName" label="姓名" min-width="80"/>
      <el-table-column prop="email" label="邮箱" min-width="180">
        <template #default="{ row }">
          <el-link type="primary" :href="`mailto:${row.email}`" v-if="row.email">
            {{ row.email }}
          </el-link>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column prop="statusDesc" label="用户状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.statusDesc }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="所属角色" width="100"/>
      <el-table-column prop="createTime" label="创建时间" width="120"/>
      <el-table-column prop="createUserRealName" label="创建人" width="80"/>
      <el-table-column prop="updateTime" label="更新时间" width="120"/>
      <el-table-column prop="updateUserRealName" label="更新人" width="80"/>
      <el-table-column prop="lastLoginTime" label="最后登录时间" width="120"/>
      <el-table-column label="操作" fixed="right" width="150">
        <template #default="scope">
          <el-space>
            <el-tooltip content="编辑用户" placement="top">
              <el-button
                  size="small"
                  type="primary"
                  icon="Edit"
                  circle
                  @click="showEditUserDialog(scope.row)"
              />
            </el-tooltip>

            <el-tooltip content="重置密码" placement="top">
              <el-button
                  size="small"
                  type="warning"
                  icon="Key"
                  circle
                  @click="showResetPwdDialog(scope.row)"
              />
            </el-tooltip>

            <el-tooltip content="删除用户" placement="top">
              <el-button
                  size="small"
                  type="danger"
                  icon="Delete"
                  circle
                  @click="deleteUser(scope.row)"
              />
            </el-tooltip>
          </el-space>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <div class="pagination">
      <el-pagination
          v-model:current-page="data.pageNum"
          v-model:page-size="data.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="data.total"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="handleSizeChange"
          @current-change="load"
          style="margin-top: 15px;"
      />
    </div>
  </el-card>

  <!-- 新增用户对话框 -->
  <el-dialog
      :title="data.addUserFormVisible ? '新增用户' : '编辑用户'"
      v-model="data.dialogVisible"
      width="500px"
      :close-on-click-modal="false"
      :destroy-on-close="true"
  >
    <el-form
        ref="currentFormRef"
        :model="data.currentForm"
        :rules="data.currentFormRules"
        label-width="100px"
    >
      <el-form-item label="账号名称：" prop="username" v-if="data.addUserFormVisible">
        <el-input
            v-model="data.currentForm.username"
            placeholder="请输入6-12位的英文字母或字符"
        />
      </el-form-item>

      <el-form-item label="真实姓名：" prop="realName" v-if="data.addUserFormVisible">
        <el-input v-model="data.currentForm.realName" placeholder="请输入姓名"/>
      </el-form-item>

      <template v-if="data.addUserFormVisible">
        <el-form-item label="密码：" prop="password">
          <el-input
              v-model="data.currentForm.password"
              placeholder="请输入6-12位的英文字母或字符"
              type="password"
              show-password
          />
        </el-form-item>

        <el-form-item label="确认密码：" prop="confirmPassword">
          <el-input
              v-model="data.currentForm.confirmPassword"
              placeholder="请再次输入密码"
              type="password"
              show-password
          />
        </el-form-item>
      </template>

      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="data.currentForm.email" placeholder="请输入邮箱地址"/>
      </el-form-item>

      <el-form-item label="状态：" prop="status" v-if="!data.addUserFormVisible">
        <el-select v-model="data.currentForm.status" placeholder="请选择状态">
          <el-option
              v-for="(value, key) in data.userStatus"
              :key="key"
              :label="value"
              :value="key"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="所属角色：" prop="roleId">
        <el-select v-model="data.currentForm.roleId" placeholder="请选择角色">
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
      <el-button @click="data.dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">
        保存
      </el-button>
    </template>
  </el-dialog>

  <!-- 重置密码对话框 -->
  <el-dialog
      title="重置密码"
      v-model="data.resetPwdFormVisible"
      width="500px"
      :close-on-click-modal="false"
  >
    <el-form
        ref="resetPwdFormRef"
        :model="data.resetPwdForm"
        :rules="data.resetPwdFormRules"
        label-width="100px"
    >
      <el-form-item label="账号名称：">
        <span class="form-value">{{ data.resetPwdForm.username }}</span>
      </el-form-item>

      <el-form-item label="真实姓名：">
        <span class="form-value">{{ data.resetPwdForm.realName }}</span>
      </el-form-item>

      <el-form-item label="密码：" prop="password">
        <el-input
            v-model="data.resetPwdForm.password"
            placeholder="请输入6-12位的英文字母或字符"
            type="password"
            show-password
        />
      </el-form-item>

      <el-form-item label="确认密码：" prop="confirmPassword">
        <el-input
            v-model="data.resetPwdForm.confirmPassword"
            placeholder="请再次输入密码"
            type="password"
            show-password
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="data.resetPwdFormVisible = false">取消</el-button>
      <el-button type="primary" @click="resetPwd">
        保存
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {reactive, ref, nextTick} from 'vue'
import {ElMessage, ElMessageBox, ElLoading} from 'element-plus'
import request from "@/utils/request.js"
import {trim} from "@/utils/common.js"

// 创建独立的表单引用
const currentFormRef = ref(null);
const resetPwdFormRef = ref(null);

// 使用更清晰的状态管理
const data = reactive({
  // 搜索条件
  username: '',
  realName: '',
  status: '',

  // 表格数据
  tableData: [],
  loading: false,
  pageNum: 1,
  pageSize: 10,
  total: 0,

  // 枚举数据
  userStatus: {},
  roles: [],

  // 对话框状态
  addUserFormVisible: false,
  editUserFormVisible: false,
  resetPwdFormVisible: false,
  dialogVisible: false,

  // 当前表单
  currentForm: {},

  // 重置密码表单
  resetPwdForm: {},

  // 表单规则
  currentFormRules: {
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
          if (value !== data.currentForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        },
        trigger: 'blur'
      }
    ],
    email: [
      {required: true, message: '请输入邮箱地址', trigger: 'blur'},
      {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
    ],
    roleId: [
      {required: true, message: '请选择角色', trigger: 'change'}
    ],
    status: [
      {required: true, message: '请选择状态', trigger: 'change'}
    ]
  },

  // 重置密码规则
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
        },
        trigger: 'blur'
      }
    ]
  }
})

// 初始化数据
const initData = async () => {
  try {
    data.loading = true

    // 并行请求
    const [statusRes, rolesRes] = await Promise.all([
      request.post('/api/user/status-enum'),
      request.post('/api/role/all-list')
    ])

    if (statusRes.code === 200) {
      data.userStatus = statusRes.data
    }

    if (rolesRes.code === 200) {
      data.roles = rolesRes.data.map(item => ({
        label: item.roleName,
        value: item.id
      }))
    }

    await load()
  } catch (error) {
    ElMessage.error('初始化数据失败')
  } finally {
    data.loading = false
  }
}

// 加载表格数据
const load = async () => {
  try {
    data.loading = true
    const res = await request.post('/api/user/list', {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: trim(data.username),
      realName: trim(data.realName),
      status: data.status
    })

    if (res.code === 200) {
      data.tableData = res.data.list
      data.total = res.data.total
    }
  } catch (error) {
    ElMessage.error('加载数据失败')
  } finally {
    data.loading = false
  }
}

// 搜索
const search = () => {
  data.pageNum = 1
  load()
}

// 重置搜索
const resetSearch = () => {
  data.username = ''
  data.realName = ''
  data.status = ''
  search()
}

// 分页大小变化
const handleSizeChange = (size) => {
  data.pageSize = size
  data.pageNum = 1
  load()
}

// 提交表单（新增/编辑）
const submitForm = async () => {
  try {
    console.log('submitForm开始')

    // 使用独立的表单引用
    await currentFormRef.value.validate()

    console.log('表单验证通过')
    console.log('当前表单数据:', data.currentForm)

    const isAdd = data.addUserFormVisible
    const api = isAdd ? '/api/user/add' : '/api/user/edit'
    const params = isAdd
        ? {
          username: trim(data.currentForm.username),
          realName: trim(data.currentForm.realName),
          password: trim(data.currentForm.password),
          email: trim(data.currentForm.email),
          roleId: trim(data.currentForm.roleId)
        }
        : {
          userId: trim(data.currentForm.userId),
          status: trim(data.currentForm.status),
          email: trim(data.currentForm.email),
          roleId: trim(data.currentForm.roleId)
        }

    console.log('提交参数:', params);

    const res = await request.post(api, params)
    if (res.code === 200) {
      ElMessage.success(`${isAdd ? '新增' : '编辑'}用户成功`)
      data.dialogVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    // 验证失败
    console.error('表单验证失败:', error)
  }
}

// 显示新增用户对话框
const showAddUserDialog = () => {
  data.addUserFormVisible = true
  data.editUserFormVisible = false
  data.dialogVisible = true
  data.currentForm = {
    username: '',
    realName: '',
    password: '',
    confirmPassword: '',
    email: '',
    roleId: ''
  }

  nextTick(() => {
    if (currentFormRef.value) {
      currentFormRef.value.resetFields()
      console.log('表单已重置')
    }
  })
}

// 显示编辑用户对话框
const showEditUserDialog = (user) => {
  data.addUserFormVisible = false
  data.editUserFormVisible = true
  data.dialogVisible = true
  data.currentForm = {
    userId: user.userId,
    username: user.username,
    realName: user.realName,
    status: user.status.toString(),
    email: user.email,
    roleId: user.roleId
  }

  nextTick(() => {
    if (currentFormRef.value) {
      currentFormRef.value.resetFields()
      console.log('表单已重置')
    }
  })
}

// 显示重置密码对话框
const showResetPwdDialog = (user) => {
  data.resetPwdFormVisible = true
  data.resetPwdForm = {
    userId: user.userId,
    username: user.username,
    realName: user.realName,
    password: '',
    confirmPassword: ''
  }

  nextTick(() => {
    if (resetPwdFormRef.value) {
      resetPwdFormRef.value.resetFields()
    }
  })
}

// 重置密码
const resetPwd = async () => {
  try {
    await resetPwdFormRef.value.validate()

    const res = await request.post('/api/user/reset-pwd', {
      userId: trim(data.resetPwdForm.userId),
      newPassword: trim(data.resetPwdForm.password)
    })

    if (res.code === 200) {
      ElMessage.success('重置密码成功')
      data.resetPwdFormVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    // 验证失败
  }
}

// 删除用户
const deleteUser = (user) => {
  ElMessageBox.confirm(
      `确定要删除用户【${user.realName}】吗？`,
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }
  ).then(async () => {
    const res = await request.post(`/api/user/delete/${user.userId}`)
    if (res.code === 200) {
      ElMessage.success('删除用户成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  }).catch(() => {
  })
}

// 导出用户
const showExportUser = async () => {
  let loadingInstance = null;
  try {
    loadingInstance = ElLoading.service({
      lock: true,
      text: '正在导出数据...',
      background: 'rgba(0, 0, 0, 0.7)'
    });

    const params = {
      username: data.username,
      realName: data.realName,
      status: data.status
    };

    // 调用 API
    const result = await request.get('/api/user/export', {
      params,
      responseType: 'blob'
    });

    // 处理文件下载
    const blob = new Blob([result.data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    // 获取文件名
    let fileName = `用户列表_${new Date().toLocaleDateString().replace(/\//g, '-')}.xlsx`;
    const contentDisposition = result.headers['content-disposition'];
    if (contentDisposition) {
      const match = contentDisposition.match(/filename="?([^"]+)"?/);
      if (match && match[1]) {
        fileName = decodeURIComponent(match[1]);
      }
    }

    // 创建下载链接
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', fileName);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);

    ElMessage.success('导出成功');
  } catch (error) {
    console.error('导出失败:', error);
    ElMessage.error(`导出失败: ${error.message || '未知错误'}`);
  } finally {
    if (loadingInstance) {
      setTimeout(() => loadingInstance.close(), 100);
    }
  }
};


// 自定义上传处理（使用封装的request）
const handleImport = async (options) => {
  console.log('开始导入用户数据...', options)
  const {file} = options
  let loadingInstance = null

  try {
    loadingInstance = ElLoading.service({
      lock: true,
      text: '正在导入用户数据...',
      background: 'rgba(0, 0, 0, 0.7)'
    })

    const formData = new FormData()
    formData.append('file', file)

    const res = await request.post('/api/user/import', formData, {
      skipContentType: true
    })

    if (res.code === 200) {
      ElMessage.success('导入成功')
      load()
    } else {
      ElMessage.error(res.msg || '导入失败')
    }
  } catch (error) {
    ElMessage.error('导入失败: ' + (error.message || '未知错误'))
  } finally {
    // 确保Loading关闭
    if (loadingInstance) {
      setTimeout(() => loadingInstance.close(), 100)
    }
  }
}

// 初始化页面
initData()
</script>

<style lang="scss" scoped>
.user-management-container {
  padding: 20px;
  background-color: #f5f7fa;

  .search-card {
    margin-bottom: 16px;

    :deep(.el-card__body) {
      padding: 18px 20px;
    }
  }

  .toolbar-card {
    margin-bottom: 16px;

    :deep(.el-card__body) {
      padding: 12px 20px;
    }
  }

  .table-card {
    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: flex-end;
    }
  }

  .form-value {
    padding-left: 10px;
    color: #606266;
  }
}

.dark {
  .user-management-container {
    background-color: var(--el-bg-color-page);

    .el-card {
      background-color: var(--el-bg-color-overlay);
      border-color: var(--el-border-color-light);
    }

    .form-value {
      color: var(--el-text-color-regular);
    }
  }
}
</style>