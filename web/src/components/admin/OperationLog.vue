<template>
  <el-card>
    <el-table :data="table" border  style="width: 100%">
      <el-table-column prop="id" label="ID" width="180" />
      <el-table-column prop="username" label="Name" width="180" />
      <el-table-column prop="method" label="Method" />
      <el-table-column prop="params" label="Params" />
      <el-table-column prop="ip" label="IP" />
      <el-table-column prop="createDate" label="createDate" />
      <el-table-column prop="result" label="Result" />
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="url" label="Url" />
    </el-table>
    <el-pagination
    @current-change="handleCurrentChange"
      background  
       @next-click="ppage(1)" 
       @prev-click="ppage(-1)" 
      large
      layout="prev, pager, next"
      :total="total"
    />
  </el-card>
</template>

<script>

import $ from 'jquery'
import config from '@/utiles/config'
import { onMounted, reactive, toRefs} from 'vue'
import { useStore } from 'vuex';
import moment from 'moment/moment'
export default{

  setup(){
    const store = useStore();
    const vue = reactive({
      table:[],
      total:0,
      page:1
    })
    const show = ()=>{
      $.ajax({
          url:`${config.API_URL}/admin/log/`,
          type:'get',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          data:{
            page:vue.page
          },
          success(res){
            if(res.code===200){
              console.log(res)
              vue.total = res.count
              vue.table = res.data
              for (let i in vue.table){
                  vue.table[i].createDate = moment(vue.table[i].createDate).utcOffset(480).format('YYYY-MM-DD  HH:mm:ss')
              }
              console.log(vue.table)
            }
          }
      })
    }
    const ppage = (i)=>{
        vue.page += i;
        show()
    }
    const handleCurrentChange = (number)=>{
        vue.page = number
        show()
    }

    onMounted(()=>{
      show()
    })

    return{
      ...toRefs(vue),
      ppage,
      handleCurrentChange
      
    }
  }

}















</script>

<style>

</style>