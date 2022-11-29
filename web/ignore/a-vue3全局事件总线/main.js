// 整个项目的入口文件

import { createApp } from 'vue'

//  引入 app组件 所有组件的父组件
import App from './App.vue'

import mitt from 'mitt'


const app = createApp(App)


app.config.globalProperties.$bus = mitt()
app.mount('#app')


