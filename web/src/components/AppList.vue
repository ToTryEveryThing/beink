<template>
    <el-dialog v-model="chat" width-head="false"  style="border-radius: 10px;" center :show-close="false">
        <el-tabs v-model="activeName" class="demo-tabs" body-style="padding:0" @tab-click="handleClick">
            <el-tab-pane label="私聊" name="first">
                <chat/>
            </el-tab-pane>
            <el-tab-pane label="群聊" name="second">
                <groupChat/>
            </el-tab-pane>
        </el-tabs>
      </el-dialog>
    <div class="open" v-if="$store.state.background===''">
        <ul>
            <li v-for="i in list1" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
        </ul>
        <ul>
            <li @click="showChat">
                <a href="javascript:void(0);" >
                    <svg aria-hidden="true">
                        <use xlink:href="#icon-globalDiscussion"></use>
                    </svg>
                     <p>聊天</p>    
                </a>    
            </li>
            <li>
                <router-link to="/article">
                    <svg aria-hidden="true">
                        <use xlink:href="#icon-biji"></use>
                    </svg>
                    <p>article</p>
                </router-link>
            </li>
            <li v-for="i in list2" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
        </ul>
    </div>
    <div v-else class="openhh">
        <ul>
            <li @click="showChat">
                <a href="javascript:void(0);" >
                    <svg aria-hidden="true">
                        <use xlink:href="#icon-globalDiscussion"></use>
                    </svg>
                     <p>chat</p>    
                </a>    
            </li>
            <li v-for="i in list2" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
            <li>
                <router-link to="/article">
                    <svg aria-hidden="true">
                        <use xlink:href="#icon-biji"></use>
                    </svg>
                    <p>article</p>
                </router-link>
            </li>
            
            <li v-for="i in list1" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
        </ul>
    </div>

</template>

<script>
    import {reactive,toRefs, ref} from 'vue'
    import { useStore } from 'vuex'
    import { warning } from '@/utiles/message'
    import chat from '@/views/ChatView.vue'
    import groupChat from '@/views/GroupChat.vue'
    export default{
        components:{chat, groupChat},
        setup(){
            const activeName = ref('first')
            const store = useStore()
            const vue = reactive({
                chat:false,
                list1:[
                    {href:'https://fanyi.baidu.com/?aldtype=16047#auto/zh',icon:'#icon-fanyi',name:'翻译'},
                    {href:'https://www.aliyundrive.com/drive',icon:'#icon-wangpan',name:'网盘'},
                    {href:'https://blog.csdn.net',icon:'#icon-boke',name:'博客'},
                    {href:'https://www.bilibili.com/',icon:'#icon-bilibili',name:'bilibil'},
                ],
                list2:[
                    {href:'http://snake.beink.cn/',icon:'#icon-tanchishedazuozhan',name:'Snake'},
                    // {href:'https://www.w3school.com.cn/index.html',icon:'#icon-w3school',name:'w3school'}
                ]
            })
            
            const showChat = ()=>{
                if(store.state.is_login){
                    vue.chat = true
                }else{
                    warning("请登录后操作")
                }
            }

            return {
                activeName,
                ...toRefs(vue),
                showChat
            }
        }
    }

</script>

<style scoped>

    .demo-tabs{
        margin-top: -50px;
    }
    .demo-tabsel-tabs__header{
        margin:0 !important;
    } 
    .demo-tabs >>> .el-tabs__header{
        padding: 0;
    }
    *{
        padding: 0;
        margin: 0;
        border: none;
        text-decoration: none;
    }
    .open{
        position: absolute;
        bottom: 30vh;
        left: 50%;
        transform: translate(-50%);
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .openhh{
        position: absolute;
        bottom: 20vh;
        left: 50%;
        transform: translate(-50%);
        flex-direction: column;
        align-items: center;
    }
   .open li{
        list-style: none;
        width: 120px;
        height: 80px;
        margin-left: 20px;
        margin-right: 20px;
    }
    .openhh li{
        list-style: none;
        width: 90px;
        height: 70px;
        margin-left: 10px;
        margin-right: 10px;
    }
    .open ul{
        position:relative;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        box-shadow:7px  7px  20px #9f9d9d1f;
        padding: 7px;
        margin-top: 10px;
    }
    .openhh ul{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        padding: 7px;
        margin-top: 10px;
    }
    .open a{
        position: relative;
        display: block;
        width: 120px;
        height: 80px;
        display: flex;
        justify-content: center;
        transition: .3s;
        border-radius: 5%;
    }
    .openhh a{
        position: relative;
        display: block;
        width: 90px;
        height: 70px;
        display: flex;
        justify-content: center;
        transition: .3s;
        border-radius: 5%;
    }
    .open  a:hover{
        background-color: var(--color);
        transform:translateY(-10px);
    }
    .openhh a:hover{
        background-color: rgba(225,225,225,.2);
        transform:translateY(-10px);
        color:white;
    }
    .open use{
        position: absolute;
        width: 30px;
        height: 30px;
    }
    .openhh use{
        position: absolute;
        width: 30px;
        height: 30px;
    }
    .open svg{
        margin-top: 10px;
        position: absolute;
        width: 30px;
        height: 30px;
    }
    .openhh svg{
        margin-top: 10px;
        position: absolute;
        width: 30px;
        height: 30px;
    }
    .open a p{
        position:absolute;
        bottom: 0;
        left: 50%;
        transform: translate(-50%);
        color: #2d3436;
        margin-bottom: 15px;
        font-size: 13px;
    }
    .openhh a p{
        position:relative;
        top: 45px;
        color:#ffffff;
        font-size: 11px;
        height: 20px;;
    }
    @media only screen and (max-width: 410px) {
        .openhh{
            bottom: 1vh;
        }
        
      }
</style>