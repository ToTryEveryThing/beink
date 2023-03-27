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
      >
        <el-menu-item index="1">
            <el-icon><location /></el-icon>
            <span>用户列表</span>
        </el-menu-item>
      </el-menu>
        </el-aside>
        <el-main>
          <UserList/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import UserList from '@/components/admin/UserList.vue'
export default {
  components: { UserList },

}
</script>

<style>

</style>