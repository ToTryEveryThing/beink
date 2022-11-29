<template>
    <h1>Hello World</h1>
    当前和为：<h2>{{sum}}</h2>
    <button @click="sum++">点我+1</button>
    <hr>
    <h2>{{msg}}</h2>
    <button @click="msg+='!'">点我+！</button>
    <hr>
    <h3>{{vue.age}}</h3>
    <button @click="vue.age++">age++</button>

</template>

<script>
import {ref,watch,reactive ,watchEffect} from 'vue'
    export default{
        //vue2写法
        // watch:{
        //     // sum(){一般写法
        //     //     console.log('我改变了')
        //     // }
        //     sum:{
        //         immediate:true,//立即执行
        //         deep:true,//深度监视
        //         handler(newValue,oldValue){
        //             console.log('我改变了',newValue,oldValue)
        //         }
        //     }
        // },
        setup(){
            let sum =  ref(0)
            let msg = ref('hh')
            // 监视ref的数据
            // ['sum'] 多个数据
            watch(sum,(newValue,oldValue)=>{
                console.log('我改变了',newValue,oldValue)
            },{immediate:true})


            let vue = reactive({
                age:20
            })
            // watch监视reactive的某个数据
            watch(()=>vue.age,(newValue,oldValue)=>{
                console.log('hhh',newValue,oldValue)
            })

            // 这里面用到了谁 就监视谁
            watchEffect(()=>{
                let x = vue.age
                console.log('nnnnn',x)
            })
            return {
                sum,
                msg,vue
            }
        }
    }

</script>

<style>



</style>