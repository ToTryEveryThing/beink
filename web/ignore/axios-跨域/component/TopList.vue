<template>

    <!-- 
        1.cors  后端来写 （简单）
        2.代理  前端写 
     -->
    <div >
        <input type="text"  v-model="keyword">
        <button @click="search">提交</button>
    </div>
    <hr><hr>
    <br><br>
    <ul>
        <i class="card" v-for="i in user" :key="i.id">
            <a :href="i.html_url">
                <img :src="i.avatar_url" alt="">
            </a>
            <span>{{i.id}}</span>
        </i>
    </ul>
    

</template>

<style>
    .card{
        position:relative;
        height: 100px;
        width: 100px;  
        margin-left: 10px;
    }
    .card span{
        position: absolute;
        bottom: -20px;
        left:50%;
        transform:translate(-50%);    
    }
    img{
        width: 100px;
    }
    li{
        list-style: none;
    }
    ul{
        display: flex;
        width: 700px;
        height: 700px;
        flex-wrap: wrap;
        justify-content: space-around;
    }
    body{
        display: flex;
        align-content: center;
        justify-content: space-around;
    }
    input{
        margin-left:100px;
        margin-top: 10px;
        height: 20px;
        width:300px;
        border:1px solid grey;
        text-indent: 3px;
    }
    button{
        border:none;
        height: 20px;
    }
</style>

<script>
    import axios from 'axios'
    export default{
        data() {
            return {
                keyword:'',
                user:[]
            }
        },
        methods: {
            search(){
                // // https://api.github.com/search/users?q=
                // // 
                // axios.get(`https://v.api.aa1.cn/api/qqimg/index.php?qq=${this.keyword}`).then(
                // response => {
                //     console.log(response.data)
                //     this.user=response.data
                // }, 
                // error => {
                //     console.log("失败", error.message);
                // });
                axios
                    .get(`http://localhost:8080/students`)
                    .then(response=>{console.log(response.data)})
                    .catch(error=>{console.log(error)})

            }
        },
    }
    
</script>