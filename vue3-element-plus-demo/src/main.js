// 引入 Vue 的 createApp 方法，用于创建 Vue 应用实例
import {createApp} from 'vue'

// 引入根组件 App.vue，整个应用的入口组件
import App from './App.vue'

// 引入路由配置 router（来自 ./router/index.js）
import router from './router'

// 引入全局 CSS 样式文件，通常用于定义全局样式、重置样式等
import './assets/css/global.css'


// 安装 Element Plus UI 框架
// 安装命令：npm i element-plus -S
import ElementPlus from 'element-plus'
// 引入 Element Plus 默认的 CSS 样式
import 'element-plus/dist/index.css'
// 引入 Element Plus 的中文语言包，实现国际化支持
import zhCn from 'element-plus/es/locale/lang/zh-cn'


// 安装 Element Plus 图标库
// 安装命令：npm install @element-plus/icons-vue
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


// 【可选】安装其他插件（如 Sass、自动导入等）：
// 安装命令：
// npm install -D sass unplugin-vue-components unplugin-auto-import
// 这些插件一般在 vite.config.js 中使用，而不是 main.js


// 创建 Vue 应用实例
const app = createApp(App)

// 使用 Vue Router 插件，启用路由功能
app.use(router)

// 使用 Element Plus UI 框架，并设置默认语言为中文
app.use(ElementPlus, {
    locale: zhCn, // 设置 Element Plus 的语言为中文
})

// 将应用挂载到 HTML 中 id 为 "app" 的 DOM 元素上
app.mount('#app')


// 全局注册 Element Plus 所有图标组件
// 遍历所有图标组件，逐个注册为全局组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    // 注册为全局组件后，在任意组件中可以直接使用 <el-icon><XXX /></el-icon>
    app.component(key, component)
}