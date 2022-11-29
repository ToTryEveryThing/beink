<template >
    <input type="checkbox"  @click="checkk" :checked="successes===list.length && successes>0"> | 
    <span>已完成 {{successes}} 个</span> 全部 {{list.length}} 个
    <button @click="over">清除已完成任务</button>
</template>

<script>
    export default{
        props:['list','check','overr'],
        computed:{
            successes(){
                let sum = 0
                this.list.forEach((i)=>{
                    if(i.isdone===true)sum++
                })
                return sum;
            }
        },
        methods:{
            checkk(e){
                if(e.target.checked===true){
                    this.check(true)
                }else{
                    this.check(false)
                }
            },
            over(){
                this.overr()
            }
        }
    }
</script>

<style scoped> 
</style>