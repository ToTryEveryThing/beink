<template>

        <el-row class="score" v-if="!$store.state.guess.show_match">
            <el-col :span="8" class="flex" > 
                {{ $store.state.guess.my_score }}
            </el-col>
            <el-col :span="8" class="flex" > 
                <span class="tip" >{{ $store.state.guess.match_tip }} 
                    <el-icon class="is-loading"> <Loading /></el-icon>
                </span>
            </el-col>
            <el-col :span="8" class="flex">
                {{ $store.state.guess.ta_score }}
            </el-col>
        </el-row>

</template>

<script>

export default {
    setup(){
        console.log("score")
    }

}
</script>

<style scoped>
    .score{
        left: 50%;
        transform: translate(-50%);
        position:absolute;
        width:30vw;
        height: 10vh;
        font-size: 50px;
        color: black;
    }
    .flex{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .tip{
       font-size:20px;
    }
</style>