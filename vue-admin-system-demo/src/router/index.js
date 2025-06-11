// 导入 Vue Router 核心函数
import {createRouter, createWebHistory} from 'vue-router'

// 创建路由实例
const router = createRouter({
    // 使用 HTML5 的 history 模式（需要服务器配置支持）
    // import.meta.env.BASE_URL 会自动获取项目的基础路径（如：/ 或 /admin/）
    history: createWebHistory(import.meta.env.BASE_URL),

    // 定义路由配置数组
    routes: [
        // 重定向配置：访问根路径时重定向到仪表盘
        {path: '/', redirect: '/dashboard'},

        // 主布局组件（包含导航栏、侧边栏等公共布局）
        {
            path: '/',
            component: () => import('@/views/Layout.vue'), // 异步加载布局组件
            children: [
                {
                    path: 'dashboard',
                    meta: {title: 'Dashboard'},
                    component: () => import('@/views/Dashboard.vue')
                },
                {
                    path: 'about',
                    meta: {title: '关于'},
                    component: () => import('@/views/About.vue')
                },
                // 权限管理
                {
                    path: 'auth',
                    children: [
                        {
                            path: 'permission',
                            meta: {title: '资源管理'},
                            component: () => import('@/views/auth/Permission.vue')
                        },
                        {
                            path: 'role',
                            meta: {title: '角色管理'},
                            component: () => import('@/views/auth/Role.vue')
                        },
                        {
                            path: 'user',
                            meta: {title: '用户管理'},
                            component: () => import('@/views/auth/User.vue')
                        }
                    ]
                },
                // 古文
                {
                    path: 'gushici',
                    children: [
                        {
                            path: 'tengwanggexu',
                            meta: {title: '《滕王阁序》拼音版'},
                            component: () => import('@/views/gushici/滕王阁序.vue')
                        },
                        {
                            path: 'jiangjinjiu',
                            meta: {title: '《将进酒》拼音版'},
                            component: () => import('@/views/gushici/将进酒.vue')
                        },
                        {
                            path: 'manjianghong',
                            meta: {title: '《满江红·写怀》拼音版'},
                            component: () => import('@/views/gushici/满江红.vue')
                        },
                        {
                            path: 'yueyanglouji',
                            meta: {title: '《岳阳楼记》拼音版'},
                            component: () => import('@/views/gushici/岳阳楼记.vue')
                        },
                        {
                            path: 'chushibiao',
                            meta: {title: '《出师表》拼音版'},
                            component: () => import('@/views/gushici/出师表.vue')
                        },
                        {
                            path: 'duangexing',
                            meta: {title: '《短歌行》拼音版'},
                            component: () => import('@/views/gushici/短歌行.vue')
                        },
                        {
                            path: 'songdongyangmashengxu',
                            meta: {title: '《送东阳马生序》拼音版'},
                            component: () => import('@/views/gushici/送东阳马生序.vue')
                        },
                        {
                            path: 'beimingyouyu',
                            meta: {title: '《北冥有鱼》拼音版'},
                            component: () => import('@/views/gushici/北冥有鱼.vue')
                        },
                        {
                            path: 'dingfengbo',
                            meta: {title: '《定风波》拼音版'},
                            component: () => import('@/views/gushici/定风波.vue')
                        },
                        {
                            path: 'jiangchengziyimaozhengyueershiriyejimeng',
                            meta: {title: '《江城子·乙卯正月二十日夜记梦》拼音版'},
                            component: () => import('@/views/gushici/江城子·乙卯正月二十日夜记梦.vue')
                        },
                        {
                            path: 'jiangchengzimizhouchulie',
                            meta: {title: '《江城子·密州出猎》拼音版'},
                            component: () => import('@/views/gushici/江城子·密州出猎.vue')
                        }
                    ]
                },
            ]
        },

        // 登录页面（无需认证即可访问）
        {
            path: '/login',
            meta: {title: '登录'},
            component: () => import('@/views/Login.vue')
        },

        // 404 页面配置
        {
            path: '/notFound',
            meta: {title: '未找到页面'},
            component: () => import('@/views/404.vue')
        },

        // 通配符路由（处理未匹配的路径）
        {
            path: '/:pathMatch(.*)*', // Vue Router 4 的通配符语法
            redirect: '/notFound' // 重定向到 404 页面
        }
    ],
})

// 全局前置守卫（路由跳转前执行）
router.beforeEach((to, from, next) => {
    // 定义公开可访问的路径（不需要认证）
    const publicPages = ['/login', '/notFound']

    // 判断目标路由是否需要认证
    const authRequired = !publicPages.includes(to.path)

    // 获取认证状态（这里使用 localStorage 存储 token，实际项目建议使用 Vuex/Pinia）
    const loggedIn = localStorage.getItem('token')

    // 设置页面标题（优先使用路由的 meta.title，否则使用默认标题）
    document.title = to.meta.title || 'system-admin'

    // 路由守卫逻辑：
    if (authRequired && !loggedIn) {
        // 需要认证且未登录时，重定向到登录页
        next('/login')
    } else {
        // 认证通过或无需认证时，正常跳转
        next()
    }
})

// 导出路由实例
export default router