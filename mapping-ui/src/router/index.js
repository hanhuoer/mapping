import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout/Index'

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};

export const constantRoutes = [
    {
        path: '/login',
        component: () => import('../views/Login')
    },
    {
        path: '/',
        component: Layout,
        children: [
            {
                path: '/',
                component: () => import('@/views/Home')
            },
            {
                path: '/about',
                component: () => import('@/views/About')
            },
            {
                path: '/client',
                component: () => import('@/views/Client')
            },
            {
                path: '/overview',
                component: () => import('@/views/Overview')
            }
        ]
    }
];

const router = new VueRouter({
    routes: constantRoutes
});

export default router
