<template>
    <el-row justify="center" class="oonoono">
        <el-col :span="15" v-if="!editShow">
                <el-card shadow="always" >
                    <el-col :span="24">
                        <div class="bgggg">
                            <div class="author">
                                <el-avatar :size="50" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
                                <div class="preson">
                                    {{ name }} 
                                    <el-button @click="changeStatus" v-if="guanzhu==0" size="small" type="danger">关注</el-button> 
                                    <el-button @click="changeStatus" v-else size="small" type="primary">已关注</el-button>
                                </div> 
                                <div  @click="adddd" class="addd" v-if="!editShow&&isisALL">
                                    <!-- 添加 -->
                                    <svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" viewBox="0 0 24 24" fill="none" stroke="#0984e3" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus-square"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="12" y1="8" x2="12" y2="16"></line><line x1="8" y1="12" x2="16" y2="12"></line></svg>
                                </div>
                            </div>
                        </div>
                    </el-col> 
                    <el-col :span="24" class="oonoono">
                        <el-row :gutter="20" justify="center">
                            <el-col :span="18"  v-for="i  in content"  :key="i.id"  class="animate__animated  animate__fadeIn" >
                                <el-card  shadow="hover" style="margin-top:20px;" > 
                                    <template #header>
                                        <div class="card-header">
                                        <span>
                                            <el-button text type="info" bg>
                                                {{ i.title }}
                                            </el-button>
                                        </span>
                                        <el-button-group  class="ml-4">
                                            <!-- <el-button type="primary" @click="view(i.id)" >
                                                <el-icon><View /></el-icon>
                                            </el-button> -->
                                            <el-button type="primary" v-if="isisALL" @click="Edit(i)" >
                                                <el-icon >
                                                    <Edit />
                                                </el-icon>
                                            </el-button>
                                            <el-button type="primary" v-if="isisALL" @click="Delete(i)">
                                                <el-icon >
                                                    <Delete />
                                                </el-icon>
                                            </el-button>
                                        </el-button-group>
                                        </div>
                                    </template>
                                    <el-row @click="view(i.id)" style="cursor: pointer;">
                                        <el-col :span="6">
                                        <el-statistic title="Up" :value="i.up" />
                                        </el-col>
                                        <el-col :span="6">
                                        <el-statistic title="Views" :value="i.views" />
                                        </el-col>
                                        <el-col :span="6">
                                        <el-statistic title="Discuss" :value="i.discuss">
                                        </el-statistic>
                                        </el-col>
                                    </el-row>
                                </el-card>
                            </el-col>
                        </el-row>
                    </el-col>
                </el-card>
        </el-col>
        <el-input v-if="editShow" v-model="title">
            <template #prepend>标题</template>
            <template #append><el-switch v-model="isshow" /></template>
        </el-input>
        <v-md-editor  v-if="editShow" v-model="editContent" 
            :height="editorHeight"
            :default-show-toc="true"
            left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save back" 
            right-toolbar="preview toc sync-scroll fullscreen"
            :toolbar="toolbar"
            @save="save()" 
            :disabled-menus="[]"
            @upload-image="handleUploadImage"
        />
    </el-row>
</template>

