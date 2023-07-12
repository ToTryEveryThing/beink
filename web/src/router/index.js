import { createRouter, createWebHistory } from 'vue-router'
import admin from '../views/AdminView'
import Mainarticle from '../views/study/MainStudy'
import article from '../components/study/UtilsView'
import more from '../views/more/MoreView'
import guess from '../components/game/GuessGame'
import login from '../views/AccountLogin'
import snake from '@/components/game/SnakeGame'
import userlist from '@/components/admin/UserList'
import broadcast from '@/components/admin/BroadCast'
import me from '@/components/study/MyArticle'
import log from '@/components/admin/OperationLog'
import profile from "@/components/UserProfile" 

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
    children:[
      {path:'userlist', component:userlist},
      {path:'broadcast', component:broadcast},
      {path:'log', component:log}
    ],
    meta: {
      req:true,
      login:true
    }, 
  },
  {
    path:"/article",
    name:'article',
    component:Mainarticle,
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
    path:"/user/profile",
    name:'profile',
    component:profile,
    meta: {
      req:false,
      login:false
    }, 
  },
  {
    path:"/article/:id/",
    component:article,
    meta:{
      req:false,
      login:true
    }
  },
  {
    path:"/author/:name/",
    component:me,
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
      {path:'snake',component: snake}
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
    // if(to.meta.login){
    //   if(localStorage.getItem("jwt")){
    //     next()
    //   }else{
    //     next("/login")
    //   }
    // }
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
