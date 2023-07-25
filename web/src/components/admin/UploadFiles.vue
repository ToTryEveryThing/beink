<template>
    <el-upload
      class="upload-demo"
      drag
      action="https://beink.oss-cn-beijing.aliyuncs.com"
      :data="DATA"
      :before-upload="beforeUpload"
    >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">
      Drop file here or <em>click to upload</em>
    </div>
    </el-upload>
  </template>
  
<script  setup>
import config from "@/utiles/config";
import { onMounted, reactive } from "vue";
import { useStore } from "vuex";
import $ from 'jquery'


const store = useStore()

  let DATA = reactive({
      OSSAccessKeyId: '',
      policy: '',
      Signature: '',
      key: '',
      success_action_status : '200',
      host: '',
      dir: ''
  })

  onMounted(()=>{
    $.ajax({
          url:`${config.API_URL}/admin/oss/policy/`,
          type:'post',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          success(res){
            if(res.code === 200){
              DATA.OSSAccessKeyId = res.data.OSSAccessKeyId
              DATA.policy = res.data.policy
              DATA.Signature = res.data.Signature
              DATA.dir = res.data.dir

              DATA.host = res.data.host
              console.log(DATA)
            }
          }
      })
  })

  const beforeUpload = (file)=>{
    DATA.key = DATA.dir  + file.name
  }



</script>
  