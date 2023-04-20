import $ from 'jquery'
import config from '../utiles/config'
import moment from 'moment/moment'
export default {
    state: {
        post_index:1,//文章id
        article_author:'',
        article_title:'',
        up_status:0,
        all_up:0,
        content:[],
        count:0,
        discussStatus:[],
        discussId:[]
    },
    // 同步
    mutations: {
        updateDiscuss(state,value){
            state.post_index = value.index
            state.article_author = value.author
            state.article_title = value.title
            state.all_up = value.all_up
        },
        upupupup(state,value){
            if(value.status){
                state.content[state.content.indexOf(value.value)].up++
                state.discussStatus[value.value.id] = true
            }else{
                state.content[state.content.indexOf(value.value)].up--
                state.discussStatus[value.value.id] = false
            }
        },
        showDiscuss(state,value){
            $.ajax({
                url:`${config.API_URL}/user/discuss/show/`,
                type:'post',
                data:{
                    article_id:state.post_index,
                    page:value.page
                },
                success(res){
                  if(res.code===200){
                    state.discussId = []
                    state.count = res.count
                    state.content = res.data
                    state.content.forEach((i,index)=>{
                        state.content[index].date = moment(i.date).format('YYYY-MM-DD HH:mm')
                        state.discussId.push(i.id)
                    })
                    $.ajax({
                        url:`${config.API_URL}/user/discussstatus/`,
                        type:'post',
                        headers:{
                            Authorization:"Bearer " + localStorage.getItem("jwt")
                        },
                        data:{
                           ids:JSON.stringify({ids:state.discussId})
                        },
                        success(res){
                          state.discussStatus = res

                        },
                    })
                    
                  }

                },
            })
        },
        changeUp(state){
            $.ajax({
                url:`${config.API_URL}/user/article/up/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                   articleId:state.post_index,
                   status:state.up_status === 1 ? 0 : 1
                },
                success(){
                    state.up_status = state.up_status === 1 ? 0 : 1
                    state.all_up = state.all_up + ( state.up_status === 1 ? 1 : -1 )
                },
            })
        },
        getStatus(state){
            $.ajax({
                url:`${config.API_URL}/user/article/upstatus/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + localStorage.getItem("jwt")
                },
                data:{
                   articleId:state.post_index,
                   userName:localStorage.getItem("name")
                },
                success(res){
                    state.up_status = JSON.parse(res.data)
                },
            })
        }
    },
    // 异步
    actions: {
        changeUp(content){
            content.commit("changeUp")
        },
        getStatus(content){
            content.commit("getStatus")
        }
    },

}

// 同步:发送一个请求,等待返回,然后再发送下一个请求
// 异步:发送一个请求,不等待返回,随时可以再发送下一个请求
