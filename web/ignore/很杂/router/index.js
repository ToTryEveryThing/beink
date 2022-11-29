import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView'
import inin from '@/views/InIn'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path:'/click',
    name:'click',
    // .vue 可以省略
    component:() =>import('../views/OnClick.vue')
  },
  {
    path:'/InIn',
    name:'InIn',
    component:inin
  },
  {
    path:'/fadata',
    name:'fadata',
    component:()=>import('../views/FaData.vue')
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
