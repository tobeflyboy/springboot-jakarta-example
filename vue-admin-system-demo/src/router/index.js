import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/', redirect: '/dashboard'},
        {
            path: '/',
            component: () => import('@/views/Layout.vue'),
            children: [
                {path: 'dashboard', meta: {title: 'Dashboard'}, component: () => import('@/views/Dashboard.vue')},
                {path: 'about', meta: {title: '关于'}, component: () => import('@/views/About.vue')},
                {
                    path: 'auth',
                    children: [
                        {path: 'permission', meta: {title: '资源管理'}, component: () => import('@/views/auth/Permission.vue')},
                        {path: 'role', meta: {title: '角色管理'}, component: () => import('@/views/auth/Role.vue')},
                        {path: 'user', meta: {title: '用户管理'}, component: () => import('@/views/auth/User.vue')}
                    ]
                }
            ]
        },
        {path: '/login', meta: {title: '登录'}, component: () => import('@/views/Login.vue')},
        {path: '/notFound', meta: {title: '未找到页面'}, component: () => import('@/views/404.vue')},
        {path: '/:pathMatch(.*)*', redirect: '/notFound'}
    ],
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/notFound']; // 添加需要公开访问的页面
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('token'); // 或者你的认证状态判断逻辑
    document.title = to.meta.title || 'system-admin';
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

export default router
