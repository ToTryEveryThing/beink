<template>
  <el-empty :image-size="300" description="404" />
  
</template>

<script>
import router from '../router/index'
  export default{
    mounted(){
      setTimeout(function(){
        router.push({"name":"main"})
      },3000)
    }
  }
</script>

<style scoped>

</style>