<template>
    <v-md-editor v-model="markdown"  
        left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save back" 
        right-toolbar="preview toc sync-scroll fullscreen"
        :toolbar="vue.toolbar"
        v-if="isShow" @save="save" height="800px">
    </v-md-editor>
  <el-row justify="center" v-if="!isShow">
      <el-col :span="1" >
          <el-affix :offset="150">
              <el-button  type="primary" v-if="$store.state.account==='admin'" @click="edit">修改</el-button>
              <a href="javascript:window.scrollTo(0,0)" class="fas">
                <span></span>
              </a>
            </el-affix>
      </el-col>
      <el-col :span="16" >
          <el-card shadow="never">
              <el-col :span="24">
                  <v-md-preview  :text="markdown"></v-md-preview >
              </el-col>
          </el-card>
      </el-col>
  </el-row>

</template>

<style scoped>
.fas{
    margin-top: 15px;
    margin-left: 7px;
    background-color: #ffffff;
    width: 40px;
    position: fixed;
    height: 40px;
    border-radius: 50%;
    color: #409eff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    box-shadow: 0px 0px 6px rgba(0, 0, 0, .12);
    cursor: pointer;
    z-index: 99999;
}
.fas span{
    width: 0;
    height: 0;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
    border-bottom: 10px solid #409eff;
}

</style>

<script>


import {onMounted, ref,reactive} from 'vue'
import $ from 'jquery'
import {useStore} from 'vuex'
import router from '../../router/index'
export default{
  setup(){
    const vue = reactive({
            toolbar: {
            back: {
                title: '返回',
                icon: 'v-md-icon-undo',
                action() {
                    isShow.value = false
                },
            },
            },
        })
      const store = useStore();
      const markdown = ref("")
      const isShow = ref(false)
      const otherLeft = ref("")
      const otherRight = ref("")
      const save = ()=>{
          $.ajax({
              url:"https://so.beink.cn/user/admin/git/save/",
              data:{
                  markdown:otherLeft.value + "我是diyi" + markdown.value + "我是dier" + otherRight.value
              },
              headers:{
                   Authorization:"Bearer " + store.state.token
              },
              type:'post',
              success(res){
                  console.log(res)
              },
              error(res){
                  console.log(res)
              }
          })
          isShow.value = false
      }
      let aaa=()=>{
          $.ajax({
              url:"https://so.beink.cn/user/admin/git/show/",
              type:'post',
              success(res){
                  otherLeft.value = res.split("我是diyi")[0]
                  markdown.value = res.split("我是diyi")[1].split("我是dier")[0]
                  otherRight.value = res.split("我是diyi")[1].split("我是dier")[1]
              },
          })
      }
      onMounted(()=>{
          aaa()
          const jwt = localStorage.getItem("jwt");
              if(jwt){
                  store.commit("updateToken",jwt)
                  store.dispatch("getinfo",{
                      success(){
                          aaa()
                      },
                      error(){
                          router.push({name:'main'})
                      }
                  })
              }
      })
      const edit =()=>{
          isShow.value = true
      }
      return{
          markdown,save
          ,isShow,edit,
          vue
      }
  }
}

</script>  