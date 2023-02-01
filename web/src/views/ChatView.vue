<template>
    <el-row class="row-bg" justify="space-evenly">
      <el-col :span="24">
        <el-card shadow="never" >
        <el-row>
          <el-col :sm="5" :xs="7">
            <el-card shadow="never">
              <li>在线用户 我：<el-tag>{{ name  }}</el-tag></li> 
              <el-scrollbar  >
                <el-divider ><el-icon><User /></el-icon></el-divider>
                <li class="user"  @click="choice(item)" v-for="item in userList" :key="item">
                  <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/> 
                  <!-- 新消息 -->
                  <el-badge :is-dot="item.status==='true'">
                    <span>{{ item.name }}</span>
                  </el-badge>
                  <el-divider />
                </li>
              </el-scrollbar>
            </el-card>
          </el-col>
          <el-col :sm="19" :xs="17">
            <el-card  shadow="never">
                <div class="oneUser">{{ oneUserName }}</div>
                <el-divider />
                <el-scrollbar height="64vh" id="srcoll" always class="scroll">
                  <div class="fu"  v-for="j in withContext.context" :key="j.author">
                    <div v-if="j.author==='wo'" class="wo" >{{j.context }}</div> 
                    <div v-if="j.author==='ta'" id="ta" class="ta">{{j.context }}</div> 
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
  import {ref ,onMounted , reactive ,onUnmounted } from 'vue'
  import router from '../router/index'
  import {success} from '../utiles/message'
    let oneUserName = ref("请选择一位发起聊天")
    let oneUserId = ref(0)
    let textarea = ref("")
    let userList = ref([])
    let withContext = ref([{}])
    let context = reactive([])
    let name = sessionStorage.getItem("name")
    let id = sessionStorage.getItem("id") 
    let socket = null;
    const send = ()=>{
      if(textarea.value==='')return
      if(oneUserName.value==="请选择一位发起聊天")return
      if(oneUserId.value===0)return
      socket.send(JSON.stringify({id:oneUserId.value,message:textarea.value}));
      withContext.value.context.push({author:'wo',context:textarea.value})
      textarea.value = ''
      setTimeout(function(){
        document.getElementById("bottom").scrollIntoView(false);
      },200)
    }
    const createContext = (name)=>{
       let f = false
       let x = 0
        for(let i=0;i<context.length;i++){
          if(context[i].name===name){
            f = true
            x = i;
          }
        }
        if(f)return x;
        else{
          context.push({"name":name,"context":[]})
          return context.length-1
        }
    }
    const changeStatus = (id,status)=>{
      userList.value[id].status = status
    }
    const choice = (item)=>{
      oneUserName.value = item.name
      oneUserId.value = item.id
      let x = createContext(item.name)
      changeStatus(oneUserId.value,"false")
      withContext.value = context[x]
      document.getElementById("bottom").scrollIntoView(false);
    }
    onMounted(()=>{
      socket = new WebSocket(`wss://so.beink.cn/websocket/${id}/${name}`);
      socket.onopen = () => {
        success("连接成功")
      }
      socket.onmessage = msg => {
        let value = JSON.parse(msg.data) 
        if(value.author==='All'){
          userList.value = value.message
          // 如果某个人退出了 给他删掉
        }else{
          //收到某人的信息
          let x = createContext(value.author)
          context[x].context.push({author:'ta',context:value.message})
          // 如果正在和当前人通信 即可加入到withContext
          // 否则 不加   进行提示
          if(value.author===oneUserName.value){
            withContext.value = context[x]
            setTimeout(function(){
              document.getElementById("bottom").scrollIntoView(false);
            },200)
            
          }else{
            // 进行提示
            changeStatus(value.to,"true")
          }
        }
      }
      socket.onclose = () => {
          router.push("/")
      }
    })
    onUnmounted(() => {
        socket.close();
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
      right: 0;
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
    .scroll >>> .el-scrollbar__view{
  
    }

  </style>