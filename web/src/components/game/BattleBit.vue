<template>
  <el-button @click="show" text>  总人数：{{ count }}</el-button>
  <el-table border :data="data"  style="width: 100%">
    <el-table-column  prop="Name"  label="Name" />
    <el-table-column prop="Map" sortable label="Map" />
    <el-table-column prop="Region" sortable label="Region" />

    <el-table-column
      sortable
      prop="MaxPlayers"
      label="MaxPlayers"
    >
    <template #default="scope">
      <el-tag
        type="primary"
        disable-transitions
        size="large">
        {{ scope.row.MaxPlayers/2 }} VS {{ scope.row.MaxPlayers/2 }}
        </el-tag>
    </template>  
    </el-table-column>

    <el-table-column prop="Players" sortable  label="Players">
      <template #default="scope">
          {{ scope.row.Players }} / {{ scope.row.MaxPlayers }}
      </template> 
    </el-table-column>
    <el-table-column prop="DayNight" label="DayNight"/>

  </el-table>
</template>

<script setup>
import  $ from 'jquery'
import { onMounted, ref } from 'vue'

  const data = ref([])
  const count = ref(0)
  onMounted(()=>{
    show()
  })

  setInterval(function(){
    show()
  },
  10000*5)
  
  const show = ()=>{
    $.ajax({
      url:"https://publicapi.battlebit.cloud/Servers/GetServerList",
      type:'get',
      success(res){
        count.value = 0
        data.value = res
        .filter(s=>{
          if(s.Map ==="Namak" && s.Region === "Japan_Central")
          alert("HHHHHHHHHHHHHH")
          count.value += s.Players
          count.value += s.QueuePlayers
          return s.Players
        })
        .sort((a,b)=>{return b.Players - a.Players })
      }
    })
  }

</script>
