<template>
    <el-row class="hhhhhhhh">
        <el-col :span="6" v-for="i in tabs.title"  :key="i.name">
            <el-tag  style="cursor: pointer;"  @click="send(tabs.name)">
                {{ i }}
            </el-tag>
        </el-col>
    </el-row>
</template>

<script setup>
import { ref } from '@vue/reactivity'
import config from '@/utiles/config' 
import $ from 'jquery'
import {useStore} from 'vuex'
import router from '@/router';
    const store = useStore()
    let tabs = ref({})
    const send=(i)=>{
        router.push(`/study/${i}/`)
    }
    $.ajax({
        url:`${config.API_URL}/user/admin/git/showall/`,
        type:'post',
        success(res){
            console.log(res)
            if(res.code===1){
            for(let i=0;i<res.date.length;i++){
                if(res.date[i].name===store.state.account){
                    if(res.date[i].title.length===0){
                        break 
                    }
                    tabs.value = {"name":res.date[i].name,"title":JSON.parse(res.date[i].title)}
                }
            }
            }     
        },
    })
</script>

<style scoped>
.hhhhhhhh{
    display: flex;
    justify-content: space-around;
}
</style>