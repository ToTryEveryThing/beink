<template>
  <el-row >
    <el-col :span="18" :offset="3">
  <el-dialog
    v-model="dialogVisible"
    title="添加"
    width="30%"
    :before-close="handleClose"
  >
  <el-input v-model="title" placeholder="Please input">
    <template #prepend>Title :</template>
  </el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addTab(editableTabsValue)">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-tabs 
    v-model="editableTabsValue"
    @tab-change="changeTab"
    class="demo-tabs"
  >
  <el-tab-pane > 
    <template #label>
      <span  @click="goBack">
        <ArrowLeft style="height:20px;width:20px;     margin-bottom: -5px;" />
        <HomeFilled style=" margin-bottom: -5px;margin-left:5px;"/>
      </span>
    </template>
  </el-tab-pane>
    <el-tab-pane
      v-for="item in editableTabs"
      :key="item.name"
      :label="item.title"
      :name="item.name"
    >
    <el-row justify="space-evenly" v-if="item.show">
      <el-col  :span="1" v-if="$store.state.account==='admin'">
        <el-button  type="primary"  @click="item.show=false" plain>编辑</el-button>
        <el-button type="success" style="margin-top:10px;" class="fsafs" @click="dialogVisible = true" plain>添加</el-button>
        <el-button  type="danger" style="margin-top:10px;" class="fsafs" @click="removeTab(item.name)" plain>删除</el-button>
        <el-affix class="fsafs" :offset="100">
          <a style="display:none" href="javascript:window.scrollTo(0,0)" class="fas">
            <span></span>
          </a>
        </el-affix>
      </el-col>
      <el-col  :span="1" v-else>
        <el-affix class="fsafs" :offset="100">
          <a style="display:none" href="javascript:window.scrollTo(0,0)" class="fas">
            <span></span>
          </a>
        </el-affix>
      </el-col>
      <el-col :span="16" >
          <el-card>
              <el-col :span="24">
                  <v-md-preview  :text="item.content"></v-md-preview >
              </el-col>
          </el-card>
      </el-col>
      <el-col :gutter="20" :span="3">
          <el-col :span="12">
            <el-image style="width: 100px; height: 100px" src="https://cdn.acwing.com/media/user/profile/photo/71127_lg_5c719f083a.png" :fit="fit" /> 
          </el-col>
          <el-col :span="12">
            <el-tag>米叔</el-tag>
          </el-col>
      </el-col>
  </el-row>
    <v-md-editor v-if="!item.show" v-model="item.content" 
    height="850px"
    left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save back" 
    right-toolbar="preview toc sync-scroll fullscreen"
    :toolbar="vue.toolbar"
    @save="item.show=true,save()" ></v-md-editor>
    </el-tab-pane>
  </el-tabs>
</el-col>
</el-row>
</template>

<script>
import {onMounted, ref,reactive} from 'vue'
import {useStore } from 'vuex'
import router from '../router/index'
import $ from 'jquery'
    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 300) {
                $(".fas").fadeIn(50);
            }
            else {
                $(".fas").fadeOut(50);
            }
        });
    });
  export default{
    mounted(){
      this.$bus.on('send',data=>{
          console.log(data)
          localStorage.setItem("activeName",data)
      })
    },
    setup(){
      const vue = reactive({
          toolbar: {
          back: {
              title: '取消编辑',
              icon: 'v-md-icon-undo',
              action() {
                const tabs = editableTabs.value
                tabs.forEach((i)=>{
                  if(i.name===editableTabsValue.value)
                  i.show = true
                })
              },
          },
          },
      })
      let tabIndex = 0
      let store = useStore()
      let title = ref('')
      const editableTabsValue = ref()
      const editableTabs = ref([])
      const dialogVisible = ref(false)
      const addTab = () => {
          const newTabName = ++tabIndex
          editableTabs.value.push({
              title: title.value,
              name: newTabName,
              content: `# New Tab content about ${title.value}`,
              show:true
          })
          editableTabsValue.value = newTabName
          dialogVisible.value  = false
          title.value = ''
          save()
      }
    const removeTab = (targetName) => {
        let f = confirm("确定吗")
        if(!f)return 
        const tabs = editableTabs.value
        editableTabsValue.value = targetName - 1 || 1
        editableTabs.value = tabs.filter((tab) => tab.name !== targetName)
        const Tabs = editableTabs.value
        Tabs.forEach((i,index)=>{
          i.name = index+1
        })
        save()
    }
    const goBack = ()=>{
        router.push({name:'main'})
    }
    const save = ()=>{
      $.ajax({
          url:"https://so.beink.cn/user/admin/git/save/",
          data:{
              markdown:JSON.stringify(editableTabs.value)
          },
          headers:{
                Authorization:"Bearer " + store.state.token
          },
          type:'post',
          success(){
              tabIndex = editableTabs.value.length
          },
          error(res){
              console.log(res)
          }
      })
    }
    const aaa = ()=>{
      $.ajax({
            url:"https://so.beink.cn/user/admin/git/show/",
            type:'post',
            success(res){
              editableTabs.value = JSON.parse(res)
              tabIndex = editableTabs.value.length
            },
        })
    }
    onMounted(()=>{
      $('html').css({'--backColor':'#f7f7f7f'})  
      document.getElementsByTagName('body')[0].style.backgroundImage 
            = `url("")`   
      editableTabsValue.value = Number(localStorage.getItem("activeName"))   || 1
      aaa()
      const jwt = localStorage.getItem("jwt");
        if(jwt){
            store.commit("updateToken",jwt)
            store.dispatch("getinfo",{
                success(){
                    aaa()
                },
                error(){
                }
            })
        }
    })
   const changeTab = ()=>{
    localStorage.setItem("activeName",editableTabsValue.value)
   }
    return{
      title,vue,
        addTab,removeTab,
        editableTabsValue,
        editableTabs,save,
        goBack,dialogVisible,changeTab
      }
  }
}
</script>

<style scoped>
.fas{
  position: absolute;
  margin-left: 7px;
  background-color: #ffffff;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  color: #409eff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, .12);
  cursor: pointer;
}
.fas span{
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 10px solid #409eff;
}
.fsafs{
  margin: 0px;
}
@media only screen and (max-width: 500px) {

  
}
</style>