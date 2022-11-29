<template>

    <h1>hello world</h1>
    列表<br>
    <!-- :age-表达式  age-里面是串 -->
    <SonData :name="name"></SonData>
    <SonData :name="name"></SonData>
    <SonData name="我不会变"></SonData>
    <button @click="name++">自己自动+1</button>

    <h1 @click="hh" :style="{color:cc}" >{{hi}}</h1>



</template>


<script>
import SonData from "@/components/SonData.vue";
import {reactive,toRefs} from 'vue'
    export default{
        components:{
            SonData,
        },
        setup(){
            const vue  = reactive({
                name:1,
                hi:'Hello World',
                cc:'chocolate'
            })
            const hh = function(){
                if(vue.hi==='Hello World'){
                    vue.hi='世界 你好'
                    vue.cc="orange"
                }
                else {
                    vue.hi='Hello World'
                    vue.cc='chocolate'
                }
            } 
            return {
                ...toRefs(vue),
                hh
            }
        }
    }


</script>
