<template>
    <div>
        <MyHeader :add="add"></MyHeader>

        <MyList :list="list"  :change="change" :shanchu="shanchu"></MyList>

        <MyFooter :list="list" :check="check" :overr="overr"></MyFooter>

    </div>
    
</template>

<script>
import MyHeader from "./component/MyHeader.vue";
import MyList from "./component/MyList.vue";
import MyFooter from "./component/MyFooter.vue";
    export default{
        components:{MyHeader,MyList,MyFooter},
        data() {
            return {
                list: [
                    { id: "001", name: "吃饭", isdone: false },
                    { id: "002", name: "喝酒", isdone: false },
                    { id: "003", name: "上网", isdone: true },
                ]
            };
        },
        methods:{
            // 添加
            add(e){
                this.list.unshift(e)
            },
            // 状态
            change(id){
                this.list.forEach((list)=>{
                    if(list.id===id)list.isdone = !list.isdone
                })
            },
            // 删除
            shanchu(id){
                this.list = this.list.filter((list)=>{
                    return list.id!==id
                })
            },
            //
            check(e){
                this.list.forEach((list)=>{
                    list.isdone = e
                })
            },
            // 清除完成的任务
            overr(){
                this.list = this.list.filter((list)=>{
                    return !list.isdone 
                })
            }
        }
    }
</script>


<style scoped>
    div{
        margin-left:100px;
        border: 1px grey solid;
    }
   
</style>