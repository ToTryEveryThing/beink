<template>
  <DiscussView/>
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
    tab-position="top"
    v-model="editableTabsValue"
    @tab-change="changeTab"
    class="demo-tabs"
  >
    <el-tab-pane
      v-for="item in editableTabs"
      :key="item.name"
      :label="item.title"
      :name="item.name"
    >
    <el-row  justify="space-evenly" v-if="item.show">
      <el-col :span="1" v-if="$store.state.is_author" class="hidden-xs-only">
        <el-affix :offset="180">
          <el-button   circle  type="primary" style="margin-left:10px;margin-top:10px;" @click="item.show=false" ><el-icon ><Edit /></el-icon></el-button>
          <el-button  circle  type="success" style="margin-top:10px;"   @click="dialogVisible = true" ><el-icon><Plus /></el-icon></el-button>
          <el-button   circle  type="danger" style="margin-top:10px;"  @click="removeTab(item.name)" ><el-icon><Delete /></el-icon></el-button>
          <el-button   circle  type="warning" style="margin-top:10px;" @click="GoHome" ><el-icon><House /></el-icon></el-button>
        </el-affix>
      </el-col>
      <el-col  :span="1" v-else class="hidden-xs-only">
       
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
            <el-col :span="12">
              <el-image style="width: 100px; height: 100px" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" :fit="fit" /> 
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
        </el-affix>
      </el-col>
  </el-row>
    <v-md-editor  v-if="!item.show" v-model="item.content" 
    height="850px"
    left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save back" 
    right-toolbar="preview toc sync-scroll fullscreen"
    :toolbar="vue.toolbar"
    @save="item.show=true,save()" 
    :disabled-menus="[]"
    @upload-image="handleUploadImage"
    >
  </v-md-editor>
    </el-tab-pane>
  </el-tabs>
</el-col>
<div id="hdfhsdfh"></div>
<el-button v-if="$store.state.is_author&&editableTabs.length===0&&sssss" 
 type="danger"
 @click="dialogVisible = true"
 >添加</el-button>
</el-row>
<div class="bottom hidden-xs-only">
  <el-button type="info"  plain @click="Todark"   style="margin-top:10px;background-color:grey;">
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
</div>
</template>

<script>
import { nanoid } from 'nanoid'
import {onMounted, ref,reactive} from 'vue'
import { useStore } from 'vuex'
import { warning } from '@/utiles/message';
import { useRoute } from 'vue-router';
import router from '../../router/index'
import dark from '../../utiles/dark'
import config from '../../utiles/config'
import $ from 'jquery'
import DiscussView from './DiscussView.vue';
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
  components: { DiscussView },
    setup(){
      const vue = reactive({
          asd : '#hdfhsdfh',
          titles:[],
          sssss:false,
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
      let tabIndex = 1
      let store = useStore()
      let title = ref('')
      let AllTitle = ref([])
      let textName = ref('')
      let AUTHOR = ref("")
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
          // getTitle()
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
        // getTitle()
        save()
    }
    const handleUploadImage = (event, insertImage, files) =>{
      console.log(nanoid())
      if(store.state.role==="use"){
        warning("没有权限")
        return 
      }
      let e = files[0]
      let id = nanoid()
      let nname = e.name
      var newFile = new File([e] ,id + nname.substr(nname.indexOf("."))  , { type: e.type });
      // console.log(newFile)
      // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
            var formData = new FormData(); 
		        formData.append('file', newFile); 
            formData.append("keyPrefix","study")
            store.dispatch("upload",{
                token:store.state.token,
                formData:formData,
                success(){
                  insertImage({
                    url: 'https://images.beink.cn/'+"study/" + id + nname.substr(nname.indexOf(".")),
                    desc: 'study',
                    // width: 'auto',
                    // height: 'auto',
                  });
                }
            })
    }
    const getTitle=()=>{
      AllTitle.value=[]
      let j = 0
      // 只要三个
      editableTabs.value.forEach((i)=>{
          if(j>=3)return
          AllTitle.value.unshift(i.title)
          j++
        })
    }
    const save = ()=>{
      getTitle()
      $.ajax({
          url:`${config.API_URL}/user/admin/git/save/`,
          data:{
              markdown:JSON.stringify(editableTabs.value),
              title:JSON.stringify(AllTitle.value)
          },
          headers:{
                Authorization:"Bearer " + store.state.token
          },
          type:'post',
          success(res){
            if(res.code===1){
              tabIndex = editableTabs.value.length
            }else{
              console.log(res)
            }
            
          },
          error(res){
              console.log(res)
          }
      })
    }
    const aaa = ()=>{
      $.ajax({
            url:`${config.API_URL}/user/admin/git/show/`,
            type:'post',
            data:{
              name:textName.value
            },
            success(res){
              if(res.code===1){
                res = res.date
                if(res.git==="")  
                  editableTabs.value = []
                else
                editableTabs.value = JSON.parse(res.git)
                store.commit("textAuthor",res.name)
                tabIndex = editableTabs.value.length
                AUTHOR.value = res.name
                store.commit("updateDiscuss",{index:1,title:editableTabs.value[0].title,name:AUTHOR.value})
                // store.commit("showDiscuss",{page:1})
                
                let article_id = parseInt(localStorage.getItem("article_id"))
                if(editableTabs.value.length < article_id)
                  article_id = 1
                editableTabsValue.value = article_id
                changeTab(article_id)
              }else{
                router.push("/")
              }
            },
        })
        vue.sssss = true
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
      const route = useRoute()
      textName.value = route.params.name
      $('html').css({'--backColor':'#f7f7f7f'})  
      document.getElementsByTagName('body')[0].style.backgroundImage 
            = `url("")`   
      editableTabsValue.value = 1
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
    const GoHome=()=>{
      router.push("/")
    }
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
      if(editableTabs.value==false || preview.value== null)return
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
   const changeTab = (i)=>{
    if(editableTabs.value != false){
      localStorage.setItem("article_id",i)
      store.commit("updateDiscuss",{
      index:i,
      title:editableTabs.value[i-1].title,
      name:AUTHOR.value.toString()
    })
    // store.commit("showDiscuss",{page:1})
      changeTitle()
    }
   
   }
    return{handleAnchorClick,Todark,handleUploadImage,
      title,vue,preview,
        addTab,removeTab,
        editableTabsValue,
        editableTabs,save,
        dialogVisible,changeTab,GoHome
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
  margin: 10px;
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
.bottom{
  position: fixed;
  right: 0;
  bottom: 100px;
}
</style>