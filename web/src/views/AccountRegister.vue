<template>
  
  <el-form  style="margin-top:-40px;" v-if="show" autocomplete="off" label-width="60px"  :label-position="labelPosition">
    <h1>Register</h1>
      <el-form-item label="用户名" >
        <el-input autocomplete="off" autofocus="autofocus" maxlength="20" show-word-limit   v-model="account" />
      </el-form-item>
      <el-form-item label="密码">
          <el-input  autocomplete="off"   show-password type="password" v-model="password" />
      </el-form-item>
      <el-form-item label="验证码"> 
        <el-input  autocomplete="off" @keyup.enter="register"   @focus.once="captcha" style="width:200px"  placeholder="注意大小写"  v-model="code" />
        <img width="100" height="40" alt="点击获得" @click="captcha" :src="cha"/>
    </el-form-item>
      {{message}}
      <slot :keyyy="nor"></slot>
        <el-button @click="register" type="primary">注册</el-button>
  </el-form>

  <el-form  style="margin-top:-40px;" v-else autocomplete="off" label-width="80px"  :label-position="labelPosition">
    <h1>绑个邮箱吧</h1>
    <!-- <el-tag>跳过</el-tag> -->
      <el-form-item label="邮箱" >
        <el-input autocomplete="off"  v-model="mail" >
        </el-input>
      </el-form-item>
      <el-form-item label="代码" >
        <el-input autocomplete="off"  v-model.number="Code" >
        </el-input>
      </el-form-item>
      <slot :keyyy="nor"></slot>
      <!-- <el-form-item > -->
        <el-row>
          <el-col :span="8">
            <el-tag @click="pass">跳过</el-tag>
          </el-col>
          <el-col :span="8">
            <el-button  style="width:100px;"  @click="goBind" type="primary">绑定</el-button>
          </el-col>
          <el-col :span="8">

            <el-button class="btnnn" v-if="click_if"  @click="gomail" small style="width:100px;">{{messages}}</el-button>
            <el-button class="btnnn"  v-else @click="gomail" disabled  style="width:100px;">{{ time }}</el-button>
          </el-col>
        </el-row>


      <!-- </el-form-item> -->
      
  </el-form>

</template>

<script>
import {ref, onMounted} from 'vue'
import { reactive, toRefs } from '@vue/reactivity'
import {error, success} from '../utiles/message'
import config from '@/utiles/config'
import $ from 'jquery'
export default {
  setup(){
    const vue = reactive({
      drawer:true,
      visible :true,
      labelPosition :'right',
      nor:false,
      show:true,
      cha:'',
      mail:'',
      Code:'',
      messages:'获取代码',
      click_if:true,
      time:300
    })
    let account = ref('')
    let password = ref('')
    let message = ref('')
    let code = ref('')
    onMounted(()=>{
      // captcha()
    })
    const captcha = ()=>{
      $.ajax({
        url:`${config.API_URL}/captcha/`,
        type:'post',
        success(res){
          vue.cha = res.data
        }
      })
    }
    var f
    let jwt = ref("")
    const pass  =()=>{
      location.reload();
      // vue.nor = true
    }
    const hh=()=>{
      vue.click_if = false
      vue.time = 10
      f = setInterval(() => {
        if (vue.time > 0) {
          vue.time--
        } else {
          clearInterval()
          vue.click_if = true
        }
      }, 1000)
    }
    const gomail = ()=>{
      if(vue.mail==="")return 
      hh()
      $.ajax({
            url:`${config.API_URL}/user/mail/`,
            type:'post',
            headers:{
              Authorization:"Bearer " + jwt.value
            },
            data:{
              to:vue.mail
            },
            success(res){
               if(res.code===200)
                success("发送成功")
              else 
                error(res.message)
            },
            error(){
              error("error")
            }
        })
    }
    const goBind=()=>{
      if(vue.mail===""||vue.Code==="")return 
      $.ajax({
            url:`${config.API_URL}/user/mailbind/`,
            type:'post',
            headers:{
              Authorization:"Bearer " + jwt.value
            },
            data:{
              mail:vue.mail,
              code:vue.Code
            },
            success(res){
              if(res.code===0){
                error(res.message)
              }else{
                success("绑定成功")
                clearInterval(f)
                location.reload();
              }
            },
            error(){
              error("error")
            }
        })
    }
    const register = ()=>{
        $.ajax({
            url:`${config.API_URL}/user/account/register/`,
            type:'post',
            data:{
                account:account.value,
                password:password.value,
                code:code.value,
                base64:vue.cha
            },
            success(res){
                if(res.code === 200){
                  success(res.message)
                  vue.show = false
                  localStorage.setItem("jwt",res.data)
                  jwt.value = res.data
                }
                else{
                    error(res.msg)
                }
            },
        })
    }
      return{
        code,
        captcha,
        ...toRefs(vue),
        account,pass,
        password,goBind,
        message,register,gomail
      }
  }
}
</script>

<style scoped>
a:hover{
  color:#6c5ce7;
}
.el-dialog{
  width: 500px;
}
.my-header {    
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.el-form-item .el-input {
    width: 300px;
}
.el-form{
  display: flex;
  flex-direction: column;
  align-items: center;
}
.el-form h1{
  margin-left: 50px;
  margin-bottom: 20px;
}
.el-button{
  margin-left: 65px;
  width: 300px;
}
</style>

