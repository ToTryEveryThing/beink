//引入vuex
import { createStore  } from 'vuex'
import $ from 'jquery'
import admin from './admin'
import study from './study'
//存储全局数据
const state = {
  pulling_info: true,
    id:'',
    account:'',
    token:'',
    backImg:'',
    is_login:false,
    localList:JSON.parse(localStorage.getItem('list')) || [],
    list:[
      // 第一个
      {src:require('@/assets/41ca469687507ff4e7c6a3c0d3b0ff56.jpg')},
      {src:require('@/assets/wallhaven-1jpdz3.png')},
      {src:require('@/assets/wallhaven-z8mk1v.png')},
      {src:require('@/assets/wallhaven-rr2yow_1920x929.png')},
    ],
    list1:[
      // 第一个
      {src:require('@/assets/41ca469687507ff4e7c6a3c0d3b0ff56.jpg')},
      {src:require('@/assets/wallhaven-1jpdz3.png')},
      {src:require('@/assets/wallhaven-4geeqq.jpg')},
      {src:require('@/assets/wallhaven-z8mk1v.png')},
      {src:require('@/assets/wallhaven-vg8mo8.jpg')},
      {src:require('@/assets/wallhaven-rr2yow_1920x929.png')},
      {src:require('@/assets/wallhaven-l83k62.jpg')},
      {src:require('@/assets/wallhaven-jx5pm5.jpg')},
      {src:require('@/assets/wallhaven-weywvr.jpg')},
      {src:require('@/assets/wallhaven-7p3we9.png')},
      {src:require('@/assets/wallhaven-d6gydo.png')},
      {src:'https://w.wallhaven.cc/full/jx/wallhaven-jx5yq5.png'},
    ],
    colorList:[
      {id:1,backColor:'#55efc4',color:'#17ddb6de'},
      {id:2,backColor:'#81ecec',color:'#2ae1dc8f'},
      {id:3,backColor:'#74b9ff',color:'#51abf1'},
      {id:4,backColor:'#a29bfe',color:'#8d7ff7'},
      {id:5,backColor:'#dfe6e9',color:'#b2bec3'},
      {id:6,backColor:'#f7f7f7',color:'#ddd'},
    ],
    background:JSON.parse(localStorage.getItem('background')) || '',
    backgroundColor:'rgba(19, 206, 102, 0.8)'
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
      url:"https://so.beink.cn/user/account/token/",
      type:'post',
      data:{
          account:value.account,
          password:value.password
      },
      success(res){
        if(res.message === "success"){  
            localStorage.setItem("jwt",res.token)
            context.commit("updateToken",res.token)
            value.success(res)
        }  else{
            value.error(res)
        }
      },
      error(res){
          value.error(res)
      }
  })
  },
  getinfo(context,data){
      $.ajax({
          url:'https://so.beink.cn/user/account/info/',
          type:'get',
          headers:{
              Authorization:"Bearer " + context.state.token
          },
          success(res){
              if(res.message == "success"){
                  context.commit("updateUser",{
                    ...res,
                    is_login : true, 
                  }); 
                  data.success(res);
              }else{
                data.success(res);
              }   
          },
          error(res){
              data.error(res)
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
          url:'https://so.beink.cn/user/account/color/',
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
          url:'https://so.beink.cn/user/account/color/',
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
    state.is_login = value.is_login
    state.backImg = value.backImg
    state.localList = JSON.parse(value.listlist)
    sessionStorage.setItem("name",state.account)
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
  },
  updatePullingInfo(state, pulling_info) {
    state.pulling_info = pulling_info;
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
      admin:admin,
      study:study
    }
  

})
