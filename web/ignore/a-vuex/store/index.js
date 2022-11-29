//引入vuex
import { createStore  } from 'vuex'

//存储全局数据
const st = {
  name:'李明',
  age:15,
  sex:'man',
  list:[
    {id:'001',name:'Hello World'}
  ]
}

//响应组件中的动作
const actions = {
    hh(context,value){
      //context 上下文 
      // value 接受的数据
      console.log('我是actions 我响应了')
      //开始操作数据
      context.commit('HH',value)
    }
}

//操作数据 
const mutations = {
  HH(state,value){
    //state 其中有数据   现在可以改了
    //value 接受的数据
    console.log("我是mutations 我被actions用了")
    state.age+=value
  },
  add(state,value){
    console.log("被调用")
    state.list.unshift(value)
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
    state:st,
    actions,
    mutations,
    getters

})
