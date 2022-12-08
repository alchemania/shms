import * as VueRouter from 'vue-router'
import healthCard from '../components/Personal/healthCard.vue'
import NATquery from '../components/NAT/NATquery.vue'
import NATbook from '../components/NAT/NATbook.vue'
import login from '../components/login/login.vue'
import Navbar from '../components/Navbar.vue'
import cookies from "vue-cookies";
import RiskArea from '../components/RiskArea/RiskArea.vue'

const routes = [
    {
        path: '/',
        component: login,
    },
    {
        path: '/login',
        component: login,
    },
    {
        path: '/main',
        component: Navbar,
        children: [
            {
                path: '/healthcard',
                component: healthCard,
            },
            {
                path: '/natquery',
                component: NATquery,
            },
            {
                path: '/natbook',
                component: NATbook,
            },
            {
                path: '/riskarea',
                component: RiskArea,
            },
        ]
    }

]

const router = VueRouter.createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: VueRouter.createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

//登录拦截，拦截非登录请求
router.beforeEach((to, from, next) => {
    if (to.path.startsWith('/login')) {
        next()
    } else {
        let user = cookies.get('userid')
        if (!user) {
            next({
                path: '/login'
            })
        } else {
            next()
        }
    }
});

export default router