<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside width="200px">
          <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
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
      </el-menu>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {


}
</script>

<style>

</style>