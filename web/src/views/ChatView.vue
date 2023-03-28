<template>
    <el-row class="row-bg" justify="space-evenly">
      <el-col :span="24">
        <el-card shadow="never" body-style="padding:0;">
        <el-row>
          <el-col :sm="5" :xs="7">
            <el-card shadow="never">
              <li>我：<el-tag>{{ name  }}</el-tag>
                <el-switch v-model="value1" style="float:right;"/>
              </li> 
              <el-scrollbar  >
                <el-divider ><el-icon><User /></el-icon></el-divider>
                <ul id="myList">
                  <li class="user"  @click="choice(item)" v-for="item in userList" :key="item">
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/> 
                    <!-- 新消息 -->
                    <el-badge :is-dot="item.status==='true'">
                      <span>{{ item.name }}</span>
                    </el-badge>
                    <el-divider />
                  </li>
                </ul>
              </el-scrollbar>
            </el-card>
          </el-col>
          <el-col :sm="19" :xs="17">
            <el-card  shadow="never">
                <div class="oneUser">{{ oneUserName }}</div>
                <el-divider />
                <el-scrollbar height="60vh" id="srcoll" always class="scroll">
                  <div class="fu"  v-for="j in TTT" :key="j.id">
                    <div v-if="j.from===oneUserName" class="ta" >
                      {{j.content }}
                    </div> 
                    <div v-else id="wo" class="wo">
                      {{j.content }}
                    </div> 
                    <el-divider v-if="value1" >
                      {{ j.date }}
                    </el-divider>
                  </div>
                  <div id="bottom"></div>
                </el-scrollbar>
                <el-divider />
                <el-input
                  maxlength="100"
                  show-word-limit
                  v-model="textarea"
                  :rows="4"
                  type="textarea"
                  placeholder="Please input"
                  @keyup.enter="send"
                />
                <el-button style="float:right;" @click="send" type="primary">发送</el-button>
            </el-card>
          </el-col>
        </el-row>
        </el-card>
      </el-col>
    </el-row>
  </template>

  <script setup>
  import moment from 'moment/moment'
  import {onMounted, ref } from 'vue'
  import {useStore} from 'vuex'
  import { open2 } from '@/utiles/message';
  import $ from 'jquery'
  import {warning} from '@/utiles/message'
  import config from '@/utiles/config'
    let oneUserName = ref("请选择一位发起聊天")
    let oneUserId = ref(0)
    let textarea = ref("")
    let userList = ref([])
    let TTT = ref([])
    let value1 = ref(false)
    let name = sessionStorage.getItem("name")
    const store = useStore()
    const send = ()=>{
      if(textarea.value==='')return
      if(oneUserName.value==="请选择一位发起聊天")return
      if(oneUserId.value===0)return
      store.state.socket.socket.send(JSON.stringify({about:"single",id:oneUserId.value,message:textarea.value}));
      TTT.value.push(
        {
          id:new Date(),
          from:name,
          to:oneUserName.value,
          content:textarea.value,
          date:moment(new Date()). utcOffset( 480). format( 'YYYY-MM-DD HH:mm:ss')
        }
      )
      textarea.value = ''
      setTimeout(function(){
        document.getElementById("bottom").scrollIntoView(false);
      },200)
      // fresh()
    }
    // 所有人
    const changeStatus = (id,status)=>{
      userList.value[id].status = status
    }
    // 选择人
    const choice = (item)=>{
      if(item.name===oneUserName.value)return 
      TTT.value = []
      oneUserName.value = item.name
      oneUserId.value = item.id
      changeStatus(oneUserId.value,"false")
      fresh()
    }
    // 刷新内容
    const fresh = ()=>{
      // console.log("我他妈刷新了")
      $.ajax({
            url:`${config.API_URL}/user/chat/content/`,
            type:'post',
            headers:{
              Authorization:"Bearer " + localStorage.getItem("jwt")
            },
            data:{
              to:oneUserName.value
            },
            success(res){
              TTT.value = res.date
              setTimeout(function(){
                document.getElementById("bottom").scrollIntoView(false);
              },100)
              for (let i in TTT.value){
                TTT.value[i].date = moment(TTT.value[i].date).add(8, 'hours').format( 'YYYY-MM-DD HH:mm:ss')
              }
            } 
        })
    }
    onMounted(()=>{
      store.dispatch("connectToWebSocket", {
                    id: localStorage.getItem("id"),
                    name: localStorage.getItem("name"),
                    token: localStorage.getItem("jwt"),
        });
        setTimeout(() => {
          store.state.socket.socket.send("all");
          store.commit("up",sessionStorage.getItem("name"))
          store.state.socket.socket.onmessage = msg => {
        let value = JSON.parse(msg.data)
        if(value.author==='All'){
          userList.value = value.message
          // 如果某个人退出了 给他删掉
        }
        else if(value.author==='oneself'){
          // 目标用户已下线
          // alert("666")
          oneUserName.value = "请选择一位发起聊天"
          TTT.value = []
          warning(value.message)
        } 
        else if(value.author==="about"){
          open2(value.message)
        }
        else{
          //收到某人的信息
          // 如果正在和当前人通信 即可加入到TTT 不用刷新了
          // 否则 不加   进行提示
          if(value.author===oneUserName.value && value.author!==name){
            TTT.value.push({
              id:new Date(),
              from:value.author,
              to:name,
              content:value.message,
              date:new Date()
            })
          }else{
            // 进行提示
            if(value.author!==name)
            changeStatus(value.to,"true")
          }
        }
        setTimeout(function(){
        document.getElementById("bottom").scrollIntoView(false);
      },50)
          }
        }, 1000);
    })

  </script>
  
  <style scoped>
    li{
      list-style: none;
  
    }
    .oneUser{
        text-align: center;
    }
    .fu{  
      position: relative;
      width: 98%;
      height: 45px;
    }
    .wo{
      position: absolute;
      right: 0px;
      border-radius: 3px;
      color:black;
      background-color:#67c23a;
      padding:10px;
    }
    .ta{
      position: absolute;
      border-radius: 3px;
      background-color: grey;
      padding: 10px;
      color:white;
    }
    .user{
      cursor: pointer;
    }
    #myList li.selected {
      background-color: #292020;
    }
    .scroll >>> .el-scrollbar__view{
  
    }

  </style>