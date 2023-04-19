<template>
  <DiscussView/>
  <el-row justify="center" style="margin-top:20px;">
    <el-col :md="18" :xs="24">
    <el-row  justify="space-evenly">
      <el-col :span="1" class="hidden-xs-only">
        <el-affix :offset="180">
          <el-badge :value="$store.state.discuss.all_up" :max="9999" class="item">
            <!-- {{ $store.state.discuss.up_status }} -->
            <el-button text @click="changeup"  style="margin-top:10px;"  >
                <svg v-if="$store.state.discuss.up_status===0" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-up"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg>
                <svg v-else  xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#409eff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-up"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg>
              </el-button>
          </el-badge>
          <el-badge :value="TEXT.views" :max="9999" class="item">
            <el-button text style="margin-top:10px;"   >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
            </el-button>
          </el-badge>
          <el-button text  style="margin-top:10px;" @click="GoHome('/')" >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
          </el-button>
          <el-button text @click="GoHome('/article/me/')" v-if="$store.state.discuss.article_author===$store.state.account" style="margin-top:20px;margin-left:0" >
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-3"><path d="M12 20h9"></path><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path></svg>
          </el-button>
        </el-affix>
      </el-col>
      <el-col :xs="23" :sm="16">
          <el-card>
              <el-col :span="24">
                  <v-md-preview ref="preview" id="preview" class="dark" :text="TEXT.content"></v-md-preview >
              </el-col>
          </el-card>
      </el-col>
      <!-- 目录 -->
      <el-col class="hidden-xs-only" :gutter="20" :span="5">
          <el-affix :offset="20">
            <el-card shadow="always"> 
              <template #header>
                <div class="card-header">
                  <span>
                    {{ TEXT.title }}
                  </span>
                </div>
              </template>
            <el-scrollbar :height="vue.height" always="true">
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
        </el-card>
        <el-card shadow="always" style="margin-top:20px;"> 
          <template #header>
            <div class="card-header">
              <span>所有文章</span>
            </div>
          </template>
        <el-scrollbar height="400px" always="true">
          <el-col   :span="24">
            <li v-for="i in allTe" class="gogog" @click="gogogogogogo(i.id)" :key="i.id">
              <el-card shadow="hover" >
                {{ i.title }}
                <el-row>
                  <el-col :span="6">
                    <el-statistic title="Up" :value="i.up" />
                  </el-col>
                  <el-col :span="6">
                    <el-statistic title="Views" :value="i.views" />
                  </el-col>
                  <el-col :span="6">
                    <el-statistic title="Discuss" :value="i.discuss">
                    </el-statistic>
                  </el-col>
              </el-row>
            </el-card>
            </li>
          </el-col>
      </el-scrollbar>
    </el-card>
        </el-affix>
      </el-col>
  </el-row>
</el-col>
<div id="hdfhsdfh"></div>
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
import { useRoute } from 'vue-router';
import router from '../../router/index'
import dark from '../../utiles/dark'
import config from '../../utiles/config'
import $ from 'jquery'
import DiscussView from './DiscussView.vue';
  export default{
  components: { DiscussView },
    setup(){
      const vue = reactive({
          asd : '#hdfhsdfh',
          titles:[],
          height:300
      })
      let preview = ref(null)
      let store = useStore()
      let title = ref('')
      let TEXT = ref([])
      let allTe = ref({})
      let textName = ref('')
      const dialogVisible = ref(false)
    const toc = ()=>{
      const anchors = preview.value.$el.querySelectorAll('h2,h3')
      const titles = Array.from(anchors).filter((title) => !!title.innerText.trim());
      const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort();
      vue.titles = titles.map((el) => ({
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
        id:nanoid()
      }));
      console.log(vue.titles.length)
      if(vue.titles.length>=10){
        vue.height = 300
      }else{
        vue.height = 35 * vue.titles.length
      }
    }
    const aaa = ()=>{
      $.ajax({
            url:`${config.API_URL}/user/article/showbyid/`,
            type:'post',
            data:{
              id:textName.value
            },
            success(res){
              if(res.code===1){
                TEXT.value = res.date
                store.commit("updateDiscuss",{
                  index:textName.value,
                  author:TEXT.value.post,
                  title:TEXT.value.title,
                  all_up:TEXT.value.up
                })
                setTimeout(()=>{
                  toc()
                },100)
                showOne()
                store.dispatch("getStatus")
              }else if(res.date==='')router.push("/")
              else{
                router.push("/article")
              }
            },error(){
              router.push("/article")
            }
        })
    }
    const showOne = ()=>{
      $.ajax({
            url:`${config.API_URL}/user/article/showone/`,
            type:'post',
            data:{
              post:TEXT.value.post
            },
            success(res){
              if(res.code===1){
                allTe.value = res.date.filter(i => i.isshow==true || i.post===store.state.account)
                console.log(res)
              }
            }
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
    const gogogogogogo = i =>{
      if(i===textName.value)return 
      textName.value = i
      aaa()
    }
    onMounted(()=>{
      const route = useRoute()
      textName.value = route.params.id
      $('html').css({'--backColor':'#f7f7f7f'})  
      document.getElementsByTagName('body')[0].style.backgroundImage 
            = `url("")` 
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
    const GoHome=i=>{
      router.push(i)
    }
    const changeup = ()=>{
      store.dispatch("changeUp")
    }
    const handleAnchorClick = (anchor)=> {
      $(`${vue.asd}`).removeClass("goto-active")
      $(`#pane- #${anchor.id}`).addClass("goto-active")
      vue.asd = `#pane- #${anchor.id}`
      const { lineIndex } = anchor;
      const heading = preview.value.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);
      if (heading) {
        preview.value.scrollToTarget({
          target: heading,
          scrollContainer: window,
          top: 60,
        });
      }
    }
    return{handleAnchorClick,Todark,
      title,vue,preview,allTe,changeup,
        dialogVisible,GoHome,TEXT,gogogogogogo
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
.gogog{
  margin-bottom: 20px;
  height: 100px;
  list-style: none;
  cursor: pointer;
  transition: 0.2s;
}

.item{
  height: 50px;
}
</style>