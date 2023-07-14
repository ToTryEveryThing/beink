<template>
    <el-form 
        :model="form" 
        label-width="80px"
        label-position="left">

        <el-form-item label="账户名">
            <el-input disabled style="width:100%;" v-model="form.name" />
        </el-form-item>

        <el-form-item label="旧密码">
            <el-input  style="width:100%;"
             v-model="form.oldpassword"
             placeholder="不填则默认不改"
              type="password" show-password/>
        </el-form-item>

        <el-form-item label="新密码">
            <el-input  style="width:100%;"
             v-model="form.newpassword"
             placeholder="不填则默认不改"
              type="password" show-password/>
        </el-form-item>

        <el-form-item  label="邮箱">
            <el-input disabled style="width:100%;" v-model="form.email" />
        </el-form-item>

        <el-form-item label="注册时间">
            <el-date-picker
                disabled
                v-model="form.date"
                type="datetime"
                placeholder="Select date and time"
            />
        </el-form-item>

        <el-form-item label="头像" >
            <el-avatar size="large" :src="form.image" style="cursor: pointer;" @click="dialogTableVisible = true" />
        </el-form-item>
        <el-form-item label="角色">
            <el-tag>
                {{ form.role }}
            </el-tag>
        </el-form-item>
        <el-button type="danger" @click="save">save</el-button>
    </el-form>

    <el-dialog  width="35%" style=" border-radius: 10px;" :show-close="false"
    v-model="dialogTableVisible" title="选择一个作为头像">

        <el-avatar
         v-for="i in 30" :key="i" size="large" 
         :src="`https://cdn.beink.cn/userimage/vue-color-avatar/${i}.png`"
          style="cursor: pointer; margin:5px;" 
          @click="userimage(i)"
          />
    </el-dialog>
</template>

<script setup>
import $ from 'jquery'
import config from '@/utiles/config'
import { success, warning } from '@/utiles/message'
import { useStore } from 'vuex'
import {onMounted, reactive, ref } from 'vue'

    const store = useStore()
    let dialogTableVisible = ref(false)
    const form = reactive({
        name: '',
        oldpassword: "",
        newpassword: "",
        email: '',
        image: '',
        role: '',
        date: "",
        id: ""
    })

    onMounted(()=>{
        setTimeout(()=>{
            form.email = store.state.email
            form.name = store.state.account
            form.image = store.state.userimage
            form.role = store.state.role
            form.date = store.state.date
            form.id = store.state.id
        },1000)
    })

    const save = ()=>{
        $.ajax({
          url:`${config.API_URL}/user/account/update/`,
          type:'post',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          data:{
            all: JSON.stringify(form)
          },
          success(res){
            if(res.code===200){
                success("修改成功")
            }else{
                warning(res.message)
            }
          }
      })
    }

    const userimage = url =>{
        form.image = `https://images.beink.cn/userimage/vue-color-avatar/${url}.png`
        dialogTableVisible.value = false
    }

</script>

<style scoped>

body,html{
    background-color: #ffffff;
}

</style>