<template>

    
    <el-row>
        <el-col :span="24">
            <el-button type="primary" @click="send">翻译</el-button>
            <el-select v-model="TO" style="float:right;" placeholder="翻译为" v-loading="loading" size="large">
                <el-option
                  label="中文"
                  value="zh"
                />
                <el-option
                  label="英文"
                  value="en"
                />
            </el-select>
        </el-col>
        <el-col :span="12">
            <el-input v-model="query" placeholder="Please input" type="textarea" :rows="8" class="input" clearable />
        </el-col>
        <el-col :span="12" >
            <el-input v-model="result"  type="textarea" :rows="8"  class="input" clearable />
        </el-col>
      </el-row>
</template>

<script>
import config from '../../utiles/config'
import $ from 'jquery'
import {ref } from 'vue'
export default {
    setup(){
        let query = ref("")
        // let from = ref("auto")
        let TO = ref("")
        let loading  = ref(false)
        let result = ref("")
        const options = [
            {
                value: 'zh',
                label: '中文',
            },
            {
                value: 'en',
                label: '英文',
            },
        ]  
        const send=()=>{
            if(TO.value==="")TO.value = "zh"
            loading.value =  true
            $.ajax({
                url:`${config.API_URL}/translate/query/`,
                type:'post',
                data:{
                    query:query.value,
                    from:"auto",
                    to:TO.value
                },
                success(res){
                    console.log(res)
                    result.value = res.trans_result[0].dst
                    loading.value = false
                },
            })
        }
        return{
            query,send,result,options,TO,loading
        }

    }
}
</script>

<style scoped>
.input{
    padding: 10px;
}
</style>