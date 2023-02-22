<template>
    <el-button type="primary"  class="open hidden-xs-only" v-if="$store.state.discuss.post_title!==''" style="margin-left: 16px" @click="drawer = true">
        <el-icon><Comment style="width: 1em; height: 1em;"/></el-icon>
    </el-button>
      <el-drawer 
      class="main"
      size="450px"
      @open="show"
      v-model="drawer"
       :show-close="false"
       :lock-scroll="false"
       :with-header="true">
            <template #header>

                <el-row>
                    <el-col :span="8">{{ $store.state.discuss.post_title }}</el-col>
                    <el-col :span="8">
                        <el-switch
                            class="switch"
                            v-model="showDate"
                            inline-prompt
                            active-text="是"
                            inactive-text="否"
                        />
                    </el-col>
                  </el-row>
              </template>
            <el-scrollbar height="85vh" style="margin-top:-30px;">
                <el-empty description="无人评论" v-if="count===0" />
                <el-card  v-for="i in content" :key="i" shadow="never" style="margin-top:10px;">  
                    <div class="dis">
                        <div class="name">
                            <span><el-tag  light v-if="i.userName===i.postName">作者</el-tag>  {{ i.userName }}</span>
                            <span v-show="showDate">{{ i.date }}</span>
                        </div>
                        <br>
                        
                        <div class="text">
                            <div v-text="i.content"> </div>
                        </div>

                        <el-button-group class="de">
                            <el-button v-if="$store.state.account===i.userName" @click="del(i.id)"  type="danger" text> 
                                <el-icon><Delete color="grey"/></el-icon>
                            </el-button>
                            <el-button  text  @click="up(i.id,i)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-up"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg>
                                {{ i.up }}
                            </el-button>
                            <!-- <el-button  text >
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-down"><path d="M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17"></path></svg>
                                2
                            </el-button> -->
                          </el-button-group>
                    </div>
                </el-card>
                <el-pagination
                    :page-size="6"
                    small
                    background
                    :hide-on-single-page="true"
                    @current-change="handleCurrentChange"
                    @next-click="ppage(1)" @prev-click="ppage(-1)"
                    layout="prev, pager, next"
                    :total="count"
                    class="mt-4"
                />
              </el-scrollbar>
            <el-input v-model="reply" class="reply" placeholder="Please input">
                <template #append>
                    <el-button @click="Reply">发表</el-button>
                </template>
            </el-input>
      </el-drawer>
</template>

<script>
import { reactive, ref, toRefs } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import moment from 'moment/moment'
import config from "@/utiles/config"
export default {
    setup(){
        const store = useStore()
        let drawer = ref(false)
        const vue = reactive({
            reply:'',
            content:[],
            showDate:false,
            count:0,
            page:1
        })
        const del=(i)=>{
            $.ajax({
                url:`${config.API_URL}/user/discuss/delete/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                   id:i
                },
                success(res){
                 if(res.code===1){
                    show()
                 }
                },
            })
        }
        const handleCurrentChange = (number)=>{
            vue.page = number
            show()
        }
        const ppage=(i)=>{
            vue.page += i
            show()
        }
        const show=()=>{
            $.ajax({
                url:`${config.API_URL}/user/discuss/show/`,
                type:'post',
                data:{
                    post_name:store.state.discuss.post_name,
                    post_index:store.state.discuss.post_index,
                    page:vue.page
                },
                success(res){
                  if(res.code===1){
                    vue.count = res.count
                    vue.content = res.data
                    vue.content.forEach((i,index)=>{
                        vue.content[index].date = moment(i.date).format('YYYY-MM-DD HH:mm')

                  })
                  }
                },
            })
        }
        const Reply=()=>{
            if(vue.reply===null||!store.state.is_login)return
            $.ajax({
                url:`${config.API_URL}/user/discuss/add/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    content:vue.reply,
                    post_name:store.state.discuss.post_name,
                    post_index:store.state.discuss.post_index
                },
                success(res){
                  if(res.code===1){
                    show()
                    vue.reply = ''
                  }
                },
            })
        }
        const up=(i,y)=>{
            $.ajax({
                url:`${config.API_URL}/user/up/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    article_id:i
                },
                success(res){
                    if(res.code===1){
                        vue.content[vue.content.indexOf(y)].up++
                    }
                },
            })
        }
        return{
            ...toRefs(vue),ppage,handleCurrentChange,
            drawer,show,Reply,del,up
        }
    }
}
</script>

<style scoped> 
    .main{
        overflow: hidden;
        position: relative;
    }
    .open{
        position: fixed;
        z-index: 50;
        bottom: 140px;
        right: 6px;
    }
    .reply{
        width: 400px;
        position: fixed;
        bottom: 30px;
    }
    .dis{
        width:367px;
        display: flex;
        flex-wrap: wrap;
        position: relative;
    }
    .name{
        display: flex;
        font-size: 10px;
        width: 370px;
        color: grey;
    }
    .name span{
        margin-right: 10px;
    }
    .name :nth-child(2){
        position: relative;
        right: 0px;
    }
    .text{
        width: 367px;
    }
    .de{
        position: absolute;
        top: 0;
        right: 0;
    }

    .el-drawer__header>:first-child{
        flex: 0;
    }
    .mo{
        display: none;
    }
    .el-card:hover  .mo{
        display: block;
    }
</style>