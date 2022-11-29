<template>
   <h1  >{{name}}</h1>
</template>

<script>
    import {reactive,toRefs } from 'vue'
    export default{
        name:'SonData',
        // 接受的信息不能改
        props:['name'],//简单接受
        // props:{ 先声明类型
        //     name:String,
        //     age:Number
        // } ,
        // props:{
        //     name:{
        //         type:String,//类型
        //         required:true//必须传
        //     },
        //     age:{
        //         type:Number,
        //         default:99//如果没串  默认为99
        //     }
        // },
        setup(props){
            const vue = reactive({
                name:props.name,
            })
            return {
                ...toRefs(vue),
            }
        }
    }
   
</script>



