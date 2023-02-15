<template>
  
  <el-form  style="margin-top:10vh;" autocomplete="off" label-width="80px"  :label-position="labelPosition">
    <h1>Register</h1>
      <el-form-item label="Name" >
        <el-input autocomplete="off" autofocus="autofocus" maxlength="10" show-word-limit   v-model="account" />
      </el-form-item>
      <el-form-item label="Password">
          <el-input  autocomplete="off"   show-password type="password" v-model="password" />
      </el-form-item>
      <el-form-item label="验证码"> 
        <el-input  autocomplete="off" @keyup.enter="register" style="width:200px"  placeholder="注意大小写"  v-model="code" />
        <img width="100" height="40"  @click="captcha" :src="cha"/>
    </el-form-item>
      {{message}}
      <slot :keyyy="nor"></slot>
        <el-button @click="register" type="primary">注册</el-button>
  </el-form>
</template>

<script>
import {ref, onMounted} from 'vue'
import { reactive, toRefs } from '@vue/reactivity'
import {error, success} from '../utiles/message'
import $ from 'jquery'
export default {
  setup(){
    const vue = reactive({
      drawer:true,
      visible :true,
      labelPosition :'right',
      nor:false,
      cha:'',
    })
    let account = ref('')
    let password = ref('')
    let message = ref('')
    let code = ref('')
    onMounted(()=>{
      captcha()
    })
    
    const captcha = ()=>{
      $.ajax({
        url:"https://so.beink.cn/captcha/",
        type:'post',
        success(res){
          vue.cha = res
        }
      })
    }
    const register = ()=>{
        $.ajax({
            url:"https://so.beink.cn/user/account/register/",
            type:'post',
            data:{
                account:account.value,
                password:password.value,
                code:code.value,
                base64:vue.cha
            },
            success(res){
                if(res.msg === "success"){
                  success("注册成功")
                  setTimeout(()=>{
                    vue.nor = true
                  },1500)
                    
                }else if(res==="error"){
                  error("验证码错误")
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
        account,
        password,
        message,register
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
  margin-left: 64px;
  margin-bottom: 20px;
}
.el-button{
  margin-left: 80px;
  width: 300px;
}
</style>

