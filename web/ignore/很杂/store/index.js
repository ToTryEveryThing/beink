//引入vuex
import { createStore } from 'vuex'

//存储全局数据
const st = {
  name:'李明',
  age:15,
  sex:'man',
  list:[
    {id:'001',say:'Hello World'}
  ]
}
//相应组件的动作
const actions = {

}


//操作数据
const mutations = {

}

//导出store
export default createStore({
  //名字相同可以简写
    state:st,
    actions,
    mutations

})
