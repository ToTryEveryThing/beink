<template>
    <div class="Search-type" >
        <form :class="[$store.state.background===''?'input1':'input2']"   action="https://www.baidu.com/baidu" target="_top">   
          <svg aria-hidden="true" class="baidu">
            <use xlink:href="#icon-baidu"></use>
          </svg>   
              <input id="input" @blur="showed"  type="text" @focus="hh"  @keyup="choise"  placeholder="请输入内容" name="word"  v-model="name">  
              <div class="suggest">
              <ul v-if="is_show" id="search-result">
                <li  v-for="i in list" :key="i"  @click="click(i)">{{i}}</li>
              </ul>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios'
import { reactive,toRefs,watch} from 'vue'
export default{
    setup() {
      var index = -1
        const vue = reactive({
          is_show:false,
          name:'',
          list:[
          ]
        })
        function showed(){
          setTimeout(function(){
            vue.is_show = false
          },100)
        }
        function hh(){
          vue.is_show = true
          setTimeout(function(){
            axios.get(`apis/su?wd=${encodeURI(vue.name)}`).then(
              response => {
                  vue.list = []
                  if(vue.name===' ')return 
                  if(response.data===' ')return 
                  var st
                  var en
                  let h = response.data
                  for(let i =0;i<h.length;i++){
                    if(h[i]==='[')st=i
                    if(h[i]==='}')en=i
                  }
                  h = h.slice(st,en)
                  vue.list=JSON.parse(h)
              },
              error => { 
                  console.log("失败", error.message);
              }
            )
          },150)
        }
        function click(i){
          window.open(`https://www.baidu.com/s?word=${encodeURI(i)}`)
            vue.list=[]
            vue.name=''
        }
        watch(()=>vue.name,()=>{
            hh()
        })
        function choise(e){
          if(e.keyCode===37||e.keyCode===39||e.keyCode===8)index=-1
          let ul = document.getElementsByTagName('ul')[0];
          if(e.keyCode===38){//上
            if(index!==-1)
            ul.children[index].style.backgroundColor=''
            if(index<=0)index=vue.list.length-1 
            else index--
            document.getElementsByTagName('input')[0].value = vue.list[index]
            ul.children[index].style.backgroundColor='#7f7f7f3a'
          }
          if(e.keyCode===40){//下
            if(index!==-1)
            ul.children[index].style.backgroundColor=''
            if(vue.list.length<index+2)index=0
            else index++
            document.getElementsByTagName('input')[0].value = vue.list[index]
            ul.children[index].style.backgroundColor='#7f7f7f3a'
          }
        }
        return {
          ...toRefs(vue),hh,click,choise,showed
        }
    }

  }
</script>

<style scoped>

  .baidu{
    position: absolute;
    width: 25px;
    top: -55px;
    padding-left: 8px;
  }
  *{
    padding:0;
    margin: 0;
  }  
  .Search-type{
    position: relative;
    display: flex;
    justify-content: center;
    margin-bottom: 20px;

  }
  .input1 input{
    border:none;
    border-radius: 10px;
    outline: none;
    font-size: 15px;
    line-height: 5px;
    height:40px;
    width: 625px;
    color:#2f3542;
    text-indent: 40px;
    transition: .3s;
    background-color: #ffffff4a;
    box-shadow: 2px 2px 10px var(--color);
  }
  .input2 input{
    left: 10px;
    border:none;
    border-radius: 10px;
    outline: none;
    font-size: 14px;
    line-height: 5px;
    height:38px;
    width: 625px;
    color:#2f3542;
    text-indent: 40px;
    transition: .3s;
    background-color: #ffffffde;
    box-shadow: 2px 2px 20px#8a858560;
  }
  .input1 input:hover{
    box-shadow: 2px 2px 20px#a3a1a160;
  }
  .suggest{
    position:relative;
  }
  .input1 ul{
    position: absolute;
    z-index: 99999;
    width: 100%;
    background-color: var(--backColor);
    box-shadow: 7px 8px 13px 0px var(--color);
  }
  .input2 ul{
    position: absolute;
    z-index: 99999;
    width: 100%;
    background-color: #ffffff81;
    box-shadow: 2px 2px 20px#8a858560;
  } 
  li{
    position: relative;
    list-style: none;
    font-size:15px;
    line-height:30px;
    height: 30px;
    padding-left:5px;
    transition: .2s;
    background-color: #ffffff4a;
    z-index: -999999999999999;
    padding-left: 10px;
  }
  li:hover{
    background-color: #7f7f7f3a;
  }
  @media only screen and (max-width: 410px) {
    .input1 input,.input2 input {
      height:35px;
      width: 325px;
    }
    input{
      background-color: white !important;
    }
  }
  input:focus  + .xxx{
    height:23px;
    background-color: rgba(0,0,0,0);
  }
  .xxx{
    position: relative;
    width: 2px;
    height: 23px;
    margin-top: -30px;
    margin-left: 9px;
    margin-right: 10px;;
    background-color: black;
  }
  @keyframes xxx{
    0% {
			transform:scaleY(1);
			opacity:1;
		}
		100% {
			transform:scaleY(0.3);
			opacity:1;
		}
  }
</style>