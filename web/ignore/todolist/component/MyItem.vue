<template>
   
        <li v-for="i in list" :key="i.id">
            <input type="checkbox" :checked="i.isdone" @click="ordone(i.id)">  | 
            <span>{{i.name}}</span>
            <button @click="btn(i.id)">删除</button>
        </li>
</template>


<script>
    export default{
        props:['list','change','shanchu'],
        methods:{
            ordone(i){
                this.change(i)
            },
            btn(i){
                if(confirm("确定删除吗")){
                    this.shanchu(i)
                }
            }
        }
    }
</script>

<style scoped>
    li{
        list-style: none;
        border: 1px grey solid;
        width: 300px;
        height: 30px;
        font-size:20px;
    }
    button{
        display: none;
        background-color: red;
        border: none;
        float: right;
        width:80px;
        color:white;
    }
    li:hover button{
        display: block;
    }
    li:hover{
        background-color: #b2b2b2;
    }
</style>