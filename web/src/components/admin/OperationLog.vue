<template>
  <el-card>
    <el-row>
      <el-col :span="5">
        <el-input v-model="Result" placeholder="Please input">
          <template #prepend>Result</template>
        </el-input>
      </el-col>
      <el-col :span="5">
        <el-input v-model="IP" placeholder="Please input">
          <template #prepend>IP</template>
        </el-input>
      </el-col>
      <el-col :span="12">
        <el-button type="primary" @click="show">查询</el-button>
        <el-button type="info" @click="reback">重置</el-button>
        <el-button type="primary" @click="deleteSuccess('200')">删除成功日志</el-button>
        <el-button type="warning" @click="deleteSuccess('-1')">删除异常日志</el-button>
        <el-button type="danger" @click="deleteSuccess('0')">删除失败日志</el-button>
      </el-col>
    </el-row>
    <el-table :data="table" border  style="width: 100%">
      <el-table-column prop="id" label="ID" width="180" />
      <el-table-column prop="username" label="Name" width="180" />
      <el-table-column prop="method" label="Method" />
      <!-- <el-table-column prop="params" label="Params" /> -->
      <el-table-column prop="ip" label="IP" />
      <el-table-column prop="createDate" label="createDate" />
      <el-table-column label="Result">
        <template #default="scope">
          <el-tag type="primary"   v-if="scope.row.result === '200'">{{ scope.row.result }} </el-tag>
          <el-tag type="danger" v-else-if="scope.row.result === '400'">错误的请求</el-tag>
          <el-tag type="warning" v-else-if="scope.row.result === '405'">方法错误</el-tag>
          <el-tag type="info" v-else-if="scope.row.result === '401'">没有权限</el-tag>
          <el-tag v-else type="danger" color="red">{{ scope.row.result }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="Type" />
      <el-table-column prop="url" label="Url" />
    </el-table>

    <el-pagination
    @current-change="handleCurrentChange"
      background  
       @next-click="ppage(1)" 
       @prev-click="ppage(-1)" 
      large
      layout="prev, pager, next"
      :total="total"
    />

    <table border="1">
      <tr>
        <td>username</td>
        <td>result</td>
        <td>ip</td>
        <td>url</td>
      </tr>
      <tr>
        <td>
          <li v-for="(value,key) in username"  :key="key">
            {{ key }} -- {{ value }}次
          </li>
        </td>
        <td>
          <li v-for="(value,key) in result"  :key="key">
            {{ key }} -- {{ value }}次
          </li>
        </td>
        <td>
          <li v-for="(value,key) in ip"  :key="key">
            {{ key }} -- {{ value }}次
          </li>
        </td>
        <td>
          <li v-for="(value,key) in url"  :key="key">
            {{ key }} -- {{ value }}次
          </li>
        </td>
      </tr>
    </table>

    <Limittt></Limittt>

  </el-card>
</template>

<script>
import $ from 'jquery'
import config from '@/utiles/config'
import { onMounted, reactive, toRefs} from 'vue'
import { useStore } from 'vuex';
import moment from 'moment/moment'
import Limittt from '@/components/admin/LimitUser.vue'
import { success } from '@/utiles/message';
export default{

  components:{Limittt},
  setup(){


    const store = useStore();
    const vue = reactive({
      table:[],
      total:0,
      page:1,
      username:{},
      result:{},
      ip:{},
      url:{},
      IP:"",
      Result:""
    })

    const reback = ()=>{
      vue.IP = ""
      vue.Result = ""
      show()
    }

    const deleteSuccess = (i)=>{
      $.ajax({
          url:`${config.API_URL}/admin/log/delete/${i}`,
          type:'delete',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          success(res){
            console.log(res,i)
            if(res.code===200){
              success(res.message)
              show()
            }
          }
      })
    }

    const show = ()=>{
      $.ajax({
          url:`${config.API_URL}/admin/log/`,
          type:'get',
          headers:{
              Authorization:"Bearer " + store.state.token
          },
          data:{
            page:vue.page,
            ip:vue.IP,
            result:vue.Result
          },
          success(res){
            if(res.code===200){
              console.log(res)
              vue.total = res.count
              vue.table = res.data
              vue.username = res.group[0]
              vue.result = res.group[1];
              vue.ip = res.group[2]
              vue.url = res.group[3]
              for (let i in vue.table){
                  vue.table[i].createDate = moment(vue.table[i].createDate).utcOffset(480).format('YYYY-MM-DD  HH:mm:ss')
              }
            }
          }
      })
    }
    const ppage = (i)=>{
        vue.page += i;
        show()
    }
    const handleCurrentChange = (number)=>{
        vue.page = number
        show()
    }

    onMounted(()=>{
      show()
    })

    return{
      ...toRefs(vue),show,reback,
      ppage,deleteSuccess,
      handleCurrentChange
      
    }
  }

}















</script>

<style>
li{
  list-style: none;
}
</style>