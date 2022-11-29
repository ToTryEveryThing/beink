<template>
    <div >
        <input type="text"  v-model="keyword">
        <button @click="search">提交</button>
    </div>
    <hr><hr>
    <br><br>
    <ul>
        <i class="card" v-for="i in user" :key="i.login">
            <a :href="i.html_url">
                <img :src="i.avatar_url" alt="">
            </a>
            <span>{{i.login}}</span>
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
        width: 600px;
        height: 800px;
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
                axios.get(`https://api.github.com/search/users?q=${this.keyword}`).then(
                response => {
                    this.user=response.data.items
                }, 
                error => {
                    console.log("失败", error.message);
                });
            }
        },
    }
    
</script>