<template>
    
    <div class="add">
        <button @click="sub">-</button>
        <span :style="{opacity}" >{{name}}</span>
        
        <button @click="add">+</button>
    </div>

    <input ref="fous" type="text" v-model="length"><br><br><br>
    <input type="text" v-model="width"><br><br>
    面积为：{{ji}}<br><br>
    <button @click="zhou">点击计算</button>周长为:{{per}}

    
    <button @click="reff">ref属性-点我获得数字</button>
    <hr>

    我是全局数据：<br>
    我是{{$store.state.name}},我今年{{$store.state.age}}了{{$store.state.sex}}人
    I can say:{{$store.state.list[0].say}}

</template>


<script>

    import {reactive,toRefs,computed,onMounted} from 'vue'
    
    export default{
        setup(){
            let vue = reactive({
                name:999,
                length:0,
                width:0,
                per:0,
                opacity:1
            })
            const add = function(){
                vue.name++
            }
            const sub = function(){
                vue.name--
            }
            const zhou = function(){
                vue.per = 2*(vue.length*1 + vue.width*1)
            }
            const ji = computed(()=>{
                return vue.length*vue.width*1
            })
            onMounted(() => {
                setInterval(()=>{
                    vue.opacity-=0.1
                    vue.name++
                    if(vue.opacity<=0)vue.opacity=1
                },200)
            })
            return {
                ...toRefs(vue),
                add,
                sub,
                zhou,
                ji,
            }
        }
        
    }
</script>

<style >
    .add{
        font-size:50px;
    }
</style>