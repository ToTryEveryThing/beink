<template>
    <ul :class="[$store.state.background===''?'add1':'add2']">
        <li  v-for="i in localList " :key="i.id"> 
             <a :href="i.href" target="_self">
                <svg aria-hidden="true">
                    <use :xlink:href="i.icon"></use>
                </svg>
                <span>{{i.name}}</span>
             </a>
        </li>
     </ul>
</template>

<script>
    import {  mapState } from 'vuex';
    export default{
        data() {
            return {
            }
        },
        
        computed:{
            ...mapState(['localList'])
        },
        methods:{
            
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
    .add1 .add:hover{
        background-color: var(--color);
    }
    .add2 .add:hover{
        background-color: rgba(225,225,225,.2);
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
    .add1 span{
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
    .add2 span{
        position: absolute;
        bottom: 5px;
        font-size: 12px;
        height: 18px;
        color:#f7f7f7;
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        text-align: center;
    }
    h4{
        margin-bottom: 10px;
    }
    label{
        font-size:15px;
    }
    
    li{
        list-style:none;
        position:relative;
        margin-left: 10px;;
    }
    .add1 li:hover{
        background-color: var(--color);
    }
    .add2 li:hover{
        background-color: rgba(225,225,225,.2);
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
    @media only screen and (max-width: 410px) {
        a{
            width: 80px;
            height: 40px;
        }
        .add{
            display: none;
        }
      }
</style>