<template>
   <div>
      <router-link to="/main">main</router-link>
      <router-link to="/">返回</router-link>
      <!-- :replace="true" 简写 replace   历史记录被销毁无法回头 -->
   </div>
      <button @click="fan" >点我也能返回</button>


   <next></next>
   <router-view></router-view>
</template>

<script>

   import next from '@/components/NextPage'
   export default{
      components:{next},
      // methods: {
         // fan(){点击跳转
            // this.$router.push({
            //    // 里面的写法
            //    path:'',
            //    name:'',
            //    ..
            // })
               // this.$router.replace({//同理
                  
               // })
               // this.$router.go(3) 前进3步
               // this.$router.go(-3) 后退3步
               // this.$router.forword() 前进
         // }
      // },
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