<script>
import { nanoid } from 'nanoid'
import { onMounted, reactive, toRefs } from 'vue';
import config from '@/utiles/config';
import { useStore } from 'vuex'
import { useRoute } from 'vue-router';
import { success, warning } from '@/utiles/message';
import $ from 'jquery';
import router from '@/router';
export default {
    setup(){
        const store = useStore()
        const route = useRoute()
        const vue = reactive({
            guanzhu: 0,
            isisALL: false,
            name:'',
            content:[],
            editorHeight:"900px",
            title:'',
            ad:false,
            isshow:false,
            all:[],
            toolbar: {
            back: {
                title: '取消编辑',
                icon: 'v-md-icon-undo',
                action() {
                    vue.editShow = false
                    vue.ad = false
                },
            },
          },
            editShow:false,
            editContent:""
        })
        const show = ()=>{
            $.ajax({
                url:`${config.API_URL}/user/article/showone/`,
                type:'post',
                data:{
                    post:vue.name
                },
                success(res){
                    if(res.code===200){
                        vue.content = res.data.filter(i => i.isshow==true || i.post===store.state.account)
                    }
                    else{
                        console.log(res)
                        // alert(res.message)
                        // router.go(-1) //后退1步
                    }
                }
            })
        }
        onMounted(()=>{
            vue.name = route.params.name
            console.log(vue.name)
            show()
            setTimeout(()=>{
                vue.isisALL = (store.state.account===vue.name)
                getguanzhuStatus()
            },1000)

            // vue.editorHeight = document.body.clientHeight  + "px"
            // console.log(vue.editorHeight)
        })
        const Delete = i =>{
            if(confirm("确定吗"))
            $.ajax({
                url:`${config.API_URL}/user/article/delete/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                    id:i.id,
                    post:i.post,
                },
                success(res){
                    if(res.code===200)
                    {
                        success("删除成功")
                        vue.editShow = false
                        changeContent(i)
                    }
                },
             })
        }

        const Edit = i =>{
            vue.editContent = i.content
            vue.title = i.title
            vue.all = i
            vue.editShow = true
            vue.isshow = i.isshow
        }

        const changeContent = i =>{
            // let filteredKeys = keysArray.filter(key => key.length > 5);
            vue.content = vue.content.filter(key=>key!=i)
        }

        const ADD = ()=>{
            if(vue.title==='')return 
            if(vue.editContent==='')return
            $.ajax({
                url:`${config.API_URL}/user/article/add/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                    content:vue.editContent,
                    title:vue.title
                },
                success(res){
                    if(res.code===200)
                    {
                        success("添加成功")
                        vue.editShow = false
                        vue.ad = false
                        show()
                    }
                },
             })
        }

        const  save = ()=>{
            if(vue.ad){
                ADD()
                return
            }
            $.ajax({
                url:`${config.API_URL}/user/article/edit/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                    id:vue.all.id,
                    post:vue.all.post,
                    content:vue.editContent,
                    title:vue.title,
                    show:vue.isshow
                },
                success(res){
                    if(res.code===200)
                    {
                        success("修改成功")
                        vue.editShow = false
                        for(let i=0;i<vue.content.length;i++){
                            if(vue.content[i].id===vue.all.id){
                                vue.content[i].title = vue.title
                                vue.content[i].content = vue.editContent
                                break
                            }
                        }
                    }
                },
             })
        }

        const getguanzhuStatus =()=>{
            console.log("is_login",store.state.is_login,"gg")
            // if(store.state.is_login)
            $.ajax({
                url:`${config.API_URL}/follow/getstatus/`,
                type:'get',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                    followerId: store.state.id,
                    followingId: null,
                    name: vue.name
                },
                success(res){
                    console.log("hhhhhhhhhhhh ")
                    if(res.code===200){
                        vue.guanzhu = res.data
                        console.log(vue.guanzhu)
                    }
                },
             })
        }

        const changeStatus = ()=>{
            if(store.state.is_login)
            $.ajax({
                url:`${config.API_URL}/follow/changefollow/`,
                type:'get',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                    followerId: store.state.id,
                    followingId: null,
                    change: vue.guanzhu,
                    name: vue.name
                },
                success(res){
                    if(res.code===200){
                        vue.guanzhu = vue.guanzhu == 1 ? 0 :1
                    }
                },
             })
        }

        const view = i =>{
            router.push(`/article/${i}/`)
        }

        const handleUploadImage = (event, insertImage, files) =>{
            if(store.state.role==="use"){
                warning("没有权限")
                return 
            }
            let e = files[0]
            let id = nanoid()
            let nname = e.name
            var newFile = new File([e] ,id + nname.substr(nname.indexOf("."))  , { type: e.type });
            // console.log(newFile)
            // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
                    var formData = new FormData(); 
                        formData.append('file', newFile); 
                    formData.append("keyPrefix","study")
                    store.dispatch("upload",{
                        token:store.state.token,
                        formData:formData,
                        success(){
                        insertImage({
                            url: 'https://images.beink.cn/'+"study/" + id + nname.substr(nname.indexOf(".")),
                            desc: 'study',
                            // width: 'auto',
                            // height: 'auto',
                        });
                        }
                    })
        }
        const adddd = ()=>{
            vue.ad = true
            vue.add = true
            vue.editShow = true
            vue.title = ''
            vue.editContent = ''
        }
        return {
            ...toRefs(vue),view,handleUploadImage,
            Delete,Edit,save,adddd,changeStatus
        }
    }
}
</script>

<style scoped>
.oonoono{
    min-width:1024px;
    max-width:100%;
}
.bgggg{
    position: relative;
    background-image: url('https://images.beink.cn/study/4jNcaeXNl5w-iL8vYujbX.png');
    height: 200px;
    width: 100%;
}
.author{
    padding: 20px;
    position: absolute;
    height: 40px;
    width: 100%;
    bottom: 0;
}
.author .preson{
    color: rgb(255, 255, 255);
    font-family:Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif;
    position: absolute;
    padding-left: 70px;
    top: 20px;
}

.card-header .el-button-group{
    float:right;
}
.addd{
    position: absolute;
    top: 0px;
    right: 20px;
    padding: 20px;
    cursor: pointer;
}
</style>