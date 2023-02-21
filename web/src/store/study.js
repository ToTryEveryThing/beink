import $ from 'jquery'
import config from '@/utiles/config'
export default {
    state: {
        editableTabs:'',
    },
    mutations: {
        show(state){
            $.ajax({
                url:`${config.API_URL}/user/admin/git/show/`,
                type:'post',
                data:{
                    name:"admin"
                },
                success(res){
                  if(res.code===1){
                    state.editableTabs = JSON.parse(res.date.git)
                  }
                },
            })
        }
    },
    actions: {

    },

}
