<template>

    <ul>
        <MyItem :list="list"  :change="change" :shanchu="shanchu"></MyItem>
    </ul>
    
</template>

<script>
import MyItem from './MyItem.vue';

    
    export default{
    components: { MyItem},
    props:['list','change','shanchu']
}


</script>