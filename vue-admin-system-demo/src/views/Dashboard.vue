<template>
  <div class="dashboard-container">
    <!-- 总览面板 -->
    <el-row :gutter="20" justify="start">
      <el-col :xs="24" :sm="12" :md="8" v-for="(panel, index) in summaryPanels" :key="index">
        <SummaryCard :title="panel.title" :data="panel.data"/>
      </el-col>
    </el-row>

    <!-- 详细数据表格 -->
    <el-card style="margin-top: 20px;">
      <template #header>
        <h3 style="margin: 0;">今日详细数据</h3>
      </template>
      <div v-for="(items, key) in payDailySumTotalMap" :key="key">
        <h4 style="margin: 15px 0;">{{ key }}</h4>
        <el-table :data="items" border stripe style="width: 100%; margin-bottom: 20px;">
          <el-table-column prop="merchantCode" :label="key === '支付宝' ? '支付宝产品' : '商户号'" align="center"></el-table-column>
          <el-table-column prop="dailyCount" label="日交易笔数" align="center"></el-table-column>
          <el-table-column prop="monthlyCount" label="月交易笔数" align="center"></el-table-column>
          <el-table-column label="日交易金额" align="center">
            <template #default="{ row }">
              {{ formatCurrency(row.dailyTotal) }}
            </template>
          </el-table-column>
          <el-table-column label="月交易金额" align="center">
            <template #default="{ row }">
              {{ formatCurrency(row.monthlyTotal) }}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import SummaryCard from '@/components/dashboard/SummaryCard.vue';
import request from '@/utils/request.js'
import {ElMessage} from "element-plus";

const total = ref({});
const ccbpayTotal = ref({});
const alipayTotal = ref({});
const payDailySumTotalMap = ref({});

const summaryPanels = ref([
  {title: '总计', data: total},
  {title: '建设银行', data: ccbpayTotal},
  {title: '支付宝', data: alipayTotal}
]);

const formatCurrency = (value) => {
  if (!value) return '-';
  return new Intl.NumberFormat('zh-CN', {style: 'currency', currency: 'CNY'}).format(value);
};

const fetchData = async () => {
  request.post('/api/dashboard').then(res => {
    if (res.code === 200) {
      const data = res.data;

      total.value = data.total || {};
      ccbpayTotal.value = data.ccbpayTotal || {};
      alipayTotal.value = data.alipayTotal || {};
      payDailySumTotalMap.value = data.payDailySumTotalMap || {};
    } else {
      ElMessage.error(res.msg)
    }
  })
};

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.el-table {
  font-size: 14px;
}
</style>