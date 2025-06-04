<template>
  <el-card class="summary-card">
    <template #header>
      <div class="card-header">
        <span>{{ title }}</span>
      </div>
    </template>

    <template v-for="(item, index) in displayItems" :key="index">
      <el-row :gutter="10" class="summary-row">
        <el-col :span="8"><strong>{{ item.label }}</strong></el-col>
        <el-col :span="16">{{ item.value }}</el-col>
      </el-row>
    </template>
  </el-card>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  data: {
    type: Object,
    required: true,
    default: () => ({})
  }
});

const formatCurrency = (value) => {
  if (!value && value !== 0) return '-';
  return new Intl.NumberFormat('zh-CN', { style: 'currency', currency: 'CNY' }).format(value);
};

// 定义要展示的字段和显示方式
const fields = [
  { key: 'dailyCount', label: '日交易笔数' },
  { key: 'monthlyCount', label: '月交易笔数' },
  { key: 'dailyTotal', label: '日交易金额', formatter: formatCurrency },
  { key: 'monthlyTotal', label: '月交易金额', formatter: formatCurrency }
];

// 动态计算要展示的数据项
const displayItems = computed(() => {
  return fields
      .filter(field => props.data[field.key] !== undefined)
      .map(field => ({
        label: field.label,
        value: field.formatter ? field.formatter(props.data[field.key]) : props.data[field.key]
      }));
});
</script>

<style scoped>
.summary-card {
  margin-bottom: 20px;
}
.card-header {
  font-size: 16px;
  font-weight: bold;
}
.summary-row {
  margin-bottom: 10px;
}
</style>