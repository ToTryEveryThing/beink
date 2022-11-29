<template>

        <div>
            <router-link to="/main/on">on</router-link>
        </div>
        <router-view></router-view>
</template>


<script>
    export default{
        name:'MainPage'
    }

</script>
    

<style scoped>
    div{
        background-color: rgb(255, 255, 255);
    }
</style>