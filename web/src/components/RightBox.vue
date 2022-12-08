<template>
    <img id="time" @click="drawer = true"  src="@/assets/moon.png">
    <el-icon @click="drawer = true"  style="float:right;width:30px;height:90px; margin-right:0px;"><DArrowLeft style="width:20px;height:20px;"/></el-icon>
    <el-drawer size="380px" v-model="drawer" :with-header="false">
        <div class="main" v-if="!$store.state.is_login">
            <router-link to="/login">登录</router-link>
            <router-link to="/register">注册</router-link>
        </div>
        <div class="main" v-else>
            <h3 style="margin-bottom:5px;"><UserFilled style="width: 1em; height: 1em; margin-right: 10px; margin-bottom:-3px;color: #79bbff;" />{{$store.state.account}}</h3>
            <router-link v-if="$store.state.account=='admin'" to="/admin"><More style="width: 1em; height: 1em;  margin-bottom:-3px;color:  #73767a;"/></router-link>
        </div>
        <el-carousel  interval="1000" direction="vertical" >
            <el-carousel-item v-for="i in list" :key="i.id">
                <img v-if="i.id==='666'" @click="changeBackground(i.src)" src="@/assets/4.jpg" alt="">
                <img v-else :src="i.src" @click="changeBackground(i.src)" alt="">
            </el-carousel-item>
        </el-carousel>
        <!-- <el-input v-model="input1" placeholder="Please input">
            <template #prepend>URL:</template>
            <template #append>
                <el-button @click="(changeBackground(input1),input1='')">提交</el-button>
              </template>
        </el-input> -->
        <el-col :span="24" justify="space-evenly" >
            <el-card>
                <el-row  justify="space-evenly" >
                    <el-col  v-for="i in colorList" :key="i.id" :span="4">
                        <el-card shadow="hover"  @click="change(i.backColor,i.color,i.id)" :style="{ backgroundColor: i.backColor }" class="card"> 
                            <el-col :span="4"><div class="grid-content ep-bg-purple" /></el-col>
                        </el-card>
                    </el-col>
                  </el-row>
            </el-card>
        </el-col>
        <el-col  :span="24" :gutter="20">
            <el-card>
                <el-row >
                    <el-col :span="6" v-for="i in $store.state.study.editableTabs" :key="i.name">
                        <el-tag   style="cursor: pointer;"  @click="send(i.name)">
                            {{i.title}}
                        </el-tag>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-row justify="space-evenly">
            <el-col :span="12">
                <el-button type="danger" style="width:100%;" v-if="$store.state.is_login" @click="logout" plain>退出</el-button>
            </el-col>
          </el-row>
    </el-drawer>
</template>

<script>
import { mapState , mapActions} from 'vuex'
import { ElMessage } from 'element-plus'
import {useStore} from 'vuex'
import router from '../router/index'
import $ from 'jquery'
    export default{
        data(){
            return {
                drawer:false,
                visible :false,
                labelPosition :'right',
                store : useStore(),
                ss:[],
                input1:'',
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
    .el-icon {
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
    
</style>