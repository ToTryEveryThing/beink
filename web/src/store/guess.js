export default {
    state: {
        socket:null,
        is_matching:false,
        match_status:false,
        show_match:true,
        match_tip:'请出拳',
        my_name:'',
        ta_name:'',
        my_score:0,
        ta_score:0,
        my_choice:'',
        ta_choice:'',
        is_click:true
    },
    mutations: {
        updateALL(state,value){
            state.my_score = value.my_score
            state.ta_score = value.ta_score
            state.match_tip = "本局结果"
            setTimeout(()=>{
                state.match_tip = "请出拳"
                state.is_click = true
            },2000)
        },
        updateSocket(state,value){
            state.socket = value
        },
        up(state,value){
            state.my_name = value
        },
        match(state){
            state.is_matching = !state.is_matching
            console.log(state.is_matching)
        },
        update(state,value){
            state.ta_name = value.ta_name
            state.match_status = true
            setTimeout(()=>{
                state.show_match = false
            },1000)
        },
        logoutt(state){
            state.is_matching=false
            state.match_status=false
            state.show_match=true
            state.match_tip='请出拳'
            state.is_click=true
            state.my_score=0
            state.ta_score=0
        }
    },
    actions: {
       
    },

}
