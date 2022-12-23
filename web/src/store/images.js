import $ from 'jquery'
import { ElMessage } from 'element-plus'
const open2 = (i) => {
    ElMessage({
      message: i,
      type: 'success',
    })
  }
const open4 = (i) => {
    ElMessage.error(i)
}
export default {
    state: {
        ImagesList:[]
    },
    actions: {
        getList(context){
            $.ajax({
                url:'https://so.beink.cn/oss/getList/',
                type:'get',
                success(res){
                    context.commit("getList",res);
                },
                error(){
                    open4("图片获取失败")
                }
             })
        },
        delete(context,value){
            $.ajax({
                url:'https://so.beink.cn/oss/deleteImage/',
                type:'delete',
                headers:{
                    Authorization:"Bearer " + value.token
                },
                data:{
                    url:value.url
                },
                success(res){
                    context.commit("getList",res)
                    open2("删除成功")
                },
                error(){
                    open4("删除失败")
                }
            })
        },
        upload(context,value){
            $.ajax({
                url:'https://so.beink.cn/oss/uploadImage/',
                type:'post',
                data: value.formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                headers:{
                    Authorization:"Bearer " + value.token
                },
                success(res){
                    context.commit("getList",res)
                    open2("上传成功")
                },
                error(){
                    open4("上传失败")
                }    
            })
        }
    },
    mutations: {
        getList(state,value){
            state.ImagesList = value
        }
    },
}
