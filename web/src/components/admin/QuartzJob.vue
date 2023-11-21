<template>
    <el-table :data="tableData" border  style="width: 100%">
        <el-table-column fixed prop="jobName" label="任务名"  />
        <el-table-column prop="jobGroup" label="分组名"  />
        <el-table-column prop="jobClassName" label="类名" width="400" />
        <el-table-column prop="description" label="描述"  />
        <el-table-column prop="triggerState" label="状态"  >
            <template #default="scope" >
                <svg @click="startOrPause(scope.$index,scope.row)" v-if="scope.row.triggerState == 'NORMAL'"  xmlns="http://www.w3.org/2000/svg" class="rotate-icon" width="24" height="24" viewBox="0 0 24 24" style="    cursor: pointer;fill: rgba(14, 133, 241, 1);transform: scaleX(-1);msFilter:progid:DXImageTransform.Microsoft.BasicImage(rotation=0, mirror=1);"><path d="M2 11h5v2H2zm15 0h5v2h-5zm-6 6h2v5h-2zm0-15h2v5h-2zM4.222 5.636l1.414-1.414 3.536 3.536-1.414 1.414zm15.556 12.728-1.414 1.414-3.536-3.536 1.414-1.414zm-12.02-3.536 1.414 1.414-3.536 3.536-1.414-1.414zm7.07-7.071 3.536-3.535 1.414 1.415-3.536 3.535z"></path></svg>
                <svg @click="startOrPause(scope.$index,scope.row)" v-else xmlns="http://www.w3.org/2000/svg"  width="24" height="24" viewBox="0 0 24 24" style="    cursor: pointer;fill: rgba(255, 5, 5, 1);transform: scaleX(-1);msFilter:progid:DXImageTransform.Microsoft.BasicImage(rotation=0, mirror=1);"><path d="M8 7h3v10H8zm5 0h3v10h-3z"></path></svg>
            </template>
        </el-table-column>
        <el-table-column prop="cronExpression" label="表达式"  />
        <el-table-column fixed="right" label="Operations">
          <template #default="scope">
            <el-button link type="danger" size="small" @click="deleteJOb(scope.row)"
              >delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
</template>

<script setup>
import config from "@/utiles/config";
import $ from 'jquery'
import { success } from "@/utiles/message";
import { onMounted, ref } from "vue";
import { useStore } from "vuex";


    const store = useStore()

    const tableData = ref([])
    onMounted(()=>{
        getList()
    })
    const getList = ()=>{
        $.ajax({
          url:`${config.API_URL}/admin/quartz/job/list/`,
          type:'get',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          success(res){
            if(res.code === 200){
                success(res.message)
                tableData.value = res.data
            }
          }
      })
    }
    const startOrPause = (i,e)=>{
        let s = e.triggerState == "NORMAL" ? 'pause' : "start"
        $.ajax({
          url:`${config.API_URL}/admin/quartz/job/`+ s + "/",
          type:'post',
          data:{
            name:e.jobName,
            group:e.jobGroup
          },
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          success(res){
            if(res.code === 200){
                success(res.message)
                tableData.value[i].triggerState = e.triggerState == "NORMAL" ? 'PAUSE' : "NORMAL"
            }
          }
        })
    }
    const deleteJOb = (e)=>{
        console.log(e.jobName,e.jobGroup)
        $.ajax({
          url:`${config.API_URL}/admin/quartz/job/delete/${e.jobName}/${e.jobGroup}/`,
          type:'delete',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          success(res){
            if(res.code === 200){
                success(res.message)
                getList()
            }
          }
      })
    }

</script>

<style scoped>
@keyframes rotate {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }

  .rotate-icon {
    display: inline-block; /* 让图标作为内联块元素，这样可以设置宽高 */
    animation: rotate 2s linear infinite; /* 应用旋转动画，持续时间为2秒，线性运动，无限循环 */
  }

</style>