<template>
  <el-row justify="center">
    <el-col :md="18" :xs="24">
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
    <el-row  justify="space-evenly" v-if="item.show">
      <el-col :span="1" v-if="$store.state.account==='admin'">
        <el-affix   :offset="70">
          <el-button  type="primary"  @click="item.show=false" plain>编辑</el-button>
          <el-button type="success" style="margin-top:10px;" class="fsafs" @click="dialogVisible = true" plain>添加</el-button>
          <el-button  type="danger" style="margin-top:10px;" class="fsafs" @click="removeTab(item.name)" plain>删除</el-button>
          <el-button link @click="Todark"  style="margin-top:10px;" class="fsafs">
            <el-icon >
              <Sunny  />
            </el-icon>
            <el-icon >
              <Moon />
            </el-icon>
          </el-button>
          <a  href="javascript:window.scrollTo(0,0)" style="margin-top:10px;" class="fas">
            <span></span>
          </a>
        </el-affix>
      </el-col>
      <el-col  :span="1" v-else>
        <el-affix  :offset="100">
          <el-button @click="Todark"  style="margin-top:10px;" class="fsafs">
            <el-icon >
              <Sunny  />
            </el-icon>
            <el-icon >
              <Moon />
            </el-icon>
          </el-button>
          <a style="margin-top:10px;" href="javascript:window.scrollTo(0,0)" class="fas">
            <span></span>
          </a>
        </el-affix>
      </el-col>
      <el-col :xs="23" :sm="16">
          <el-card>
              <el-col :span="24">
                  <v-md-preview ref="preview" id="preview" class="dark" :text="item.content"></v-md-preview >
              </el-col>
          </el-card>
      </el-col>
      <el-col class="hidden-xs-only" :gutter="20" :span="4">
        <el-affix :offset="0">
          <el-scrollbar height="100vh">
            <el-col :span="12">
              <el-image style="width: 100px; height: 100px" src="https://cdn.acwing.com/media/user/profile/photo/71127_lg_5c719f083a.png" :fit="fit" /> 
            </el-col>
            <el-col   :span="24">
              <div
              style="position:relative;"
              v-for="anchor in vue.titles" :key="anchor.title"
              :style="{ padding: `0px 0 10px ${anchor.indent * 20}px` ,color:'#3eaf7c'}"
              @click="handleAnchorClick(anchor)"
            >
              <a style="cursor: pointer" :id="anchor.id"  class="goto">{{ anchor.title }}</a>
            </div>
            </el-col>
          </el-scrollbar>
         
        </el-affix>
      </el-col>
  </el-row>
    <v-md-editor  v-if="!item.show" v-model="item.content" 
    height="850px"
    left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save back" 
    right-toolbar="preview toc sync-scroll fullscreen"
    :toolbar="vue.toolbar"
    @save="item.show=true,save()" 
    >
  </v-md-editor>
    </el-tab-pane>
  </el-tabs>
</el-col>
<div id="hdfhsdfh"></div>
</el-row>

</template>

<script>
import { nanoid } from 'nanoid'
import {onMounted, ref,reactive} from 'vue'
import {useStore } from 'vuex'
import router from '../router/index'
import dark from '../utiles/dark'
import $ from 'jquery'
    // $(function () {
    //     $(window).scroll(function () {
    //         if ($(window).scrollTop() > 300) {
    //             $(".fas").fadeIn(50);
    //         }
    //         else {
    //             $(".fas").fadeOut(50);
    //         }
    //     });
    // });
  export default{
    setup(){
      const vue = reactive({
          asd : '#hdfhsdfh',
          titles:[],
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
      let preview = ref(null)
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
              markdown:JSON.stringify(editableTabs.value),
              account:store.state.account
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
              setTimeout(function(){changeTitle()},1000)
            },
        })
    }
    const Todark = ()=>{
      let Class = localStorage.getItem("theme") || "";
      if(Class==="dark"){
          dark()
          localStorage.setItem("theme","sun")
      }else{
          dark("dark")
          localStorage.setItem("theme","dark")
      }
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
    const handleAnchorClick = (anchor)=> {
      $(`${vue.asd}`).removeClass("goto-active")
      $(`#pane-${editableTabsValue.value} #${anchor.id}`).addClass("goto-active")
      vue.asd = `#pane-${editableTabsValue.value} #${anchor.id}`
      const { lineIndex } = anchor;
      const heading = preview.value[editableTabsValue.value-1].$el.querySelector(`[data-v-md-line="${lineIndex}"]`);
      if (heading) {
        preview.value[editableTabsValue.value-1].scrollToTarget({
          target: heading,
          scrollContainer: window,
          top: 60,
        });
      }
    }
    const changeTitle =()=>{
      const anchors = preview.value[editableTabsValue.value-1].$el.querySelectorAll('h2,h3')
      const titles = Array.from(anchors).filter((title) => !!title.innerText.trim());
      if (!titles.length) {
        vue.titles = [];
        return;
      }
      const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort();
      vue.titles = titles.map((el) => ({
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
        id:nanoid()
      }));
    }
   const changeTab = ()=>{
      localStorage.setItem("activeName",editableTabsValue.value)
      changeTitle()
   }
    return{handleAnchorClick,Todark,
      title,vue,preview,
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
.goto{
  border: 3px solid #40a0ff00;
  padding-left: 5px;
  transition: 0.3s;
}
.goto:hover{
  margin-left: 10px;
  box-sizing: border-box;
  color:   #0984e3;
  border-left: #0984e3 solid 3px;
}
.goto-active{
  border: 3px solid #40a0ff00;
  padding-left: 5px;
  box-sizing: border-box;
  color:   var(--el-color-primary);
  border-left: #0984e3 solid 3px;
}
</style>