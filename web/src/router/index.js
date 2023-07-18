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
import profile from "@/components/user/UserProfile"
import background from '@/components/admin/BackgroundImages.vue' 

// 路由切换进度条
// https://blog.csdn.net/weixin_73337633/article/details/131376644
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({
  easing: 'ease', // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3,// 初始化时的最小百分比,
})

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
      {path:'log', component:log},
      {path:'background', component:background},
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
      login:true,
    }, 
    beforeEnter: (to ,from,next) => {
        if(localStorage.getItem("jwt")){
            next()
        }
        else{
            next('/404')
        }
        
    }
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
  //必须登录才能访问所有的页面
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


router.beforeEach((to, from, next) => {
  //这这里开启加载条
  NProgress.start()
  next()
})
router.afterEach(() => {
  // 在即将进入新的页面组件前，关闭掉进度条
  NProgress.done()
})


export default router
