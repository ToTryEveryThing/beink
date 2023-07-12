<template>
  <router-view />
</template>

<script setup >
import {useStore} from 'vuex'
  import darkClass from './utiles/dark'
  darkClass(localStorage.getItem("theme"))
  const store = useStore()
  const jwt = localStorage.getItem("jwt");
  if(jwt){
      store.commit("updateToken",jwt)
      store.dispatch("getinfo",{
          success(){
          },
          error(){
          }
      })
  }
</script>
<style>
  a{
    text-decoration: none;
  }
  :root{
    --backColor:#f7f7f7;
    --color:#ddd;
  }
  body::-webkit-scrollbar{
    width: 5px; 
    background: #dfe6e900;
  }
  body::-webkit-scrollbar-thumb { 
    border-radius: 3px; 
    background:#b2bec3;
  }  
  body{
    margin: 0;
  }
  
  #nprogress .bar {
    background: rgb(79, 133, 234) !important; 
  }
</style>