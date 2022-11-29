<template>
    <input type="text" placeholder="回车添加任务" @keyup.enter="submit">
</template>

<script>
    import {nanoid} from 'nanoid'
    export default{
        props:['add'], 
        methods: {
            submit(e){
                if(!e.target.value)return 
                const hh = {id:nanoid(),name:e.target.value,isdone:false}
                this.add(hh);
                e.target.value=''
            }
        },
    }
</script>

<style scoped>
    input{
        width: 300px;
        height: 30px;
        text-indent: 5px;
        font-size: 20px;
    }
</style>