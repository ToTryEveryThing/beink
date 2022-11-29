import { createRouter, createWebHistory } from 'vue-router'
import login from '../views/AccountLogin'
import register from '../views/AccountRegister'
import admin from '../views/AdminView'
import study from '../views/UtilsView'
const routes = [ 
  {
    path:'/login',
    name:'login',
    component:login,
    meta: {
      req:false,
    }, 
  },
  {
    path:'/register',
    name:'register',
    component:register,
    meta: {
      req:false,
    }, 
  },
  {
    path:'/',
    name:'main',
    component:()=>import ("../views/MainM.vue"),
    meta: {
      req:false,
    }, 
  },
  {
    path:"/admin",
    name:'admin',
    component:admin,
    meta: {
      req:true,
    }, 
  },
  {
    path:"/study",
    name:'study',
    component:study,
    meta: {
      req:false,
    }, 
  },
  {
    path: '/404',
    name: '404',
    component: () => import("../components/NotFound.vue"),
    meta: {
      req:false,
    }, 
  },
  {
    path: '/:pathMatch(.*)',
    redirect: '/404',
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to,from,next)=>{
    if(to.meta.req){
        if(sessionStorage.getItem("name")==="admin" && localStorage.getItem("jwt")){
          next()
        }else{
          next('/404')
        }
    }else{
      next()
    }
})
export default router
