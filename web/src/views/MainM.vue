<template>
    <div style="padding-top:10vh"   :class="[$store.state.background===''?'time1':'time2']">
        {{hour}}:{{min}}
    </div>
    <ToSearch ></ToSearch>
    <LocalStorage v-if="$store.state.show1" />
    <RightBox />
    <AppList v-if="$store.state.show2"/>
    <div class="copyright" >
        <a  href="https://beian.miit.gov.cn/#/Integrated/index">👻豫ICP备2021034834号 </a> <el-divider direction="vertical" />
        <a  href="https://blog.csdn.net/qq_53950686"> 🪁2023 by beink  </a> <el-divider direction="vertical" />
        <a href="https://github.com/ToTryEveryThing/beink">✨Github</a>
  </div>
</template>

<script>
import {useStore} from 'vuex'
// import router from '../router/index'
import LocalStorage from '../components/LocalStorage.vue';
import ToSearch from '../components/ToSearch.vue';
import RightBox from '../components/RightBox.vue';
import AppList from '../components/AppList.vue';
import $ from 'jquery'
import {reactive,toRefs,onMounted} from 'vue'
    export default{
        components:{ LocalStorage, ToSearch, RightBox,AppList },
        setup(){
            const store = useStore();
            const vue = reactive({
                hour:new Date().getHours(),
                min: new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes(),
            })
            onMounted(() => {
                let i = store.state.backImg||localStorage.getItem("color")||5
                    $('html').css({'--backColor':store.state.colorList[i-1].backColor})  
                    $('html').css({'--color':store.state.colorList[i-1].color})
                store.commit("show",'')
                // const jwt = localStorage.getItem("jwt");
                // if(jwt){
                //     store.commit("updateToken",jwt)
                //     store.dispatch("getinfo",{
                //         success(){
                //             router.push({ name: "main" });
                //         },
                //         error(){
                //         }
                //     })
                // }
                time
                function time(){
                    vue.hour = new Date().getHours()
                    vue.min = new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes()
                }
                setInterval(time,1000)
                store.dispatch("connectToWebSocket", {
                    id: localStorage.getItem("id"),
                    name: localStorage.getItem("name"),
                    token: localStorage.getItem("jwt"),
                });
            })
            return {
                ...toRefs(vue)
            }
        }
    }

</script>

<style scoped>
    .time2{
        display: flex;
        justify-content: center;
        font-size:40px;
        color:#ffffff;
        margin-bottom: 20px;
        letter-spacing: 2px;
    }
    .time1{
        display: flex;
        justify-content: center;
        font-size:40px;
        color:grey;
        margin-bottom: 20px;
        letter-spacing: 2px;
    }
    .copyright{
        position:absolute;bottom:5px;width:100%;text-align:center
    }
    .copyright a,.copyright{
        font-size:14px;color:#b6b6b6;
        text-decoration: none;
        transition: .3s;
    }
    @media only screen and (max-width: 410px) {
        .copyright a,.copyright{
            font-size:10px;
        }
        .time{
            font-size:35px;
        }
      }
    .copyright a:hover{
        color:#dfe6e9;
    }
    .time {
		animation-name: focus-in-contract;
		animation-duration: 1s;
		animation-timing-function: ease;
		animation-delay: 0s;
		animation-iteration-count: 1;
		animation-direction: normal;
		animation-fill-mode: none;
			
		animation: focus-in-contract 1s ease 0s 1 normal none;
	}
	@keyframes focus-in-contract {
			
		0% {
			letter-spacing:1em;
			filter:blur(12px);
			opacity:0;
		}
		100% {
			filter:blur(0);
			opacity:1;
		}
	}



</style>
