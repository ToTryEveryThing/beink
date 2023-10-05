<template>
  <el-button @click="addddddd()"><el-icon><Plus /></el-icon></el-button>
  <el-row  justify="space-evenly">
    <el-col  :md="8" :xs="24" :sm="16">
      <el-timeline>
        <el-timeline-item  size="large" :color="i.color" v-for="i in data" :key="i.target" :icon="i.icon" :timestamp="i.createTime" placement="top">
          <el-card class="father" body-style="padding-top:0px;" >
            <el-collapse >
              <el-collapse-item :title="i.target" name="1">
                <template #title>
                  {{ i.target }}
                  <el-button class="edit" text size="small" @click="edit(i)"><el-icon color="#2c3e50"><EditPen /></el-icon></el-button>
                </template>
                <el-image v-for="l in i.images" :key="l" style="width: 100px; height: 100px; margin-right: 10px;" :src="l"  />
                <p style="margin:10px 0px;font-size: 10px;">{{ i.content }}</p>
                <el-link v-for="k in i.address" target="_blank" :key="k.address" :href="k.address" :type="k.type">
                  {{ k.name }}
                </el-link>
                <br>
                <el-tag v-for="j in i.tabs" :key="j.name" effect="dark" :type="j.tabs">
                  {{ j.name }}
                </el-tag>
              </el-collapse-item>
            </el-collapse>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-col>
  </el-row>

  <el-dialog
    v-model="dialogVisible"
    fullscreen="true"
    :show-close="false"
  >
    <targetComponent :data="tem" :type="type"></targetComponent>
    <el-button @click="dialogVisible = false">关闭</el-button>
  </el-dialog>


</template>


<script setup>
import { onMounted, ref } from 'vue';
import $ from 'jquery'
import targetComponent from '@/components/TargetComponent.vue'
import config from '@/utiles/config';

  const dialogVisible = ref(false)
  const data = ref([])

  const tem = ref({})

  const bufen = ref({
      id:1,
      target:"",
      content:"",
      icon:"",
      color:"#409eff",
      createTime:"",
      images:[],
      address:[],
      tabs:[]
  })
  const type = ref("save")

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

  const addddddd = ()=>{
    dialogVisible.value = true
    type.value = "add" 
    tem.value = bufen.value
  }

  const edit = (i)=>{
    type.value = "edit"
    dialogVisible.value = true
    tem.value = i
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