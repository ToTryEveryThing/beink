<template>
  <el-dialog
    v-model="dialogVisible"
    width="30%"
    :show-close="false"
  >
  <el-input v-model="wanle" autosize type="textarea"></el-input>
  <el-button @click="save" size="small">save</el-button>
  </el-dialog>
  <el-dialog
    v-model="adddialogVisible"
    width="30%"
    :show-close="false"
  >
  <el-input v-model="ghhhhh" autosize type="textarea"></el-input>
  <el-button @click="add" size="small">add</el-button>
  </el-dialog>
  <el-button @click="addadd(),adddialogVisible = true"><el-icon><Plus /></el-icon></el-button>
  <el-row  justify="space-evenly">
    <el-col :span="8">
      <el-timeline>
        <el-timeline-item  size="large" :color="i.color" v-for="i in data" :key="i.target" :icon="i.icon" :timestamp="i.createTime" placement="top">
          <el-card class="father" body-style="padding-top:0px;" >
            <el-button class="edit" text size="small" @click="edit(i),dialogVisible=true"><el-icon><EditPen /></el-icon></el-button>
            <h2 style="margin-bottom:2px">{{ i.target }}</h2>
            <el-image v-for="l in i.images" :key="l" style="width: 100px; height: 100px; margin-right: 10px;" :src="l"  />
            <p style="margin:10px 0px;font-size: 10px;">{{ i.content }}</p>
            <el-link v-for="k in i.address" target="_blank" :key="k.address" :href="k.address" :type="k.type">
              {{ k.name }}
            </el-link>
            <br>
            <el-tag v-for="j in i.tabs" :key="j.name" effect="dark" :type="j.tabs">
              {{ j.name }}
            </el-tag>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-col>
  </el-row>
</template>


<script setup>
import { onMounted, ref } from 'vue';
import $ from 'jquery'
import config from '@/utiles/config';

  const dialogVisible = ref(false)
  const adddialogVisible = ref(false) 
  const ghhhhh = ref({})
  const wanle = ref("")
  const addcontent = ref({
      id:1,
      target:"xbox",
      content:"微软第一代游戏机",
      icon:"Loading",
      color:"#409eff",
      createTime:"2023-09-06",
      images:["https://cdn.beink.cn/background/wallhaven-gp5k23.jpg"],
      address:[
        {
          address:"https://bilibili.com",
          name:"bilibili",
          type:"success"
        }
      ],
      tabs:[
        {
          name:"动漫",
          tabs:"warning"
        }
      ]
  })
  const data = ref([])


  const show = ()=>{
    $.ajax({
        url:`${config.API_URL}/admin/target/show/`,
        type:'get',
        headers:{
          Authorization:"Bearer " + localStorage.getItem("jwt")
        },
        success(res){
          console.log(res.data)
          data.value = res.data
        } 
    })
  }

  onMounted(()=>{
    show()
  })


  const edit = (i)=>{
    wanle.value = JSON.stringify(i)
  }

  const save = ()=>{
    $.ajax({
        url:`${config.API_URL}/admin/target/update/`,
        type:'post',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        headers:{
          Authorization:"Bearer " + localStorage.getItem("jwt")
        },
        data:wanle.value,
        success(res){
          if(res.code === 200)
          dialogVisible.value = false 
          show()
        } 
    })
  }

  const add = ()=>{ 
    $.ajax({
        url:`${config.API_URL}/admin/target/add/`,
        type:'post',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        headers:{
          Authorization:"Bearer " + localStorage.getItem("jwt")
        },
        data:ghhhhh.value,
        success(res){
          if(res.code === 200)
          data.value.push(JSON.parse(ghhhhh.value))
        } 
    })
  }

  const addadd = ()=>{
    ghhhhh.value = JSON.stringify(addcontent.value)
  }

</script>


<style scoped>
  .el-link{
    margin-right:5px ;
  }
  .el-tag{
    margin-right: 10px;
    margin-top: 5px;
  }
  .edit{
    display: none;
    float: right;
    transition: all 2s;
  }
  .father:hover .edit{
    display: block;

  }

</style>