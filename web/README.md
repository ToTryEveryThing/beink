
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


## 导出excel
> npm install xlsx --save
```js
exportData() {
  /* 定义要导出的数据 */
  const data = [
    { name: 'John Doe', age: 25, city: 'New York' },
    { name: 'Jane Doe', age: 30, city: 'London' },
    // ...
  ];

  /* 将数据转换为二进制 */
  const ws = XLSX.utils.json_to_sheet(data);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
  const binaryData = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });

  /* 创建 Blob 对象 */
  const blob = new Blob([binaryData], { type: 'application/octet-stream' });

  /* 下载文件 */
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'data.xlsx';
  link.click();
}
```
## 加载进度条
```npm  install nprogress -S```
```js
import { createRouter, createWebHistory } from 'vue-router'
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
    path: '/',
    name: 'home',
    component: () => import( '../views/homeView.vue')
  },
]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
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
```
main.vue
```css
#nprogress .bar {
  background: rgb(79, 133, 234) !important; //自定义颜色
}
```