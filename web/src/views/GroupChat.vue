<template>
    <el-scrollbar height="400px"> 
        <li class="animate__animated animate__fadeIn gasdg"   v-for="i in $store.state.socket.groupChat" :key="i">
            <div >
                {{ i.name }}&nbsp;
                <svg  xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1" stroke-linecap="round" stroke-linejoin="round" class="feather feather-corner-right-down gasgsdg"><polyline points="10 15 15 20 20 15"></polyline><path d="M4 4h7a4 4 0 0 1 4 4v12"></path></svg>
                <span style="float:right;font-size:12px" ><el-icon><Timer color="green" /></el-icon>{{ i.time }}</span>
            </div> 
            <el-card shadow="none" body-style="padding:10px">
                {{ i.message }}
            </el-card>
        </li>
        <div id="hashasdh"></div>
    </el-scrollbar>
   <el-input size="large" @keyup.enter="send" placeholder="向世界问好" v-model="message" >
    <template #append> 
        <el-button @click="send">
            发送
        </el-button>
    </template>
   </el-input>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { warning } from "@/utiles/message";

    let message = ref()
    const store = useStore()

    const send = ()=>{

        if(message.value==''){
            warning("不发空信息")
            return
        }

        if(message.value.length>20){
            warning("有点长")
            message.value = ""
            return
        }

        store.state.socket.socket.send(
            JSON.stringify({about:"group", message:message.value})
        );
        message.value = ""
        setTimeout(function(){
            document.getElementById("hashasdh").scrollIntoView(false);
        },200)
    }
</script>

<style scoped>
    .gasdg{
        padding-bottom: 10px;
    }
    .gasgsdg{
        margin-top: 1px;
    }
    .gasdg svg{
        position: relative;
        top: 3px;
    }
</style>