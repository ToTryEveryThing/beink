<template>
    <el-button text @click="dialogTableVisible = true">
        搜索
    </el-button>
      <el-dialog v-model="dialogTableVisible" title="搜一下" :show-close="false">
                <el-input @change="sear" v-model="inputtt">
                    <template #append>
                        <el-button @click="sear">搜索</el-button>
                    </template>
                </el-input>
                <el-card v-for="i in datta" :key="i.id" style="margin-top:10px;" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>{{ i.title }}</span>
                            <el-button class="button" text>{{ i.post }}</el-button>
                        </div>
                    </template>
                    <div v-html="i.content" style="cursor: pointer;" @click="goto(i.id)"></div>
                </el-card >
      </el-dialog>


</template>

<script setup>
import $  from 'jquery'
import { ref } from 'vue'
import config from '@/utiles/config'
import router from '@/router'

    let dialogTableVisible = ref(false)
    let inputtt = ref('')
    let datta = ref([])
    const sear = ()=>{
        datta.value = []
        $.ajax({
            url:`${config.API_URL}/search/article/${inputtt.value}/`,
            type:'get',
            success(res){
                if(res.code===200){
                    datta.value = res.data
                }
            },
        })
    }

    const goto = (i)=>{
        router.push(`/article/${i}`)
    }


</script>

<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 0px;;
  }
  
  .text {
    font-size: 14px;
  }
  
  .item {
    margin-bottom: 18px;
  }
  
  .box-card {
    width: 480px;
  }
  .fashdfs{
    position: relative;
  }
</style>