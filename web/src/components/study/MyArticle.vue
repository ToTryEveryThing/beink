<template>
    <el-row justify="center">
        <el-col :span="18" v-if="!editShow">
            <el-row :gutter="20">
                <el-col :span="12"  v-for="i  in content" :key="i.id" class="animate__animated  animate__fadeIn" >
                    <el-card shadow="hover" style="margin-top:20px;" > 
                        <template #header>
                            <div class="card-header">
                              <span>
                                <el-button text type="info" bg>
                                    {{ i.title }}
                                </el-button>
                              </span>
                              <el-button-group class="ml-4">
                                <el-button type="primary" @click="view(i.id)" >
                                    <el-icon><View /></el-icon>
                                </el-button>
                                <el-button type="primary" @click="Edit(i)" >
                                    <el-icon >
                                        <Edit />
                                      </el-icon>
                                </el-button>
                                <el-button type="primary" @click="Delete(i)">
                                    <el-icon >
                                        <Delete />
                                      </el-icon>
                                </el-button>
                              </el-button-group>
                            </div>
                        </template>
                        <el-row>
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
        <el-input v-if="editShow" v-model="title">
            <template #prepend>标题</template>
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
    <div  @click="adddd" class="addd" v-if="!editShow">
        <!-- 添加 -->
        <svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" viewBox="0 0 24 24" fill="none" stroke="#0984e3" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus-square"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="12" y1="8" x2="12" y2="16"></line><line x1="8" y1="12" x2="16" y2="12"></line></svg>
    </div>
</template>

<script>
import { nanoid } from 'nanoid'
import { onMounted, reactive, toRefs } from 'vue';
import config from '@/utiles/config';
import { useStore } from 'vuex'
import { success, warning } from '@/utiles/message';
import $ from 'jquery';
import router from '@/router';
export default {
    setup(props){
        const store = useStore()
        const vue = reactive({
            content:props.content,
            editorHeight:"500px",
            title:'',
            ad:false,
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
                    post:localStorage.getItem("name")
                },
                success(res){
                    if(res.code===1){
                        vue.content = res.date
                    }
                }
            })
        }
        onMounted(()=>{
            show()
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
                    if(res.code===1)
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
            vue.editorHeight = document.body.clientHeight  + "px"
        }
        const changeContent = i =>{
            // let filteredKeys = keysArray.filter(key => key.length > 5);
            vue.content = vue.content.filter(key=>key!=i)
            console.log(vue.content)
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
                    if(res.code===1)
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
                    title:vue.title
                },
                success(res){
                    if(res.code===1)
                    {
                        success("修改成功")
                        vue.editShow = false
                        console.log(vue.all)
                        for(let i=0;i<vue.content.length;i++){
                            if(vue.content[i].id===vue.all.id){
                                console.log("一样呀",vue.content[i].id)
                                vue.content[i].title = vue.title
                                vue.content[i].content = vue.editContent
                                break
                            }
                        }
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
            Delete,Edit,save,adddd
        }
    }
}
</script>

<style scoped>
.card-header .el-button-group{
    float:right;
}
.addd{
    position: absolute;
    top: 0px;
    right: 0px;
    cursor: pointer;
}
</style>