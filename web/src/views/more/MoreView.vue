<template>
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          @select="handleSelect"
          router
        >
          <el-menu-item index="/">
            <el-image  src="https://beink.cn/ink.png" style="width:50px;"></el-image>
          </el-menu-item>
          <div class="flex-grow" />
          <el-menu-item index="/more/chat"><el-icon><ChatDotRound /></el-icon>聊天</el-menu-item>
          <el-menu-item index="/more/guess"><el-icon><User /></el-icon>猜拳</el-menu-item>
          <el-menu-item index="/more/snake"><el-icon><User /></el-icon>Snake</el-menu-item>
        </el-menu>
         <router-view />


</template>

<script  setup>
import $ from 'jquery'
import { ref , onMounted} from 'vue'
document.getElementsByTagName('body')[0].style.backgroundImage = ``
const activeIndex = ref("/more/chat")
$('html').css({'--backColor':''})  
$('html').css({'--color':''})
const handleSelect = (key) => {
  activeIndex.value = key
  localStorage.setItem("menu",key)
}
onMounted(()=>{
  handleSelect(localStorage.getItem("menu") || "/more/chat" )
})
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
.el-main{
  padding: 0;
}

</style>
