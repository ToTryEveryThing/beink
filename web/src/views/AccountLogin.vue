<template>
    <el-form style="margin-top:10vh;" autocomplete="off" label-width="80px"  :label-position="labelPosition">
      <h1>Login</h1>
        <el-form-item label="名字" >
          <el-input autocomplete="off" autofocus="autofocus" maxlength="10" show-word-limit   v-model="account" />
        </el-form-item>
        <el-form-item label="密码"> 
            <el-input  autocomplete="off"  @keyup.enter="login" show-password type="password" v-model="password" />
        </el-form-item> 
        <el-form-item label="验证码"> 
          <el-input  autocomplete="off" style="width:200px"    v-model="code" />
          <img width="100" height="40"  @click="captcha" :src="cha"/>
      </el-form-item>

        {{message}}
        <slot :keyyy="nor"></slot>
          <el-button @click="login"  type="primary">确定</el-button>
    </el-form> 
</template>

<script>

import {useStore} from 'vuex'
import {onMounted, ref} from 'vue'
import { reactive, toRefs } from '@vue/reactivity'
import router from '../router/index'
import { success, error } from '@/utiles/message'
import $ from 'jquery'
export default {
  setup(){
    const vue = reactive({
      drawer:true,
      color:'rgba(19, 206, 102, 0.8)',
      visible :true,
      labelPosition :'right',
      nor:false,
      cha:''
    })
    onMounted(()=>{
      captcha()
    })
    
    const captcha = ()=>{
      $.ajax({
        url:"https://so.beink.cn/captcha/",
        type:'get',
        success(res){
          vue.cha = res
        }
      })
    }

    const store = useStore();
      let account = ref('')
      let password = ref('')
      let message = ref('')
      let code = ref('')
      
      const login = function(){
          store.dispatch("login",{
              account:account.value,
              password:password.value,
              code:code.value,
              success(){
                  store.dispatch("getinfo",{
                      success(){
                        vue.nor = true
                        success("登录成功")
                        sessionStorage.setItem("name",store.state.account)
                        router.push({name:'main'})
                      },
                  })
              },
              error(){
                error("账号或密码错误")
              }
          })
      }
      return{
        captcha,
        ...toRefs(vue),
        account,
        password,
        message,
        code,
        login
      }
  }
}
</script>


<style scoped>
  a:hover{
    color:#6c5ce7;
  }
  .el-col{
    display: flex;
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

