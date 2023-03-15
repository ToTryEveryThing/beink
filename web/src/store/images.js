import $ from 'jquery'
import {success , error} from  '../utiles/message'
import config from '../utiles/config'
export default {
    state: {
        ImagesList:[]
    },
    actions: {
        getList(context){
            $.ajax({
                url:`${config.API_URL}/oss/getList/`,
                type:'get',
                data:{
                    keyPrefix:"background"
                },
                success(res){
                    if(res.code===1)
                    context.commit("getList",res.date);
                    console.log(res)
                },
             })
        },
        delete(context,value){
            $.ajax({
                url:`${config.API_URL}/oss/deleteImage/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + value.token
                },
                data:{
                    url:value.url,
                    keyPrefix:"background"
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
                url:`${config.API_URL}/oss/uploadImage/`,
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
                    value.success()
                },
                error(){
                    error("上传失败")
                    value.success()
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
