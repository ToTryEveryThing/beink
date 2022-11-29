<template>

  <h1>hello world</h1>
  <h2 :style="{opacity:opacity}">{{data}}</h2>
  <button @click="by">{{age}}</button>
  <button @click="hi">停止变换</button>
  <!-- 使用自杀方式destroy
    
  -->
</template>
<!-- 
     数据创建之前     数据创建完毕    ---》开始解析模板 生成虚拟dom(内存)  页面上还没有
    beforecreate      created  

     
    解析好了没放      此时把真实dom放入页面          挂载完毕       
    beforeMount                                    mounted      重要
 

   数据更新了，但页面上还未显示        数据更新完毕
         beforeUpdate                 updated   
         


      beforeDestroyed   destroyed ...    重要
         
          
    
 -->
  

<script>

  export default{
    data() {
      return {
        data:new Date(),
        age:0,
        opacity:1
      }
    },
    methods: {
      by(){
        this.age++;
      },
      hi(){
        console.log('hhh')
        // clearInterval(this.id)
        this.$destroy()
      }
    },
    mounted(){
        this. id = setInterval(()=>{
        this.data = new Date();
        this.opacity-=0.2
        if(this.opacity<=0)this.opacity=1
      },200)
    },
    beforeUnmount() {
      console.log('before')
    }
}
  

</script>

<style>


</style>
