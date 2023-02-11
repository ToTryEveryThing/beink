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
  </el-menu>
  <keep-alive>
    <router-view/>
  </keep-alive>
</template>

<script  setup>

import { ref , onUnmounted} from 'vue'
const activeIndex = ref("/more/chat")
const handleSelect = (key,keyPath) => {
  console.log(key, keyPath)
  activeIndex.value = key
}
onUnmounted(() => {
  console.log("结束了")

})
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
</style>
