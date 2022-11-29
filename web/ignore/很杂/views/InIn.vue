<template>
    <!-- 
        v-text v-html 是否解析标签的区别
        v-once 只会执行一次 之后数据不会变化
        v-bind:href 简写 :href
     -->
    <div class="in">
        <h1>hello {{name}}</h1>
        <button @click="hai">{{a}}</button>
        <h2>现在是：{{Time}}</h2>
        <h2 v-html="hello"></h2>

        <a :href="path">点我回首页</a>
        <p v-once>{{once}}</p>
        <p>{{once}}</p>
        <input type="text" v-model="once"><br>
        数组内容：
        <ul >
            <li v-for="i in arr" :key="i">{{i}}</li>
        </ul>
        对象内容：<br>
        <ul >
            <li v-for="(value,key,i) in obj" :key="i">{{key}}:{{value}}</li>
        </ul>

        





    </div>

</template>

<script>
    import {reactive,toRefs,onMounted} from 'vue'
    export default{
        setup(){
            const vue = reactive({
                name:'liming',
                a:true,
                hello:'<q>hello world</q>',
                once:'once',
                Time:new Date(),
                path:'/',
                arr:[1,2,5,4,7],
                obj:{
                    name:'liming',
                    age:15,
                    as:'ll',
                }
            })
            onMounted(() => {
                setInterval(function(){
                    vue.Time = new Date();
                },1000)
            })
            const hai =  function(){
                vue.a = !vue.a
            }
            return {
                ...toRefs(vue),
                hai
            }
        }
        
    }
 
</script>

<style scoped>
    ul{
        display: flex;
        flex-wrap: nowrap;
        justify-content: space-around;
    }
    h1{
        color:red;
    }
</style>