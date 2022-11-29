<template>
    <ul>
        <li v-for="i in localList " :key="i.id"> 
             <a :href="i.href" target="_self">
                <svg aria-hidden="true">
                    <use :xlink:href="i.icon"></use>
                </svg>
                <span>{{i.name}}</span>
             </a>
             <button @click="dele(i.id)">
                <svg  class="icon1" aria-hidden="true">
                    <use xlink:href="#icon-shanchu"></use>
                </svg>
             </button>
        </li>
        <div class="isShow" v-if="isShow">
            <h4>添加网址</h4>
            <label>Name</label>
            <input type="text" autofocus="autofocus" v-model="name"><br>
           <label>Url</label>
            <input type="text" v-model="href">
            <div class="yesOrno">
                <button @click="hh" class="yes">确定</button>
                <button @click="nohh"  class="no">取消</button>
            </div>
        </div>
        <div class="add" v-if="localList.length<=4" @click="isShow =! isShow">
            <svg  class="icon" aria-hidden="true">
                <use xlink:href="#icon-zengjia"></use>
            </svg>
        </div>
     </ul>
</template>

<script>
    import {nanoid} from 'nanoid'
    import {  mapState ,mapMutations} from 'vuex';
    export default{
        data() {
            return {
                href:'',
                name:'',
                isShow:false,
                icon:['#icon-yongquan','#icon-zhifeiji','#icon-qiqiu','#icon-zhifengche',
                '#icon-fengche','#icon-quqi','#icon-xigua','#icon-yinliao','#icon-bingqilin',
                '#icon-diqiuyi']
            }
        },
        computed:{
            ...mapState(['localList'])
        },
        methods:{
            hh(){
                if(this.href===''||this.name===''){
                    alert('填完');
                    return 
                }
                const ll = {id:nanoid(),href:this.href,name:this.name,icon:`${this.icon[Math.floor(Math.random()*10)]}`};
                this.$store.dispatch('changeList',ll)
                this.href=this.name=''
                this.isShow =! this.isShow
            }, 
            nohh(){
                this.isShow =! this.isShow
                this.href=this.name=''
            } ,
            ...mapMutations(['dele'])
        },
    }
</script>

<style scoped>
    *{
        padding: 0;
        margin: 0;
        border:none;
    }
    a use{
        position: absolute;
        width: 20px;
        height: 20px;
    }
    a svg{
        position: absolute;
        width: 20px;
        height: 20px;
        bottom: 25px;
    }
    .add{
        position: relative;
        width: 100px;
        height: 60px;
        transition: .3s;
        background-color:transparent;
        margin-left: 10px;
        text-align: center;
        line-height: 70px;
    }
    .add:hover{
        background-color: var(--color);
    }
    .icon{
        width: 50px;
        height: 30px;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }
    .icon1{
        right: 1px;
        top:1px;
        position: absolute;
        width: 12px;
        height: 12px;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }
    span{
        position: absolute;
        bottom: 5px;
        font-size: 12px;
        height: 18px;
        color:grey;
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        text-align: center;
    }
    .isShow{
        position: absolute;
        border-radius: 5px;
        margin-bottom: 5px;
        box-shadow: 1px 1px 5px grey;
        width: 200px;
        background-color: white;
        padding: 10px;
        z-index: 999;
    }
    h4{
        margin-bottom: 10px;
    }
    label{
        font-size:15px;
    }
    .yesOrno{
        margin-top: 10px;
        display: flex;
        justify-content: space-between;
        height: 25px;
    }
    .yes,.no{
        width:90px;
        border:none;
        transition: .2s;
        border-radius: 5%;
    }
    .no:hover{
        background-color: rgb(221, 156, 156);
    }
    .yes:hover{
        background-color: rgb(130, 130, 236);
        
    }
    li{
        list-style:none;
        position:relative;
        margin-left: 10px;;
    }
    li:hover{
        background-color: var(--color);
    }
    a{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100px;
        height: 60px;
        text-decoration: none;
        border-radius: 10px;
    }
    ul{
        display: flex;
        justify-content: center;
    }
    li  button{
        background-color: #dddddd;
        display: none;
        position: absolute;
        top: 0;
        right: 0;
        font-size: 30px;
        width: 30px;
    }
    li:hover > button {
        display: block;
    }
    table, td, th {
        border: 2px solid rgb(134, 134, 134);
    }
    input{
        text-indent: 5px;
        outline:none;
        border:1px black solid;
        height: 18px;
        width: 192px;
    }
    input:focus{
        background-color: #884df059;
    }
    li {
		animation-name: fade-in-top;
		animation-duration: 1s;
		animation-timing-function: ease;
		animation-iteration-count: 1;
		animation-direction: normal;
		animation-fill-mode: none;
		animation: fade-in-top 1s ease 0s 1 normal none;
	}
    li:nth-child(1){
        animation-delay: 0.1s;
    }
    li:nth-child(2){
        animation-delay: 0.15s;
    }
    li:nth-child(3){
        animation-delay: 0.2s;
    }
    li:nth-child(4){
        animation-delay: 0.25s;
    }
    li:nth-child(5){
        animation-delay: 0.3s;
    }
	@keyframes fade-in-top {
			
		0% {
			transform:translateY(-50px);
			opacity:0;
		}
		100% {
			transform:translateY(0);
			opacity:1;
		}
	}
</style>