// 整个项目的入口文件

import { createApp } from 'vue'

//  引入 app组件 所有组件的父组件
import App from './App.vue'

import router from './router'
import store from './store'

createApp(App).use(store).use(router).mount('#app')


