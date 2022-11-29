<template>

   <h1>{{name}}</h1>
   <h2>{{Name}}</h2>

   <button @click="send">点我发送数据</button>


   <button @click="unbind">点我解除绑定</button>

</template>

<script>
    export default{
        data() {
            return {
                name:'Hello World',
                Name:'世界 你好'
            }
        },
        methods: {
            send(){
                //发送事件
                this.$bus.emit('myself',this.name)
            },
            unbind(){
                // this.$off('myself')
                // this.$off(['xxx','yyy']) 解除多个事件
                // 解除事件
                this.$bus.off('myself')
            }
        },
    }

</script>

<style>


</style>