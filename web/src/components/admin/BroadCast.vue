<template>
    <el-card>
        <el-card> 
            <el-input  style="width:500px;" v-model="msg">
                <template #prepend>
                    <el-icon color="green"><Phone /></el-icon>
                </template>
                <template #append>
                    <el-button @click="send">发送</el-button>
                </template>
            </el-input>
        </el-card>
        <el-card class="box-card"> 
            <template #header>
              <div class="card-header">
                <span>当前状态</span>
              </div>
            </template>
            <div class="demo-progress">
                <el-progress type="dashboard" :percentage="percentage2" :color="colors" />
                
              </div>
          </el-card>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {useStore } from 'vuex'
let msg = ref("")
console.log("cast")
const percentage2 = ref(0)
const store = useStore();
store.dispatch("connectToWebSocket", {
    id: localStorage.getItem("id"),
    name: localStorage.getItem("name"),
    token: localStorage.getItem("jwt"),
});
const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 },
]


onMounted(() => {
  setInterval(() => {
    percentage2.value = (percentage2.value % 100) + 10
  }, 500)
})

const send = ()=>{
    store.state.socket.socket.send(JSON.stringify({"about":"all","msg":msg.value}))
    msg.value = ''
}

</script>

<style>

</style>