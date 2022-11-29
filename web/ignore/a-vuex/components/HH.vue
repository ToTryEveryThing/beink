<template>

    <h2>你好 世界</h2>
    {{$store.state.name}}
    <button @click="HH(20)">{{age}}</button>岁了
    <h3>年龄放大为{{bigAge}}</h3>
    <!-- 


        修改数据大致流程
                 dispatch     commit
                 /      \    /     \
            通过方法 -- actions --  mutations --  atate    --
    Vuex -- 通过方法 -- 响应动作 --  操作数据  --  数据变化  --


     -->
</template>

<script>
    import {mapGetters,mapState,mapMutations} from 'vuex'
    // 由于一直使用$store.state 调用太麻烦 于是有了mapstate....
    //mapGatters 同理
    export default{
        data() {
            return {
            }
        },
        computed:{
            //对象写法 
            // ...mapState({age:'age'}) 名字相同但不能省略 
            //数组写法   名字相同能省略 
            ...mapState(['age']),
            ...mapGetters(['bigAge'])
        },
        methods: {
            
            
            // hi(){
                //利用store中的响应修改数据
                // this.$store.dispatch('hh',55)
                //.同理
                //...mapActions(['HH'])



                //如果不需要异步可以直接commit
                // this.$state.commit('HH',55)
                // @click="hi(20)" 直接传参数
                // ...mapMutations({HH:'HH'}),
                ...mapMutations(['HH'])

                // 同步方法调用一旦开始，调用者必须等到方法调用返回后，才能继续后续的行为。
                // 异步方法调用更像一个消息传递，一旦开始，方法调用就会立即返回，调用者就可以继续后续的操作。而，异步方法通常会在另外一个线程中，“真实”地执行着。整个过程，不会阻碍调用者的工作
            // }


        },
    }


    // import {reactive,toRefs} from 'vue'
    // import {useStore} from 'vuex'
    // export default{
    //     setup(){
    //         const store = useStore()
    //         const vue = reactive({
    //             name:'liming'
    //         })
    //         const hi = function(){
    //             store.dispatch('hh',66)
    //         }
    //         return {
    //             ...toRefs(vue),
    //             hi
    //         }
    //     }
</script>

<style scoped>
    h2{
        color:red;
    }

</style>