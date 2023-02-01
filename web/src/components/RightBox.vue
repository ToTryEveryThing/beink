<template>
    <img id="time" @click="drawer = true"  src="@/assets/moon.png">
    <el-drawer size="380px" v-model="drawer" :with-header="false">
        <div class="main" v-if="!$store.state.is_login">
            <el-button @click="dialogLogin = true ,drawer = false">登录</el-button>
            <el-button @click="dialogRegister = true,drawer = false">注册</el-button>
        </div>
        <div class="main" v-else>
            <h3 style="margin-bottom:5px;"><UserFilled style="width: 1em; height: 1em; margin-right: 10px; margin-bottom:-3px;color: #79bbff;" />{{$store.state.account}}</h3>
            <router-link v-if="$store.state.account=='admin'" to="/admin"><More style="width: 1em; height: 1em;  margin-bottom:-3px;color:  #73767a;"/></router-link>
        </div>
        <el-carousel  interval="2000" direction="horizontal" >
            <el-carousel-item v-for="i in list" :key="i.src">
                <img :src="i.src"  @click="changeBackground(i.src)" alt="">
            </el-carousel-item>
        </el-carousel>
        <el-col :span="24">
            <el-card>
                <el-button @click="dialogBack = true,drawer = false">更多背景</el-button>
                <el-button @click="dark">切换模式</el-button>
                <el-button style="float:right;" @click="changeBackground('')">取消背景</el-button>
            </el-card>
        </el-col>
        <el-divider><el-icon color="#dfd3f4"><Picture /></el-icon></el-divider>
        <el-col :span="24" justify="space-evenly" >
                <el-row  justify="space-evenly" >
                    <el-col  v-for="i in colorList" :key="i.id" :span="4">
                        <el-card shadow="hover"  @click="change(i.backColor,i.color,i.id)" :style="{ backgroundColor: i.backColor }" class="card"> 
                        </el-card>
                    </el-col>
                </el-row>
        </el-col>
        <el-divider><el-icon color="#dfd3f4"><MagicStick /></el-icon></el-divider>
        <el-col  :span="24" :gutter="20">
                <el-row class="hhhhhhhh">
                    <el-col :span="6" v-for="i in $store.state.study.editableTabs" :key="i.name">
                        <el-tag   style="cursor: pointer;"  @click="send(i.name)">
                            {{i.title}}
                        </el-tag>
                    </el-col>
                </el-row>
        </el-col>
        <el-divider><el-icon color="#dfd3f4" ><Reading /></el-icon></el-divider>
        <APP/>
        <el-divider><el-icon color="#dfd3f4" ><Link /></el-icon></el-divider>
        <chat style="text-algin:center;"/>
        <el-divider><el-icon color="#dfd3f4"><ChatDotRound /></el-icon></el-divider>
        <el-row justify="space-evenly">
            <el-col :span="12">
                <el-button type="danger" style="width:100%;" v-if="$store.state.is_login" @click="logout" plain>退出</el-button>
            </el-col>
          </el-row>
    </el-drawer>
    <el-dialog destroy-on-close :show-close="false" :fullscreen="true" v-model="dialogLogin" >
        <template #header="{close}">
            <div class="my-header" style="float:right;">
              <el-button type="danger" @click="close">
                关闭
              </el-button>
            </div>
          </template>
        <login v-slot="hh" >
            <li style="display:none;" v-if="hh.keyyy===true?dialogLogin=false:dialogLogin=true"></li>
        </login>
    </el-dialog>
    <el-dialog destroy-on-close :fullscreen="true" :show-close="false" v-model="dialogRegister" >
        <template #header="{close}">
            <div class="my-header" style="float:right;">
              <el-button type="danger" @click="close">
                关闭
              </el-button>
            </div>
          </template>
        <register v-slot="hh">
            <li style="display:none;" v-if="hh.keyyy===true?(dialogRegister=false,dialogLogin=true):dialogRegister=true"></li>
        </register>
    </el-dialog>
    <el-dialog style="padding:0;" width="55%" :show-close="false"   :draggable="true" v-model="dialogBack" >
        <template #header="{close}">
            <el-row justify="space-between">
                <el-col :span="8">
                    <h4  style="float:left;">切换背景</h4>
                    <el-tag >
                        <el-link href="https://wallhaven.cc/" type="primary">图片来源</el-link>
                    </el-tag>
                    
                </el-col>
                <el-col :span="8">
                    <el-button  style="float:right;" size="small" type="danger" @click="close">
                        关闭
                      </el-button>
                </el-col>
              </el-row>
          </template>
          <el-scrollbar height="420px">
          <ul class="ul">
                <li v-for="i in $store.state.images.ImagesList" :key="i">
                    <div class="load">
                        <el-image  class="imagss"
                        fit="fit"
                        style="width:320px;height:200px;"
                        @click="changeBackground('https://images.beink.cn/'+i)"
                        :src="'https://images.beink.cn/'+i" />
                        <button class="delete" @click="ddd(i)" v-if="$store.state.account==='admin'"></button>

                    </div>
                </li>
                <li v-if="$store.state.account==='admin'">
                    <div class="upload">
                            <input  type="file" style="width:318px;height:148px;" id="XXX"/>
                            <el-button @click="upload" style="width:318px;height:50px;">提交</el-button>
                    </div>
                </li>
        </ul></el-scrollbar>
    </el-dialog>
</template>

