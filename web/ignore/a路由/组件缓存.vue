<template>
   <div>
      <router-link to="/main">main</router-link>
      <router-link to="/">返回</router-link>
   </div>
   <next></next>
   <!-- keep-alive
            可以缓存组件中的数据 以确保进入其他组件不被销毁
            include="组件名字" 不写默认全部缓存
            :incldue="['xxx','xxx']" 多个
            export default {
               name: 'HelloWorld',就是这个名字
            }
         active-class
            被选中的样式
   -->
   <keep-alive include="组件名字">
      <router-view></router-view>
   </keep-alive>
   
</template>

<script>

   export default{
      activated() {
         // 当我被选中时 我就时激活状态
      },
      deactivated(){
         // 当切换到其他组件时 我就是失活状态
      }
   }


</script>


