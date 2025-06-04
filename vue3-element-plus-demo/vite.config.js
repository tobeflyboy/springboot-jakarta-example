// 引入 Node.js 的内置模块 fileURLToPath 和 URL，用于将文件路径转换为标准路径格式
import {fileURLToPath, URL} from 'node:url'

// 引入 Vite 的核心配置函数 defineConfig，以及 Vue 插件 vue 和 Vue DevTools 插件
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue' // 支持 Vue 3 单文件组件（.vue 文件）的解析
import vueDevTools from 'vite-plugin-vue-devtools' // 启用 Vue DevTools 调试工具

// 自动导入插件相关
import AutoImport from 'unplugin-auto-import/vite' // 自动导入常用 API（如 ref、reactive 等 Vue API）
import Components from 'unplugin-vue-components/vite' // 自动注册组件（如 Element Plus 组件）
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers' // Element Plus 按需加载解析器

// 全局配置文件导出
// https://vite.dev/config/
export default defineConfig({
    /**
     * 开发服务器配置
     * 可以设置端口号、代理、热更新等
     */
    server: {
        // 设置开发服务器的监听端口，默认是 5173，你可以自定义为 3000 或其他
        // port: 3000
    },

    /**
     * 插件配置
     * 插件用于扩展 Vite 功能，比如支持 Vue、自动导入、组件按需引入等
     */
    plugins: [
        vue(), // 启用 Vue 3 支持，处理 .vue 文件
        vueDevTools(), // 启用 Vue DevTools 调试功能，方便在浏览器中调试 Vue 应用

        // 使用 unplugin-auto-import 实现自动导入 Vue、Element Plus 等常用 API
        AutoImport({
            resolvers: [
                // Element Plus 解析器，自动导入其 API，例如 ElMessage、ElButton 等
                ElementPlusResolver()
            ],
        }),

        // 使用 unplugin-vue-components 实现自动注册组件（无需手动 import + components 注册）
        Components({
            resolvers: [
                // 配置 Element Plus 的组件按需加载，并使用 Sass 样式系统
                ElementPlusResolver({importStyle: 'sass'})
            ],
        }),
    ],

    /**
     * CSS 预处理器配置
     * 这里我们使用 SCSS/Sass 预处理器，并注入全局变量或混入样式
     */
    css: {
        preprocessorOptions: {
            scss: {
                // 在所有 SCSS 文件中自动注入指定的全局样式文件
                // 这样你可以在任何组件中直接使用 index.scss 中定义的变量和 mixin
                additionalData: `@use "@/assets/css/index.scss" as *;`,
            },
        },
    },

    /**
     * 路径别名配置
     * 定义 @ 指向 src 目录，方便项目中引用资源
     */
    resolve: {
        alias: {
            // 将 @ 映射到项目 src 目录下
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
})