<script>
import darkClass from '../utiles/dark.js'
import { mapState ,mapActions } from 'vuex'
import { ElMessage ,ElNotification  } from 'element-plus'
import {useStore} from 'vuex'
import chat from './GoChat.vue'
import router from '../router/index'
import login from '../views/AccountLogin'
import register from '../views/AccountRegister.vue'
import APP from '../components/AppCURD.vue'
import $ from 'jquery'
import {onMounted, ref } from 'vue'
    export default{
        components:{login ,register ,APP ,chat},
        setup(){
            const List = ref([])
            const store = useStore()
            onMounted(()=>{
                store.dispatch("getList")
                if(localStorage.getItem("info")===null){
                    open2()
                    localStorage.setItem("info","info")
                }
            })
            const dark = ()=>{
                let Class = localStorage.getItem("theme") || "";
                if(Class==="dark"){
                    darkClass()
                    localStorage.setItem("theme","sun")
                }else{
                    darkClass("dark")
                    localStorage.setItem("theme","dark")
                }
            }
            const open2 = () => {
                ElNotification({
                    title: 'info',
                    message: "点击右侧图标 以查看更多的功能",
                    position: 'top-right',
                    type: 'info',
                })
            }
            const upload = ()=>{
                let e = document.getElementById('XXX')
                if(e.value=='')return
                var formData = new FormData(); 
                //表单可以增加数据 如下
		        formData.append('file', e.files[0]); //传给后端的路径
                formData.append('account',store.state.account)
                store.dispatch("upload",{
                    token:store.state.token,
                    formData:formData,
                })
            }
            const ddd = (i)=>{
                store.dispatch("delete",{
                    url:i,
                    account:store.state.account,
                    token:store.state.token,
                })
            }   
            return{
                List ,ddd,upload,dark
            }
        },
        data(){
            return {
                dialogLogin:false,
                dialogRegister:false,
                drawer:false,
                dialogBack:false,
                visible :false,
                labelPosition :'right',
                store : useStore(),
            }
        },
        computed:{
            ...mapState(['background','list','colorList']),
        },
        mounted(){
            let hour = new Date().getHours()
            let Time = document.getElementById("time")
            if(hour<18&&hour>=6)Time.src = require('@/assets/sun.png')
            else Time.src = require('@/assets/moon.png')  
            document.getElementsByTagName('body')[0].style.backgroundImage 
            = `url("${this.background}")`
            let i = this.store.state.backImg||localStorage.getItem("color")||6
            $('html').css({'--backColor':this.colorList[i-1].backColor})  
            $('html').css({'--color':this.colorList[i-1].color})
        },
        methods:{
            ...mapActions(['changeBackground']),
            open1() {
                ElMessage('退出登录')
            },
            logout(){
                sessionStorage.setItem("name","")
                this.store.dispatch("logout")
                this.open1()
            },
            send(i){
                this.$bus.emit('send',i)
                router.push({name:'study'})
            },
            change(a,b,c){
                if(this.store.state.background!=='')return
                if(this.store.state.is_login===true){
                        $.ajax({
                            url:'https://so.beink.cn/user/account/color/',
                            type:'post',
                            headers:{
                                Authorization:"Bearer " + this.store.state.token
                            },
                            data:{
                                account:this.store.state.account,
                                backImg:c,
                                list:(localStorage.getItem("list"))
                            },
                        })
                    }
                localStorage.setItem("color",c)
                $('html').css({'--backColor':a})  
                $('html').css({'--color':b})
            }
        },
    }
</script>

<style >
    .card{
        margin-left:1px;
    }
    .main{
        display: flex;
        justify-content: space-around;
    }
    .main a{
        display: block;
        width: 120px;
        text-align: center;
        font-size: 18px;
        margin-bottom: 5px;
        color:#a29bfe;
        transition: .3s;
    }
    .main a:hover{
        color:#1e00ff;
    }
    .login{
        display: flex;
        margin-top: 20px;
    }
    .el-carousel{
        overflow: hidden;
        height: 173px;
        width:340px;
    }
    .el-carousel img{
        height: 173px;
        width:340px;
        
    }
    .el-drawer{
        width: 400px;
    }
    *{
        padding: 0;
        margin: 0;
    }
    body{
        background-size:100% 100%;
        background-size:cover;
        height: 100%;
        background-attachment: fixed;
        background-repeat: no-repeat;
    }
    #time{
        position: absolute;
        right: 0;
        top: 0;
        width: 50px;
        cursor: pointer;
    }
    .ul li:hover{
        transition:.3s;
        background-color: rgba(225,225,225,.2);
        animation-play-state: paused;
    }
    @media only screen and (max-width: 410px) {
        #time{
            width: 35px;;
        }
      }
    .load{
        position: relative;
        cursor: pointer;
        margin-bottom: 10px;
    }
    .delete{
        border: none;
        cursor: pointer;
        border-radius: 4px;
        background: #d43e4b;
        position: absolute;
        z-index: 0;
        width:320px;
        height:0px;
        left: 0;
        bottom:4px;
        transition: .3s;
    }
    .imagss:hover + .delete ,.delete:hover{
        height:40px;
    }
    .imagss{
        border-radius: 4px;
        transition: .2s;
    }
    .imagss:hover,.upload:hover{
        box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
    }
    .ul{
        box-sizing: border-box;
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        overflow: auto;
    }
    .ul li{
        list-style: none;
        margin-left: 10px;
    }  
    .upload{
        width:320px;
        height:200px;
        transition: .2s;
    }
    .hhhhhhhh{
        display: flex;
        justify-content: space-around;
    }
    
</style>