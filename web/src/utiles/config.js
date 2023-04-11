import $ from 'jquery'
import {error} from './message'
const BASE_URL ='http://localhost:9090';
// const BASE_URL ='https://so.beink.cn';

export default {
  API_URL: `${BASE_URL}`,
  LOGIN_URL: `${BASE_URL}/login`,
}
$.ajaxSetup({
  timeout: 10000, // 设置超时时间为 10 秒
  dataType: 'json', // 指定数据类型为 JSON
  dataFilter: function(data, type) {
    // 对返回的数据进行过滤处理
    if (type === 'json') {
      if(data==="error"){
        return "";
      }
      let res = JSON.parse(data)
      if(res.code===-1){
        error(res.msg)
        return res;
      }
    }
    return data
  },
});

