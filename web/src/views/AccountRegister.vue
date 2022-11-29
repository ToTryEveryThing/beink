<template>
  
  <el-form style="padding-top: 15vh;" autocomplete="off" label-width="80px"  :label-position="labelPosition">
    <h1>Register</h1>
      <el-form-item label="Name" >
        <el-input autocomplete="off" autofocus="autofocus" maxlength="10" show-word-limit   v-model="account" />
      </el-form-item>
      <el-form-item label="Password">
          <el-input  autocomplete="off" @keyup.enter="register"  show-password type="password" v-model="password" />
      </el-form-item>
      {{message}}
        <el-button @click="register" type="primary">注册</el-button>
        <div class="goto">
          <router-link to="/">主页</router-link>
          <router-link to="/login">去登录</router-link>
        </div>
  </el-form>
</template>

<script>
import {ref} from 'vue'
import { reactive, toRefs } from '@vue/reactivity'
import { ElMessage } from 'element-plus'
import $ from 'jquery'
import router from '../router/index'
export default {
  setup(){
    const vue = reactive({
      drawer:true,
      visible :true,
      labelPosition :'right'
    })
    const open2 = () => {
      ElMessage({
        message: '注册成功',
        type: 'success',
      })
    }
    const open3 = value => {
      ElMessage({
        message: value,
        type: 'warning',
      })
    }
    let account = ref('')
    let password = ref('')
    let message = ref('')
    const register = ()=>{
        $.ajax({
            url:"https://so.beink.cn/user/account/register/",
            type:'post',
            data:{
                account:account.value,
                password:password.value
            },
            success(res){
                if(res.message === "success"){
                  open2()
                    router.push({name:'login'})
                }else{
                    open3(res.message)
                }
            },
        })
    }
      return{
        ...toRefs(vue),
        account,
        password,
        message,register,open2
        ,open3
      }
  }
}
</script>

<style scoped>
.goto{
  margin-top: 10px;
  display: flex;
}
.goto a:first-child{
  margin-left: 200px;
}
.goto a{
  font-size: 15px;
  display: block;
  height: 32px;
  width: 150px;
  color:#a29bfe;
}
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

