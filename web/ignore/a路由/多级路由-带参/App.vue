<template>
   <div>
      <router-link to="/main">main</router-link>
      <router-link to="/">返回</router-link>
   </div>
   <next></next>
   <router-view></router-view>
</template>

<script>

   import next from '@/components/NextPage'
   export default{
      components:{next}
   }
</script>

<style scoped>

   div{
      display: flex;
      justify-content: space-evenly;
   }
   a{
      display: block;
      font-size: 30px;
      color:orange;
      text-decoration: none;
   }

</style>

