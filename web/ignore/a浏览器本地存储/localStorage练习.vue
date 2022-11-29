<template>
    <h1>Hello Worlds</h1>
    <tr>
        <td>href:</td>
        <td><input type="text" v-model="href"></td>
    </tr>
    <tr>
        <td>name:</td>
        <td><input type="text" v-model="name"></td>
    </tr>
    <button @click="hh">提交</button>
    <ul>
       <li v-for="i in list " :key="i.id">
            <a :href="i.href">{{i.name}}</a>
            <button @click="dele(i.id)">X</button>
       </li>
    </ul>
</template>

<script>
    import {nanoid} from 'nanoid'
    export default{
        data() {
            return {
                href:'',
                name:'',
                list:JSON.parse(localStorage.getItem('list')) || []
            }
        },
        methods:{
            hh(){
                if(this.href===''||this.name===''){
                    alert('填完');
                    return 
                }
                // 只留5个
                if(this.list.length>4){
                    this.list.pop()
                }
                const ll = {id:nanoid(),href:this.href,name:this.name};
                this.list.unshift(ll)
                this.href=''
                this.name=''
            },  
            dele(id){
                this.list = this.list.filter((list)=>{
                    return list.id!==id
                })
            }
        },
        watch:{
            list:{
                deep:true,
                handler(value) {
                    localStorage.setItem('list',JSON.stringify(value))
                }
            }
        }
    }
</script>
<style scoped>

    li{
        list-style:none;
        position:relative;
        margin-left: 10px;;
    }
    a{
        display: block;
        width: 100px;
        height:100px;
        background-color: rgb(240, 189, 96);
        box-shadow: 1px 1px  10px grey;
        font-size:30px;
        text-align: center;
        text-decoration: none;
        color:chocolate;
    }
    a:hover{
        box-shadow: 3px 2px  10px grey;
    }
    li button{
        border:none;
        position:absolute;
        top: 0;
    }
    ul{
        display: flex;
    }
    table, td, th {
        border: 2px solid rgb(134, 134, 134);
    }
    input{
        outline:none;
        border:none;
    }
      
</style>