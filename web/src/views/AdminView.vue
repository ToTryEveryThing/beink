<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="auto">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          :collapse="isCollapse"
          @close="handleClose"
          router
        >
          <el-menu-item index="/">
              <el-icon><House /></el-icon>
              <span>main</span>
          </el-menu-item>
          <el-menu-item index="/admin/userlist">
            <el-icon><User /></el-icon>
              <span>用户列表</span>
          </el-menu-item>
          <el-menu-item index="/admin/broadcast">
            <el-icon><Connection /></el-icon>
            <span>广播</span>
          </el-menu-item>
          <el-menu-item index="/admin/log">
            <el-icon><Guide /></el-icon>
            <span>日志</span>
          </el-menu-item>
          <el-menu-item index="/admin/background">
            <el-icon><Picture /></el-icon>
            <span>背景图片</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
            <el-radio-button  :label="true"><el-icon><Fold /></el-icon></el-radio-button>
            <el-radio-button :label="false"><el-icon><Expand /></el-icon></el-radio-button>
          </el-radio-group>
        </el-header>
        <el-main style="padding:0;">
            <keep-alive>
              <router-view ></router-view>
          </keep-alive>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from 'vue'
  const isCollapse = ref("")
</script>

<style scoped>
.el-header{
  background-color: white;
}
</style>