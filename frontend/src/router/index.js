import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/dash',
      name: 'Dashboard',
      component: () => import('@/components/Dashboard.vue'),
      redirect: '/dash/app/list',

      children: [

        {
          path: 'app/list',
          component:  () => import('@/components/AppList.vue'),
        },
        {
          path: 'app/:id',
          component:  () => import('@/components/AppDetail.vue'),
        },
      ]
    },

    {
      path: '*',
      component: () => import('@/components/Error404.vue')
    }
  ]
})
