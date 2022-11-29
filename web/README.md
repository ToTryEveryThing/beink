# 伟大的作品 
---
                                    __  __    _            
      ___ _   _____  _______  __   / /_/ /_  (_)___  ____ _
     / _ \ | / / _ \/ ___/ / / /  / __/ __ \/ / __ \/ __  /
    /  __/ |/ /  __/ /  / /_/ /  / /_/ / / / / / / / /_/ / 
    \___/|___/\___/_/   \___ /   \__/_/ /_/_/_/ /_/\___ /  
                       /____/                     /____/  
                       
               _ ____       __                ____  __ __
     _      __(_) / /      / /_  ___         / __ \/ //_/
    | | /| / / / / /      / __ / _  \       / / / / / <   
    | |/ |/ / / / /      / /_/ /  __/      / /_/ / /| |  
    |__/|__/_/_/_/      /_ ___/\___/       \____/_/ |_| 

## 添加markdowm

### 官方网址 [v-md-editor]([介绍 | v-md-editor (code-farmer-i.github.io)](https://code-farmer-i.github.io/vue-markdown-editor/zh/))

### 安装

```
# 使用 npm
npm i @kangc/v-md-editor@next -S
```

### main.js配置

```
//Preview  渲染
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';

//editor  编辑
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

//挂载
app.use(VMdPreview)
app.use(VueMarkdownEditor)
```

### 使用

```
<template>
 		<!-- 编辑 -->
    <v-md-editor v-model="markdown" v-if="isShow" @save="save" height="800px"></v-md-editor>
    	<!-- 预览 -->
	<v-md-preview  :text="markdown"></v-md-preview >
</template>

<script>
export default {
  data() {
    return {
      markdown: '',
    };
  },
};
</script>
```



