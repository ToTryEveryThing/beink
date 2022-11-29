export const a ={
    data() {
        return {
            name:'Hello World',
            Name:'世界 你好'
        }
    },
}
export const b = {
    methods: {
        h(){
            alert('aiya  你干嘛')
        }
    },
}

// 放main.js中 即可全局使用
// import {a,b} from './mixin'
// createApp(App).use(store).mixin(a,b).mount('#app')