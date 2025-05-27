import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import './assets/css/global.css'

// npm i element-plus -S
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// npm install @element-plus/icons-vue
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// npm install -D sass unplugin-vue-components unplugin-auto-import

const app = createApp(App)

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})

app.mount('#app')

// 全局注册element-plus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}