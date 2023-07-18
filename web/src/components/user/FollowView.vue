<template>
    <el-card body-style="padding:10px;" shadow="hover" 
    @click="goto(i.account)"
    style="margin:10px; cursor: pointer;" v-for="i in data" :key="i.id">
        <el-avatar :size="40" :src="i.userimage" />
        {{ i.account }}
        <el-tag style="float:right;">已关注</el-tag>
    </el-card>
</template>

<script setup>
import $ from 'jquery'
import router from '@/router';
import { onMounted, ref } from 'vue';
import config from '@/utiles/config';
    let data = ref([])
    onMounted(()=>{
        $.ajax({
            url: `${config.API_URL}/follow/getfollowerlist/`,
            type: 'GET',
            data:{
                id: localStorage.getItem("id")
            },
            success(res){
                if(res.code===200){
                    data.value = res.data
                }
            }

        })
    })

    const goto =i=>{
        router.push(`/author/${i}/`)
    }


</script>

<style>
</style>