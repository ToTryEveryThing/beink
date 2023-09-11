<template>
    <el-form :model="data" label-width="120px" >
        <el-form-item label="目标">
          <el-input v-model="data.target" />
        </el-form-item>
        <el-form-item label="简介">
            <el-input v-model="data.content" />
        </el-form-item>
        <el-form-item label="创建时间">
            <el-date-picker
                v-model="data.createTime"
                type="date"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                placeholder="Pick a day"
                :size="size"
            />
        </el-form-item>
        <el-form-item label="颜色">
            <el-color-picker v-model="data.color" show-alpha />
        </el-form-item>
        <el-form-item label="图标">
            <el-input v-model="data.icon"></el-input>
        </el-form-item>
        <!-- 图片地址 -->
        <el-form-item label="相关图片">
           <el-input style="margin-bottom:5px;" v-for="i in data.images.length" :key="i" v-model="data.images[i-1]">
            <template #prepend>
                <el-button @click="deleteImages(i-1)" size="small"><el-icon><CloseBold /></el-icon></el-button>
            </template>
            </el-input>
           <el-button @click="addImages" size="small" type="primary"><el-icon><Plus /></el-icon></el-button>
        </el-form-item>
        
        <!-- 各种链接 -->
        <el-form-item label="相关地址">
            <el-row  style="margin-bottom:5px;">
                <el-col  v-for="k in data.address" :key="k">
                    <el-row>
                        <el-button @click="deleteAddress(k)"><el-icon><CloseBold /></el-icon></el-button>
                        <el-col :span="2">
                            <el-input v-model="k.name"></el-input>
                        </el-col>
                        <el-col :span="4">
                            <el-input v-model="k.address"></el-input>
                        </el-col>
                        <el-col :span="2">
                            <el-select v-model="k.type" class="m-2" placeholder="Select" >
                                <el-option
                                  v-for="item in options"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                        </el-col>
                    </el-row>
                </el-col>
                <el-button @click="addAddress" size="small" type="primary"><el-icon><Plus /></el-icon></el-button>
            </el-row>
        </el-form-item>

        <!-- 各种标签 -->
        <el-form-item label="标签">
            <el-row  style="margin-bottom:5px;">
                <el-col  v-for="k in data.tabs" :key="k">
                    <el-row>
                        <el-button @click="deleteTabs(k)"><el-icon><CloseBold /></el-icon></el-button>
                        <el-col :span="8">
                            <el-input v-model="k.name"></el-input>
                        </el-col>
                        <el-col :span="8">
                            <el-select v-model="k.tabs" class="m-2" placeholder="Select" >
                                <el-option
                                  v-for="item in options"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                        </el-col>
                    </el-row>
                </el-col>
                <el-button @click="addTabs" size="small" type="primary"><el-icon><Plus /></el-icon></el-button>
            </el-row>
        </el-form-item>
    </el-form>
    <el-timeline>
        <el-timeline-item  size="large" :color="data.color"  :key="data.target" :icon="data.icon" :timestamp="data.createTime" placement="top">
            <el-card class="father" body-style="padding-top:0px;" >
            <h2 style="margin-bottom:2px">{{ data.target }}</h2>
            <el-image v-for="l in data.images" :key="l" style="width: 100px; height: 100px; margin-right: 10px;" :src="l"  />
            <p style="margin:10px 0px;font-size: 10px;">{{ data.content }}</p>
            <el-link v-for="k in data.address" target="_blank" :key="k.address" :href="k.address" :type="k.type">
                {{ k.name }}
            </el-link>
            <br>
            <el-tag v-for="j in data.tabs"  :key="j.name" effect="dark" :type="j.tabs">
                {{ j.name }}
            </el-tag>
            </el-card>
        </el-timeline-item>
    </el-timeline>
    <el-button @click="save">save</el-button>
</template>

<script setup>
import { defineProps, toRefs } from 'vue'
import $ from 'jquery'
import { success } from '@/utiles/message';
import config from '@/utiles/config';

    const props = defineProps(['type','data']);
    const{ data, type} = toRefs(props)

  const options = [
    {
        value: 'dafault',
        label: 'dafault',
    },
    {
        value: 'primary',
        label: 'primary',
    },
    {
        value: 'success',
        label: 'success',
    },
    {
        value: 'info',
        label: 'info',
    },
    {
        value: 'danger',
        label: 'danger',
    },
    {
        value: 'warning',
        label: 'warning',
    },
 ]

  const addImages = ()=>{
    data.value.images.push("")
  }

  const deleteImages = (i)=>{
    console.log(type.value);
    data.value.images.splice(i,1)
  }

  const deleteAddress = i =>{
    data.value.address.splice(data.value.address.indexOf(i),1)
  }

  const addAddress = ()=>{
    data.value.address.push({
          address:"",
          name:"",
          type:""
    })
  }

  const addTabs = ()=>{
    data.value.tabs.push({
        name:"",
        type:""
    })
  }

  const deleteTabs = i =>{
    data.value.tabs.splice(data.value.tabs.indexOf(i),1)
  }


  const addddd = ()=>{
    $.ajax({
        url:`${config.API_URL}/admin/target/add/`,
        type:'post',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        headers:{
          Authorization:"Bearer " + localStorage.getItem("jwt")
        },
        data:JSON.stringify(data.value),
        success(res){
          if(res.code === 200) 
          success("添加成功")
        } 
    })
  }

  const editttttt = ()=>{
    $.ajax({
        url:`${config.API_URL}/admin/target/update/`,
        type:'post',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        headers:{
          Authorization:"Bearer " + localStorage.getItem("jwt")
        },
        data:JSON.stringify(data.value),
        success(res){
          if(res.code === 200) 
          success("修改成功")
        } 
    })
  }

  const save = ()=>{

    if(type.value === 'add')
        addddd()
    else
        editttttt()
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