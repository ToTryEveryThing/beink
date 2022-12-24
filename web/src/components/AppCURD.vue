<template>
    <el-row class="row-bg" justify="space-around" :gutter="20">
        <el-col   :span="8" v-for="tag in localList" :key="tag.name">
            <el-tag
                @close="dele(tag.id)"
                class="mx-1"
                closable
                size="large"
                effect="plain"
                :type="tag.Tag">
                {{ tag.name }}
            </el-tag>
        </el-col>

        <el-col v-if="localList.length<=4"  :span="8" >
            <el-tag
            @click="isShow=true"
                style="cursor: pointer;"
                class="mx-1"
                size="large"
                type="primary">
                + New url
            </el-tag>
        </el-col>
      </el-row>
      <div class="isShow" v-if="isShow">
        <h4>添加网址</h4>
        <label>Name</label>
        <input type="text" autofocus="autofocus" v-model="name"><br>
       <label>Url</label>
        <input type="text" v-model="href">
        <div class="yesOrno">
            <button @click="hh" class="yes">确定</button>
            <button @click="nohh"  class="no">取消</button>
        </div>
    </div>
</template>
<script>
    import {  mapMutations , mapState} from 'vuex';
    import {nanoid} from 'nanoid'
export default {
    data(){
        return{
            href:'',
            name:'',
            isShow:false,
            icon:['#icon-yongquan','#icon-zhifeiji','#icon-qiqiu','#icon-zhifengche',
                '#icon-fengche','#icon-quqi','#icon-xigua','#icon-yinliao','#icon-bingqilin',
                '#icon-diqiuyi'],
        }
    },
    computed:{
        ...mapState(['localList'])
    },
    methods:{
        
        ...mapMutations(['dele']),
        hh(){
            if(this.href===''||this.name===''){
                alert('填完');
                return 
            }
            const ll = {
                id:nanoid(),
                href:this.href,
                name:this.name,
                icon:`${this.icon[Math.floor(Math.random()*10)]}`,
                Tag:'info',
            };
            this.$store.dispatch('changeList',ll)
            this.href=this.name=''
            this.isShow =! this.isShow
        }, 
        nohh(){
            this.isShow =! this.isShow
            this.href=this.name=''
        } ,
    },
    
}
</script>

<style scoped>
input{
    text-indent: 5px;
    outline:none;
    border:1px black solid;
    height: 18px;
    width: 180px;
}
input:focus{
    background-color: #884df059;
}
.isShow{
    position: absolute;
    border-radius: 5px;
    margin-bottom: 5px;
    box-shadow: 1px 1px 5px grey;
    width: 200px;
    background-color: white;
    padding: 10px;
    z-index: 999;
}
.yesOrno{
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    height: 25px;
}
.yes,.no{
    width:90px;
    border:none;
    transition: .2s;
    border-radius: 5%;
}
.no:hover{
    background-color: rgb(221, 156, 156);
}
.yes:hover{
    background-color: rgb(130, 130, 236);
    
}
</style>