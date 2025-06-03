import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

import AutoImport from 'unplugin-auto-import/vite' // 自动导入vue中的组件
import Components from 'unplugin-vue-components/vite' // 自动导入ui组件，例如：element-plus等
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers' // 对应组件库引入，AntDesignVueResolver


// 全局配置文件
// https://vite.dev/config/
export default defineConfig({
  server: {
    // port: 3000 // 默认端口5173，这里可以自定义的端口号，比如 3000
  },
  plugins: [
    vue(),
    vueDevTools(),
    // element-plus按需导入
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [
        // 配置element-plus采用sass样式配置系统
        ElementPlusResolver({importStyle: 'sass'})
      ],
    }),
  ],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/assets/css/index.scss" as *;`,
      },
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
