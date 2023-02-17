export default {
    state: {
        post_index:1,
        post_name:'',
        post_title:''
    },
    mutations: {
        updateDiscuss(state,value){
            state.post_index = value.index
            state.post_name = value.name
            state.post_title = value.title
        }
    },
    actions: {
       
    },

}
