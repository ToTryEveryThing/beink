import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '@/components/MainPage.vue'
import on from '@/view/OnOne'
import detial from '@/view/DetaIl'

const routes = [
  {
    path:'/main',
    component:MainPage,
    children:[//子路由 数组形式
        {
            path:'on',//不再加'/'   
            component:on,
            children:[
              {
                name:'hhh',
                path:'detail',
                component:detial,
                // 这里的props数据会传给 detail组件使用 
                // 组件使用 props['a','b']
                // props:{a:'h',b:'hello world'}

                // 法二
                // props:true
                //....

                // 法三
                // ...

              }
            ]
        }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
