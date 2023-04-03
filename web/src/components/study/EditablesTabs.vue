<template>
    <el-button @click="go" text>
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-3"><path d="M12 20h9"></path><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path></svg>
        编辑我的文章
    </el-button>
</template>

<script setup>
// import {useStore} from 'vuex'
import router from '@/router';
 const go = ()=>{
    router.push("/article/me/")
 }

</script>

<style scoped>
.hhhhhhhh{
    display: flex;
    justify-content: space-around;
}
</style>