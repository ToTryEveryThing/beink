<template>
    <el-button type="primary"  class="open hidden-xs-only" v-if="$store.state.discuss.post_title!==''" style="margin-left: 16px" @click="drawer = true">
        <el-icon><Comment style="width: 1em; height: 1em;"/></el-icon>
    </el-button>
    <!-- <el-backtop :right="100" :bottom="100" /> -->
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
                        <el-tooltip content="时间" placement="bottom" effect="light">
                            <el-switch
                                class="switch"
                                v-model="showDate"
                                inline-prompt
                                active-text="是"
                                inactive-text="否"
                            />
                          </el-tooltip>
                    </el-col>
                  </el-row>
              </template>
            <el-scrollbar height="85vh" style="margin-top:-30px;">
                <el-empty description="无人评论" v-if="$store.state.discuss.count===0" />
                <el-card  v-for="i in $store.state.discuss.content" :key="i" shadow="never" style="margin-top:10px;">  
                    <div class="dis">
                        <div class="name">
                            <span light v-if="i.userName===i.postName">
                                <el-tag>{{ i.userName }} </el-tag> 
                            </span>
                            <span v-else>{{ i.userName }}</span>
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
                            <el-button  text v-if="!$store.state.discuss.discussStatus[i.id]" @click="up(i.id,i)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-up"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg>
                                &nbsp;&nbsp;{{ i.up }}
                            </el-button>
                            <el-button  text v-else @click="down(i.id,i)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#0066ff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-thumbs-up"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg>
                                &nbsp;&nbsp;{{ i.up }}
                            </el-button>
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
                    :total="$store.state.discuss.count"
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
import config from "@/utiles/config"
export default {
    setup(){
        const store = useStore()
        let drawer = ref(false)
        const vue = reactive({
            reply:'',
            showDate:false,
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
            store.commit("showDiscuss",{
                page:vue.page,
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
            if(store.state.is_login)
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
                        store.commit("upupupup",{status:true,value:y})
                    }
                },
            })
        }
        const down=(i,y)=>{
            if(store.state.is_login)
            $.ajax({
                url:`${config.API_URL}/user/down/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    article_id:i
                },
                success(res){
                    if(res.code===1){
                        store.commit("upupupup",{status:false,value:y})
                    }
                },
            })
        }
        return{
            ...toRefs(vue),ppage,handleCurrentChange,
            drawer,show,Reply,del,up,down
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