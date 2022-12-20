<template>
    <img id="time" @click="drawer = true"  src="@/assets/moon.png">
    <el-icon @click="drawer = true" class="iicon" style="float:right;width:30px;height:90px; margin-right:0px;"><DArrowLeft style="width:20px;height:20px;"/></el-icon>
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
        <el-col :span="24"  >
            <el-card>
                <el-button @click="dialogBack = true,drawer = false ,jiazai()">更多背景</el-button>
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
                <el-row justify="space-evenly">
                    <el-col :span="4" v-for="i in $store.state.study.editableTabs" :key="i.name">
                        <el-tag   style="cursor: pointer;"  @click="send(i.name)">
                            {{i.title}}
                        </el-tag>
                    </el-col>
                </el-row>
        </el-col>
        <el-divider><el-icon color="#dfd3f4" ><Reading /></el-icon></el-divider>
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
                    <el-link href="https://wallhaven.cc/" type="primary">图片来源</el-link>
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
            
                <li v-for="i in List" :key="i">
                    <div class="load">
                        <el-image  class="imagss"
                        fit="fit"
                        style="width:320px;height:200px;"
                        @click="changeBackground(i)"
                        :src="i" />
                        <el-row class="upp">
                            <el-col :span="2" v-if="$store.state.account==='admin'" >
                                <el-button size="small" type="danger" @click="ddd(i)">删除图片</el-button>
                            </el-col>
                        </el-row>
                    </div>
                </li>
                <li v-if="$store.state.account==='admin'">
                    <div class="upload">
                        <input  type="file" style="width:318px;height:148px;" id="XXX"/>
                        <el-button @click="getTokenn" style="width:318px;height:50px;">提交</el-button>
                    </div>
                </li>
            
        </ul></el-scrollbar>
    </el-dialog>

</template>

<script>
import { mapState ,mapActions } from 'vuex'
import { ElMessage } from 'element-plus'
import {useStore} from 'vuex'
import  OSS from 'ali-oss'
import router from '../router/index'
import login from '../views/AccountLogin'
import register from '../views/AccountRegister.vue'
import $ from 'jquery'
import {onMounted, ref } from 'vue'
    export default{
        components:{login ,register},
        setup(){
            const List = ref([])
            const other = ref('')
            const store = useStore()
            const jiazai = ()=>{
                $.ajax({
                    url:'https://so.beink.cn/user/admin/backlist/show/',
                    type:'post',
                    success(res){
                        List.value = JSON.parse(res)
                    },
                    error(res){
                        console.log(res)
                    }
                 })
            }
            const aa = ()=>{
                $.ajax({
                    url:'https://so.beink.cn/user/admin/backlist/save/',
                    type:'post',
                    headers:{
                        Authorization:"Bearer " + store.state.token
                    },
                    data:{
                        backlist:JSON.stringify(List.value)
                    }
                })
            }
            onMounted(()=>{
                jiazai()
            })
            const getTokenn =()=>{
            let e = document.getElementById('XXX')
            if(e.value=='')return
                $.ajax({
                    url:'https://so.beink.cn/oss/getToken/',
                    type:'get',
                    headers:{
                        Authorization:"Bearer " + store.state.token
                    },
                    success(res){
                        console.log(res)
                        let client = new OSS({
                            region: res.region,
                            accessKeyId: res.accessKeyId,
                            accessKeySecret : res.accessKeySecert,
                            stsToken: res.stsToken,
                            bucket: res.bucket,
                            secure:true
                        })
                        client.put(e.files[0].name,e.files[0])
                        other.value = "https://images.beink.cn/" + e.files[0].name,
                        setTimeout(addSrc,1000)
                    },error(){
                    }
                })
            }

            function addSrc(){
                List.value.push(other.value)
                other.value=''
                aa()
            }
            const ddd = (i)=>{
                List.value = List.value.filter((tab) => tab !== i)
                aa()
            }   
            return{
                List ,ddd,other,jiazai
                ,getTokenn
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
    body{background-size:100% 100%;background-attachment:fixed}
    #time{
        position: absolute;
        right: 0;
        top: 0;
        width: 50px;
        cursor: pointer;
    }
    .el-icon:hover{
        transition:.3s;
        background-color: rgba(225,225,225,.2);
        animation-play-state: paused;
    }
    .iicon {
		animation-timing-function: ease;
		animation: slide-left 1s   infinite ;
	}
	@keyframes slide-left {
		0% {
			transform:translateX(0);
            opacity:0;
		}
		100% {
			transform:translateX(-10px);
            opacity:1;
		}
	}
    @media only screen and (max-width: 410px) {
        #time{
            width: 35px;;
        }
      }
    .load{
        overflow: hidden;
        position: relative;
        cursor: pointer;
        margin-bottom: 10px;
    }
    .load el-button{
        font-size: 10px;
    }
    .load .upp{
        transition: .3s;
        position: absolute;
        transform: translateY(100%);
    }
    .load:hover .upp{
        transform: translateY(-100%);
    }
    .ul{
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
        box-sizing: border-box;
    }
    .upload:hover{
        border:1px grey Dashed;
    }
    
</style>