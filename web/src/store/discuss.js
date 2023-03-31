import $ from 'jquery'
import config from '../utiles/config'
import moment from 'moment/moment'
export default {
    state: {
        post_index:1,
        article_author:'',
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
                    console.log(res)
                  if(res.code===1){
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
        }
    },
    // 异步
    actions: {
       
    },

}

// 同步:发送一个请求,等待返回,然后再发送下一个请求
// 异步:发送一个请求,不等待返回,随时可以再发送下一个请求
