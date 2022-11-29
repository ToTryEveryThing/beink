import { createRouter, createWebHistory } from 'vue-router'
import lin from '@/components/LinLin'
import liu from '@/components/LiuLiu'

const routes = [
  {
    path:'/lin',
    component:lin,
    meta:{
        title:"lin"
        // 这里可以放自定义信息
    }
  },
  {
    path:'/liu',
    component:liu,
    meta:{
        title:"liu"
    },
    // 独享路由  
    // beforeEnter: (to ,from) => {
        // ...
        // console.log(to)
        // console.log(from)
        // if(to.meta.title ==='liu'){
        //     alert('hhh')
        //     next('/')
        // }
        // else{
        //     next()
        // }
        
    // }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


// 全局前置 路由守卫  
// 每次 路由变化前 会被调用
// router.beforeEach((to,from,next)=>{
//     if(to.path==="/liu" && from.path==="/lin"){
//         //从lin>liu 过不去  转到'/'路径下
//         alert('你过不去')
//         next('/')
//     }
//     else{
//         next()
//     }
// })

// 全局后置守卫  
// 每次 路由变化后 会被调用
router.afterEach((to)=>{
    document.title = to.meta.title
})
export default router
