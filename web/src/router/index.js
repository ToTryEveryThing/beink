import { createRouter, createWebHistory } from 'vue-router'
import admin from '../views/AdminView'
import mainstudy from '../views/study/MainStudy'
import study from '../components/study/UtilsView'
import chat from '../views/ChatView'
import more from '../views/more/MoreView'
import guess from '../components/game/GuessGame'
import login from '../views/AccountLogin'
const routes = [ 
  {
    path:'/',
    name:'main',
    component:()=>import ("../views/MainM.vue"),
    meta: {
      req:false,
      login:true
    }, 
  },
  {
    path:"/admin",
    name:'admin',
    component:admin,
    meta: {
      req:true,
      login:true
    }, 
  },
  {
    path:"/study",
    name:'study',
    component:mainstudy,
    meta: {
      req:false,
      login:true
    }, 
  },
  {
    path:"/login",
    name:'login',
    component:login,
    meta: {
      req:false,
      login:false
    }, 
  },
  {
    path:"/study/:name/",
    component:study,
    meta:{
      req:false,
      login:true
    }
  },
  {
    path:"/more",
    name:'more',
    component:more,
    children:[
      {path:'guess',component:guess},
      {path:'chat',component:chat}
    ],
    meta: {
      req:false,
      login:true
    }, 
     // 独享路由  
    beforeEnter: (to ,from,next) => {
        if(localStorage.getItem("jwt")){
            next()
        }
        else{
            next('404')
        }
        
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import("../components/NotFound.vue"),
    meta: {
      req:false,
      login:true
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
    if(to.meta.login){
      if(localStorage.getItem("jwt")){
        next()
      }else{
        next("/login")
      }

    }
    if(to.meta.req){
        if(sessionStorage.getItem("role")==="admin" && localStorage.getItem("jwt")){
          next()
        }else{
          next('/404')
        }
    }else{
        next()
    }
})
export default router
