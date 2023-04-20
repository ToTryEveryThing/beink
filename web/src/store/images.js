import $ from 'jquery'
import {success , error} from  '../utiles/message'
import config from '../utiles/config'
export default {
    state: {
        ImagesList:[]
    },
    actions: {
        getList(context,value){
            $.ajax({
                url:`${config.API_URL}/oss/getList/`,
                type:'get',
                data:{
                    keyPrefix:value.keyPrefix
                },
                success(res){
                    if(res.code===200)
                    context.commit("getList",res.data);
                },
             })
        },
        delete(context,value){
            $.ajax({
                url:`${config.API_URL}/admin/oss/deleteImage/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + value.token
                },
                data:{
                    url:value.url,
                    keyPrefix:value.keyPrefix
                },
                success(res){
                    if(res.code===200){
                        context.commit("getList",res.data)
                        success(res.message)
                    }else{
                        error(res.message) 
                    }
                },
                error(){
                    error("删除失败")
                }
            })
        },
        upload(context,value){
            $.ajax({
                url:`${config.API_URL}/admin/oss/uploadImage/`,
                type:'post',
                data:value.formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                headers:{
                    Authorization:"Bearer " + value.token
                },
                success(res){
                    if(res.code===200){
                        context.commit("getList",res.data)
                        success(res.message)
                    }else{
                        error(res.message)
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
