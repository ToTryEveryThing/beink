<template>
    broadcast
    <el-input v-model="msg"></el-input>
    <el-button @click="send">发送</el-button>
</template>

<script setup>
import { ref } from 'vue';
import {useStore } from 'vuex'
let msg = ref("")
const store = useStore();
store.dispatch("connectToWebSocket", {
    id: localStorage.getItem("id"),
    name: localStorage.getItem("name"),
    token: localStorage.getItem("jwt"),
});
const send = ()=>{
    store.state.socket.socket.send(JSON.stringify({"about":"all","msg":msg.value}))
}

</script>

<style>

</style>