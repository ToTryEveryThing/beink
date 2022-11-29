<template>
  <nav>
  
  <router-link to="/InIn">好多东西</router-link> &nbsp;|&nbsp;&nbsp;
  <router-link to="/click">监听事件</router-link>&nbsp;&nbsp; | &nbsp;&nbsp;
  <router-link to="/fadata">组件数据传输</router-link>&nbsp;&nbsp; | &nbsp;&nbsp;
  <router-link to="/">返回</router-link></nav> 
  <router-view/>
  


</template>


<script>


</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}


nav {
  padding: 10px;
  font-size:30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;

}

nav a.router-link-exact-active {
  color: #42b983;
}

</style>
