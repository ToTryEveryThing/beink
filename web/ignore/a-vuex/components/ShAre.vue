
<template>

    <input type="text" placeholder="请输入名字" v-model="name">
    <button @click="add">添加</button>
    <ul>
        <li v-for="i in list" :key="i.id">{{i.name}}</li>

    </ul>

</template>


<script>
    import {mapState} from 'vuex'

    export default{
        data() {
            return {
                name:''
            }
        },
        computed:{
            ...mapState(['list'])
        },
        methods:{
            // ...mapMutations(['add'])
            add(){
                const obj = {id:'666',name:this.name}
                this.$store.commit('add',obj)
                this.name=''
            }
        }
    }



</script>