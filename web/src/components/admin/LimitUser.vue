<template>
  <el-card>
        <el-switch
            v-model="canChat"
            class="mt-2"
            style="margin-left: 24px"
            active-text="Chat"
            inactive-text="Chat"
        />
        <el-switch
            v-model="canRegister"
            class="mt-2"
            style="margin-left: 24px"
            active-text="Register"
            inactive-text="Register"
        />
        <br>
        <el-button type="primary" @click="savee">
            保存
        </el-button>
    </el-card>
</template>

<script>
import { success } from '@/utiles/message'
import { onMounted, reactive, toRefs } from 'vue'
import $ from 'jquery'
import config from '@/utiles/config'
import {useStore} from 'vuex'
export default {

    setup(){
        const store = useStore()

        const vue = reactive({
            canChat: true,
            canRegister: true
        })

        const savee = () =>{
            $.ajax({
              url:`${config.API_URL}/admin/limits/update/`,
              type:'post',
              headers:{
                  Authorization:"Bearer " + store.state.token
              },
              data:{
                register: vue.canRegister,
                chat: vue.canChat
              },
              success(res){
                if(res.code==200)success("修改成功")
              }
          })
        }

        const show = ()=>{
            $.ajax({
              url:`${config.API_URL}/admin/limits/show/`,
              type:'post',
              headers:{
                  Authorization:"Bearer " + store.state.token
              },
              success(res){
                console.log(res)
                vue.canChat = res.data.canChat
                vue.canRegister = res.data.canRegister
              }
          })
        }

        onMounted(()=>{
            show()
        })

        return {
            ...toRefs(vue),
            savee
        }
    }
}
</script>

<style>

</style>