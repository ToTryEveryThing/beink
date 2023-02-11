<template>
        <el-card class="score" v-if="!$store.state.guess.show_match" >
            <el-row justify="space-around" v-if="$store.state.guess.is_click">
                <el-col :span="7">
                    <el-card @click="choise('s')" class="flex "> 石头 </el-card>
                </el-col>
                <el-col :span="7">
                    <el-card @click="choise('j')" class="flex "> 剪子 </el-card>
                </el-col>
                <el-col :span="7">
                    <el-card @click="choise('b')" class="flex "> 布 </el-card>
                </el-col>
              </el-row>
        </el-card>
   
</template>

<script>
import { useStore } from 'vuex';
export default {
    setup(){
        console.log("choose")
        const store = useStore()
        const choise = (i)=>{
            if(store.state.guess.is_click){
                store.state.guess.socket.send(JSON.stringify({event:"match",match:i}))
                store.state.guess.is_click = false
            } 
        }
        return{
            choise
        }
    }

}
</script>

<style scoped>
.score{
    bottom: 10vh;
    left: 50%;
    width: 35vw;
    transform: translate(-50%);
    position:absolute;  
    z-index: 99999;
    border: none;
    background-color: rgba(240, 248, 255, 0);
    
}
.flex{
    display: flex;
    justify-content: center;
    align-items: center;
}
.flex:hover{
    transform: translateY(-8px);
    box-shadow: rgba(0, 0, 0, 0.2) 0px 60px 40px -7px;
    cursor: pointer;
}
</style>