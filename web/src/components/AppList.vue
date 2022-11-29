<template>
    <div class="open">
        <ul>
            <li v-for="i in list1" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
        </ul>
        <ul>
            <li v-for="i in list2" :key="i.name">
                <a rel="nofollow" :href="i.href" target="_self">
                    <svg aria-hidden="true">
                        <use :xlink:href="i.icon"></use>
                    </svg>
                    <p>{{i.name}}</p>
                </a>
            </li>
        </ul>
    </div>

</template>

<script>
    import {reactive,toRefs} from 'vue'
    export default{
        setup(){

            const vue = reactive({
                list1:[
                    {href:'https://fanyi.baidu.com/?aldtype=16047#auto/zh',icon:'#icon-fanyi',name:'翻译'},
                    {href:'https://www.aliyundrive.com/drive',icon:'#icon-wangpan',name:'网盘'},
                    {href:'https://blog.csdn.net',icon:'#icon-boke',name:'博客'},
                    {href:'https://www.bilibili.com/',icon:'#icon-bilibili',name:'bilibil'},
                ],
                list2:[
                    {href:'https://cn.vuejs.org/guide/introduction.html',icon:'#icon-Vue',name:'Vue'},
                    {href:'https://gitee.com/mi_shu',icon:'#icon-gitee',name:'gitee'},
                    {href:'https://www.w3school.com.cn/index.html',icon:'#icon-w3school',name:'w3school'}
                ]
            })
            return {
                ...toRefs(vue)
            }
        }
    }

</script>

<style scoped>
    *{
        padding: 0;
        margin: 0;
        border: none;
        text-decoration: none;
    }
    .open{
        margin-top: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    li{
        list-style: none;
        width: 120px;
        height: 80px;
        margin-left: 10px;
        margin-right: 10px;
    }
    ul{
        position:relative;
        display: flex;
        box-shadow:7px  7px  20px #9f9d9d1f;
        padding: 10px;
        margin-top: 10px;
    }
    a{
        position: relative;
        display: block;
        width: 120px;
        height: 80px;
        display: flex;
        justify-content: center;
        transition: .5s;
        border-radius: 5%;
    }
    a:hover{
        background-color: var(--color);
    }
    use{
        position: absolute;
        width: 30px;
        height: 30px;
    }
    svg{
        margin-top: 10px;
        position: absolute;
        width: 30px;
        height: 30px;
    }
    a p{
        position:absolute;
        bottom: 0;
        left: 50%;
        transform: translate(-50%);
        color: #2d3436;
        margin-bottom: 15px;
        font-size: 13px;
    }
</style>