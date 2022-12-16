/*
 * @Author: 米叔 849299509@qq.com
 * @Date: 2022-11-29 08:55:32
 * @LastEditors: 米叔 849299509@qq.com
 * @LastEditTime: 2022-12-13 15:46:30
 * @FilePath: \web\src\main.js
 * @Description: 
 * 
 * Copyright (c) 2022 by 米叔 849299509@qq.com, All Rights Reserved. 
 */
// 整个项目的入口文件

import { createApp } from 'vue'

//  引入 app组件 所有组件的父组件
import App from './App.vue'
// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'

import 'element-plus/dist/index.css'
import router from './router'
import store from './store'
// 事件总线
import mitt from 'mitt'

//icon
import * as ELIcons from '@element-plus/icons-vue'

//VMdPreview
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';

//editor
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';

// 引入你所使用的主题 此处以 github 主题为例
import githubTheme from '@kangc/v-md-editor/lib/theme/github';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// highlightjs
import hljs from 'highlight.js';

VueMarkdownEditor.use(githubTheme, {
    Hljs: hljs,
});
VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
const app = createApp(App)
for (let iconName in ELIcons) {
	app.component(iconName, ELIcons[iconName])
}
app.config.globalProperties.$bus = new  mitt()

app.use(VMdPreview)
app.use(VueMarkdownEditor)
app.use(store)

// app.use(ElementPlus)
app.use(router)
app.mount('#app')


