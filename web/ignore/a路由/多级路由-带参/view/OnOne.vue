<template>
    <h1>woshi on</h1>
    <div>
        <ul>
            <li v-for="i in list " :key="i.id">
                <!-- 路由跳转 挟带参数 -->
                <!-- <router-link :to="`/main/on/detail?id=${i.id}&name=${i.name}`">{{i.name}}</router-link>  -->
                
                <!-- 方法二 -->
                <router-link :to="{
                    name:'hhh' //如果名字  可以用名字  简写路径
                    //path:'/main/on/detail',
                    query:{
                        id:i.id,
                        name:i.name
                    }
                }">
                {{i.name}}                    
                </router-link> 
                
            </li>
        </ul>
        <hr>
        <router-view></router-view>
    </div>
</template>

<script>
    export default{
        name:'OnOne',
        data() {
            return {
                list:[
                    {id:'01',name:'liming'},
                    {id:'02',name:'lihuas'},
                    {id:'03',name:'hhh'}
                ]
            }
        },
    }

</script>


<style>

    a{
        display: block;
        font-size: 30px;
        color:rgb(209, 174, 110);
        text-decoration: none;
     }

</style>