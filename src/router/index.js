import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    // {
    //     path: '/',
    //     component: Login
    // },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register')
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export const setRouter = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {
        const manageRoute = {
            path: '/', name: "Manage", component: () => import('../views/Manage'), redirect: '/home', children: []
        }
        const menus = JSON.parse(storeMenus)
        menus.forEach(item => {
            if (item.path) {
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu)
            } else if (item.children.length) {
                item.children.forEach(item => {
                    if (item.path) {
                        let itemMenu = {
                            path: item.path.replace("/", ""),
                            name: item.name,
                            component: () => import('../views/' + item.pagePath + '.vue')
                        }
                        manageRoute.children.push(itemMenu)
                    }
                })
            }
        })

        // 获取当前的路由对象
        const currentRouteName = router.getRoutes().map(v => v.name)
        if (!currentRouteName.includes("Manage")) {
            router.addRoute(manageRoute)
        }
    }
}
setRouter()

router.addRoute({
    path: '/',
    component: () => import('../views/Manage'),
    redirect: "/home",
    children: [
        {path: 'home', name: '首页', component: () => import('../views/Home')},
        {path: 'user', name: '用户管理', component: () => import('../views/User')},
        {path: 'role', name: '角色管理', component: () => import('../views/Role')},
        {path: 'menu', name: '菜单管理', component: () => import('../views/Menu')},
        {path: 'person', name: '个人信息', component: () => import('../views/Person')},
        {path: 'file', name: '文件管理', component: () => import('../views/File')},
    ]
},)

// 路由守卫
router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
    store.commit("setPath")  // 触发store的数据更新
    // 未找到路由的情况
    if (!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        if (storeMenus) {
            next("/404")
        } else {
            // 跳回登录页面
            next("/login")
        }
    }
    next()  // 放行路由
})
export default router
