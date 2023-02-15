import { createRouter, createWebHistory } from 'vue-router'
import admin from '../views/AdminView'
import mainstudy from '../views/study/MainStudy'
import study from '../components/study/UtilsView'
import chat from '../views/ChatView'
import more from '../views/more/MoreView'
import guess from '../components/game/GuessGame'
const routes = [ 
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
    component:mainstudy,
    meta: {
      req:false,
    }, 
  },
  {
    path:"/study/:name/",
    component:study,
    meta:{
      req:false
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
