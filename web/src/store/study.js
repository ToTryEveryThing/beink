import $ from 'jquery'
export default {
    state: {
        editableTabs:'',
    },
    mutations: {
        show(state){
            $.ajax({
                url:"https://so.beink.cn/user/admin/git/show/",
                type:'post',
                success(res){
                  if(res.code===1){
                    state.editableTabs = JSON.parse(res.date)
                  }
                  
                },
            })
        }
    },
    actions: {

    },

}
