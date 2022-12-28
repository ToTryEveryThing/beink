import $ from 'jquery'
import {success , error} from  '../utiles/message'
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
                    error("图片获取失败")
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
                    url:value.url,
                    account:value.account
                },
                success(res){
                    context.commit("getList",res)
                    success("删除成功")
                },
                error(){
                    error("删除失败")
                }
            })
        },
        upload(context,value){
            $.ajax({
                url:'https://so.beink.cn/oss/uploadImage/',
                type:'post',
                data:value.formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                headers:{
                    Authorization:"Bearer " + value.token
                },
                success(res){
                    context.commit("getList",res)
                    success("上传成功")
                },
                error(){
                    error("上传失败")
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
