<template>

  <div>
    <h1>{{name}}</h1>
    <hr>
    <ShAre></ShAre>
    <hr>
  </div>

</template>

  <hi/>

<script>
import ShAre from './components/ShAre.vue';
  
  export default{
    name:'App',
    components:{
    ShAre 
    },
    data() {
      return {
        name:'hello world'
      }
    },
  }

</script>

<style>


</style>
