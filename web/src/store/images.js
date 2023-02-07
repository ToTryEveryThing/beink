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
                    if(res.code===1)
                    context.commit("getList",res.date);
                },
             })
        },
        delete(context,value){
            $.ajax({
                url:'https://so.beink.cn/oss/deleteImage/',
                type:'post',
                headers:{
                    Authorization:"Bearer " + value.token
                },
                data:{
                    url:value.url,
                },
                success(res){
                    if(res.code===1){
                        context.commit("getList",res.date)
                        success("删除成功")
                    }else{
                        error("删除失败") 
                    }
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
                    if(res.code===1){
                        context.commit("getList",res.date)
                        success("上传成功")
                    }else{
                        error("上传失败")
                    }
                    
                   
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
