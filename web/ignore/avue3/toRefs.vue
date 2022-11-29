<template>
    <h1>Hello World</h1>
    <h2>{{name}}</h2>
    <h3>{{age}}</h3>
    <button @click="age++">age++</button>
    <button @click="add">out</button>
</template>
<script>
import {reactive,toRefs} from 'vue'
    export default{

        setup(){
            let vue  = reactive({
                    name:'李明',
                    age:15,
                    job:{
                        s:{
                            salay:20
                        }
                    }
            })
            let add = function(){
                console.log(vue.name)
            }
            return {
                //  toRef 用法
                // name:toRef(vue,'name'),
                // age:toRef(vue,'age'),
                // torefs 用法
                ...toRefs(vue),
                add
            }
        }


    }

</script>