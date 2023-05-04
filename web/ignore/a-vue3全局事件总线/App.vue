<template>
    <!-- 简写 @myself -->
    <div>
        <MyList  @click="hh"></MyList>
    </div>
    
    <hr>
    收到的数据：<h3>{{name}}</h3>
</template>

<script>
    import MyList from './component/MyList'
    export default{
        components:{MyList},
        data() {
            return {
                name:''
            }
        },
        methods: {
            hh(){
                alert("hh")
            }
        },
        mounted(){
            // 接受事件
            this.$bus.on('myself',data=>{
                this.name = data
            })
        }
    }
</script>

<style scoped> 
    div{
        background-color: red;
    }
</style>