<template>
   <h1>hellpo owrld</h1>
   <h1>世界 你好</h1>
   <button  @click="hh">{{vue.name}}---{{age}}</button>
</template>

<script>
   // 数据变动修改 需要配合reactive 使用
   // reactive 响应式数据
   // ref  常用于基本类型
   import {reactive ,ref} from 'vue'
   export default{
      setup() {
         let age = ref(22)
         let vue = reactive({
            name:'liming',
         })
         // let vue = reactive('liming')
            
         //ref的数据必须使用value修改
         function hh(){
            age.value = 66
         }
         return {
            vue,
            age,hh
         }
      }

  }

</script>

<style>


  
</style>