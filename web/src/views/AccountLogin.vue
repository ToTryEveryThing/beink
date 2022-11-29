<template>
    <el-form style="padding-top: 15vh;" autocomplete="off" label-width="80px"  :label-position="labelPosition">
      <h1>Login</h1>
        <el-form-item label="Name" >
          <el-input autocomplete="off" autofocus="autofocus" maxlength="10" show-word-limit   v-model="account" />
        </el-form-item>
        <el-form-item label="Password"> 
            <el-input  autocomplete="off"  @keyup.enter="login" show-password type="password" v-model="password" />
        </el-form-item> 
        {{message}}
          <el-button @click="login"   type="primary">确定</el-button>
          <div class="goto">
            <router-link to="/">主页</router-link>
            <router-link to="/register">去注册</router-link>
          </div>
    </el-form>
</template>

<script>

import {useStore} from 'vuex'
import {ref} from 'vue'
import { reactive, toRefs } from '@vue/reactivity'
import router from '../router/index'
import { ElMessage } from 'element-plus'
export default {
  setup(){
    const vue = reactive({
      drawer:true,
      color:'rgba(19, 206, 102, 0.8)',
      visible :true,
      labelPosition :'right'
    })
    const open2 = () => {
      ElMessage({
        message: '登录成功',
        type: 'success',
      })
    }
    
    const open4 = () => {
      ElMessage.error('账号或密码错误')
    }
    const store = useStore();
            let account = ref('')
            let password = ref('')
            let message = ref('')
            const login = function(){
                store.dispatch("login",{
                    account:account.value,
                    password:password.value,
                    success(){
                        store.dispatch("getinfo",{
                            success(){
                              open2()
                              sessionStorage.setItem("name",store.state.account)
                              router.push({name:'main'})
                            },
                        })
                    },
                    error(){
                      open4()
                    }
                })
            }
      return{
        ...toRefs(vue),
        account,
        password,
        message,
        login,open2,open4
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

