<template>
  <h1 @click="h" > {{name}}</h1>
  <h2 ref="title">
    {{Name}}
  </h2>
  <button @click="hh">点我</button>
</template>


<script>
  // 混合  共有的东西  不必写多次 
  // 放入js文件中  调用即可
  import {a,b} from '@/mixin'
  export default{
      data() {
        return {
          
        }
      },
      methods: {
        hh(){
          console.log(this.$refs.title)
        }
      },
      // 引入方法
      mixins:[a,b]
      
  }



</script>