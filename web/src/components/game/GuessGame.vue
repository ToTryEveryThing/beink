<template>
    <score/>
    <choose/>
    <Teleport to="body" v-if="$store.state.guess.show_match">
        <el-card shadow="never" class="center" v-if="!$store.state.guess.is_matching" @click="match"> 
            开始匹配    
        </el-card>
        <el-card shadow="never" class="center"  v-else @click="match"> 
            匹配中... <el-icon class="is-loading"><Loading /></el-icon>   
        </el-card>
    </Teleport>
    <el-row>
        <el-col :span="12" class="grid-content bg-red flex">
            <el-card  shadow="never" :class="{ 'goleft': !$store.state.guess.show_match }"> 
                <el-image style="width: 100px; height: 100px" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" fit="fit" />
                Name : {{ $store.state.guess.my_name }}
            </el-card>
        </el-col>
        <el-col :span="12" class="grid-content bg-blue flex ">
            <el-card shadow="never" :class="{ 'goright': !$store.state.guess.show_match }"   v-if="$store.state.guess.match_status"> 
                <el-image style="width: 100px; height: 100px" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" fit="fit" />
                Name : {{ $store.state.guess.ta_name }}
            </el-card>
        </el-col>
      </el-row>
</template>

<script > 
import { onMounted } from 'vue';
import {useStore} from 'vuex'
import score from './ScoreView.vue'
import choose from './ChooseView'
export default{
   components:{score,choose},
    setup(){
        const store = useStore();
        store.commit("up",sessionStorage.getItem("name"))
        onMounted(()=>{
            let socket = null;
            console.log("nihao ")
            const socketUrl = `wss://so.beink.cn/GuessWebsocket/${store.state.guess.my_name}`;
            socket = new WebSocket(socketUrl);
            store.commit("updateSocket",socket)
            socket.onopen = () => {
                console.log("连接")
            }
            socket.onmessage = msg => {
                if(JSON.parse(msg.data).status==="duishou")
                    store.commit("update",JSON.parse(msg.data))
                else if(JSON.parse(msg.data).status==="matching")
                    store.commit("updateALL",JSON.parse(msg.data))
                else if(JSON.parse(msg.data).status==="error"){
                    // 异常了
                    store.commit("logoutt")
                    socket.send(JSON.stringify({"event":"error","name":store.state.guess.ta_name}))
                }
                else{
                    console.log(msg,"结束了")
                    setTimeout(()=>{
                        store.commit("logoutt")
                    },2000)
                }
            }
            socket.onerror = () =>{
                store.commit("logoutt")
                console.log("error");
            }
            socket.onclose = () => {
                store.commit("logoutt")
                console.log("disconnected!");
            }
        })
        const match =()=>{ 
            store.commit("match")
            if(store.state.guess.is_matching===true)
                store.state.guess.socket.send(JSON.stringify({event:"start"}));
            else{
                store.state.guess.socket.send(JSON.stringify({event:"stop"}));
            }
        }
        return{
            match
        }
    }
} 

</script>

<style scoped>
    .center{
        cursor: pointer;
        width: 200px;
        height: 60px;
        position: absolute;
        top: 50%;
        left: 50%;  
        display: table-cell;
        vertical-align: middle;
        text-align: center;
        transform: translate(-50%);
        background-color:#dcaaff;
        border: none;
    }
    .center:hover{
        box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
    }
    .bg-red{
        background-color: rgba(255, 0, 0, 0.432);
    }
    .bg-blue{
        background-color: rgba(0, 0, 255, 0.418);
    }
    .grid-content {
        border-radius: 4px;
        min-height: 93vh;
    }
    .flex{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .goleft{
        position: absolute;
        left: 0;
        top: 0;
        border: none;
        background-color: rgba(240, 248, 255, 0);
    }
    .goright{
        position: absolute;
        right: 0;
        top: 0;
        border: none;
        background-color: rgba(240, 248, 255, 0);
    }
</style>