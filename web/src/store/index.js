//引入vuex
import { createStore  } from 'vuex'
import { error } from '@/utiles/message'
import $ from 'jquery'
import images from './images'
import study from './study'
import guess from './guess'
import discuss from './discuss'
import config from '../utiles/config'
import socket from './webSocket'

//存储全局数据
const state = {
  pulling_info: true,
    id:'',
    account:'',
    token:'',
    backImg:'',
    is_login:false,
    role:'',
    localList:JSON.parse(localStorage.getItem('list')) || [],
    is_author:false,
    list:[
      // 第一个
      {src:'https://images.beink.cn/background/wallhaven-1p18w3.jpg'},
      {src:'https://images.beink.cn/background/wallhaven-5gyg37.jpg'},
      {src:'https://images.beink.cn/background/wallhaven-rr2yow_1920x929.png'},
    ],
    colorList:[
      {id:1,backColor:'#55efc4',color:'#17ddb6de'},
      {id:2,backColor:'#81ecec',color:'#2ae1dc8f'},
      {id:3,backColor:'#74b9ff',color:'#51abf1'},
      {id:4,backColor:'#a29bfe',color:'#8d7ff7'},
      {id:5,backColor:'#f7f7f7',color:'#ddd'},
      {id:6,backColor:'#202123',color:'#262727'},
    ],
    background:JSON.parse(localStorage.getItem('background')) || '',
    backgroundColor:'rgba(19, 206, 102, 0.8)',
    show1:true,
    show2:true
}
//响应组件中的动作
const actions = {
  changeBackground(contest,value){
    localStorage.setItem('background',JSON.stringify(value))
    document.getElementsByTagName('body')[0].style.backgroundImage = `url("${value}")`
    contest.commit('changeBackground',value)
  },
  changeList(contest,value){
    contest.commit('changeList',value)
  },
  login(context,value){
    $.ajax({
      url:`${config.API_URL}/user/account/token/`,
      type:'post',
      data:{
          account:value.account,
          password:value.password,
          code:value.code,
          base64:value.base64
      },
      success(res){
        console.log(res)
        if(res.code === 200){ 
            res  = res.data
            localStorage.setItem("jwt",res.token)
            context.commit("updateToken",res.token)
            value.success(res)
        }else{
            value.error(res.message)
        }
      },
      error(){
      }
  })
  },
  getinfo(context,data){
      $.ajax({
          url:`${config.API_URL}/user/account/info/`,
          type:'get',
          headers:{
              Authorization:"Bearer " + context.state.token
          },
          success(res){
            console.log("gggg",res)
              if(res.code == 200){
                res = res.data
                  context.commit("updateUser",{
                    ...res,
                    is_login : true, 
                  }); 
                  data.success(res);
              }else{
                error(res.message)
                localStorage.removeItem("jwt")
              }   
          },
          error(res){
            error(res.data.message)
            localStorage.removeItem("jwt")
          }
      })
  },
  logout(context){
    localStorage.removeItem("jwt");
    context.commit("logout");
  }
}

//操作数据 
const mutations = {
  changeBackground(state,value){
    state.background = value
  },
  changeList(state,value){
    if(state.localList.length>4)state.localList.pop()
    state.localList.unshift(value)
    localStorage.setItem('list',JSON.stringify(state.localList))
    if(state.is_login===true){
      $.ajax({
          url:`${config.API_URL}/user/account/color/`,
          type:'post',
          headers:{
              Authorization:"Bearer " + state.token
          },
          data:{
              account:state.account,
              backImg:localStorage.getItem("color"),
              list:(localStorage.getItem("list"))
          },
      })
    }
  },
  dele(state,id){
    state.localList = state.localList.filter((list)=>{
          return list.id!==id
      })
    localStorage.setItem('list',JSON.stringify(state.localList))
    if(state.is_login===true){
      $.ajax({
          url:`${config.API_URL}/user/account/color/`,
          type:'post',
          headers:{
              Authorization:"Bearer " + state.token
          },
          data:{
              account:state.account,
              backImg:localStorage.getItem("color"),
              list:(localStorage.getItem("list"))
          },
      })
    }
  },
  updateUser(state,value){
    state.id = value.id
    state.account = value.account
    localStorage.setItem("id",value.id)
    localStorage.setItem("name",value.account)
    state.is_login = value.is_login
    state.backImg = value.backImg
    state.role = value.role
    state.localList = JSON.parse(value.listlist)
    sessionStorage.setItem("name",state.account)
    sessionStorage.setItem("role",value.role)
    localStorage.setItem("color",value.backImg)
    localStorage.setItem("list",value.listlist)
  },
  updateToken(state,value){
    state.token = value 
  },
  logout(state){
    state.id = ""
    state.account = ""
    state.is_login = false
    state.token = ""
    state.backImg = "6"
    state.date = ""
    state.role = ""
  },
  updatePullingInfo(state, pulling_info) {
    state.pulling_info = pulling_info;
  },
  textAuthor(state,value){
    if(state.account===value){
      state.is_author = true
    }else{
      state.is_author = false
    }

  }


}

//加工state中的数据
//类似于全局计算属性
const getters = {
  bigAge(state){
    return state.age*5
  }
}

//导出store
export default createStore({
  //名字相同可以简写
    state,
    actions,
    mutations,
    getters,
    modules: {
      images:images,
      study:study,
      guess:guess,
      discuss:discuss,
      socket
    }
  

})
