import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout/Index'

Vue.use(VueRouter)

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
      }
    ]
  }
]

const router = new VueRouter({
  routes: constantRoutes
})

export default router
