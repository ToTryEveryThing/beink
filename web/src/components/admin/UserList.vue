<template>
          <el-card shadow="never">
            <el-row >
              <el-col :span="24" >
                <el-col :span="24">
                  <el-card shadow="never">
                    <el-row>
                      <el-col :span="12">
                        <el-input
                        v-model="sou"
                        @change="query"
                        clearable 
                        placeholder="Please input"
                        class="input-with-select"
                      >
                        <template #prepend>
                          Name
                        </template>
                        <template #append>
                          <el-button @click="query" >查询</el-button>
                        </template>
                      </el-input>
                      </el-col>
                      <el-button @click="excel"  type="primary">导出</el-button>
                    </el-row>
                    <el-table 
                    :data="tableData" stripe border
                    @selection-change="handleSelectionChange"
                     >
                      <el-table-column type="selection" width="80" />
                      <el-table-column   prop="id" label="ID" width="60"/>
                      <el-table-column prop="account" label="Name" width="130" />
                      <el-table-column  label="Role"  width="100">
                        <template #default="scope">
                              <el-tag v-if="scope.row.role==='admin'">{{ scope.row.role }}</el-tag>
                              <el-tag type="info" v-else>{{ scope.row.role }}</el-tag>
                        </template>
                      </el-table-column>>
                      <el-table-column  label="Enable" width="80" >
                        <template #default="scope">
                              <el-tag type="success" v-if="scope.row.enable"> 是 </el-tag>
                              <el-tag type="info" v-else>否 </el-tag>
                        </template>
                      </el-table-column>>
                      <el-table-column prop="backimg" label="Img" width="60" />
                      <el-table-column prop="loginTime" label="最后时间"  />
                      <el-table-column prop="mail" label="Email"  />
                      <el-table-column  prop="date" label="Date"   />
                      <el-table-column label="Operations" fixed="right" width="150">
                        <template #default="scope">
                          <el-button size="small" @click="editUser(scope.$index)" type="info" >Edit</el-button>
                          <el-button size="small" type="danger" @click="handleDelete(scope.$index)">Delete</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    
                    <div style="margin-top:10px;">
                      <el-button style="float:right;" type="danger" @click="multiple">删除多个</el-button>
                      <el-pagination 
                        @current-change="handleCurrentChange"
                          background   @next-click="ppage(1)" @prev-click="ppage(-1)" 
                          layout="prev, pager, next" 
                          :total="count"/>
                        
                    </div>
                    
                  </el-card>
                </el-col>
              </el-col>
            </el-row>
          </el-card>
          <!-- 编辑 -->
    <el-dialog
      v-model="dialogVisible"
      title="Edit"
      width="30%"
      :before-close="handleClose"
    >
    <el-col :span="24">
      <el-card shadow="never">
        <el-tag>管理员</el-tag>
        <el-switch
          v-model="isAdmin"
          inline-prompt
          active-text="是"
          inactive-text="否"
        />
        <el-tag>启用</el-tag>
        <el-switch
          v-model="enable"
          inline-prompt
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
        />
        <el-input  type="text" disabled v-model="tableData[index].id">
          <template #prepend>ID</template>
        </el-input>
        <el-input  maxlength="10" show-word-limit v-model="tableData[index].account">
          <template #prepend>name</template>
        </el-input>
        <el-input  v-model="tableData[index].backimg">
          <template #prepend>backimg</template>
        </el-input>
        <el-date-picker
          v-model="tableData[index].date"
          type="date"
          placeholder="Pick a day"
          size="large"
          disabled
        />
      </el-card>
    </el-col>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updata">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </template>
  
  <script>
  import $ from 'jquery'
  import {useStore} from 'vuex'
  import router from '@/router/index'
  import {onMounted, reactive, toRefs} from 'vue'
  import moment from 'moment/moment'
  import {success,error} from '@/utiles/message'
  import XLSX from "xlsx";
  import config from '@/utiles/config'
  // import FileSaver from 'file-saver'
  export default {
      setup(){
        const vue = reactive({
          tableData:[],
          downloadData:[],
          loading:false,
          dialogVisible :false,
          index:0,
          sou:'',
          count:'',
          page :1,
          isAdmin:false,
          enable:false,
          ids:[]
        }) 
        const editUser = (i)=>{
          vue.dialogVisible = true
          vue.index = i
          vue.enable = vue.tableData[i].enable 
          vue.isAdmin = vue.tableData[i].role === "admin" ? true : false 
        }

        const excel = ()=>{
          const ws = XLSX.utils.json_to_sheet(vue.downloadData)//此处tableData.value为表格的数据
          const wb = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
          const binaryData = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
  
          /* 创建 Blob 对象 */
          const blob = new Blob([binaryData], { type: 'application/octet-stream' });
  
          /* 下载文件 */
          const link = document.createElement('a');
          link.href = URL.createObjectURL(blob);
          link.download = 'data.xlsx';
          link.click();
        }
        const handleSelectionChange = (i)=>{
          vue.downloadData = i
          vue.ids = []
          i.forEach(y => {
            vue.ids.unshift(y.id)
          });
        }
        const multiple = ()=>{
          if(vue.ids.length!==0)
          $.ajax({
                url:`${config.API_URL}/user/admin/multiple/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    ids:JSON.stringify({ids:JSON.stringify(vue.ids)})
                },
                success(res){
                    if(res.code===200){
                      success(res.message)
                      aaa()
                    }else{
                      error(res.message)
                    }
                },error(){
                  error("删除出错")
                }
            })
        }
        const updata =()=>{
          vue.dialogVisible = false
          $.ajax({
                url:`${config.API_URL}/user/account/updata/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    id:vue.tableData[vue.index].id,
                    account:vue.tableData[vue.index].account,
                    backimg:vue.tableData[vue.index].backimg,
                    role:vue.isAdmin === true ? "admin" : "user",
                    enable: vue.enable
                },
                success(res){
                  if(res.code===200){
                    success(res.message)
                    vue.tableData[vue.index].role = vue.isAdmin === true ? "admin" : "user"
                    vue.tableData[vue.index].enable = vue.enable
                  }else{
                    error(res.message)
                  }
                },error(){
                  error("error")
                }
          })
        }
        const query = ()=>{
         aaa()
        }
        let handleDelete= (a)=>{
          let f = confirm("确认删除" + vue.tableData[a].account)
          
            if(f){
              $.ajax({
                url:`${config.API_URL}/user/admin/delete/`,
                type:'post',
                headers:{
                    Authorization:"Bearer " + store.state.token
                },
                data:{
                    id:vue.tableData[a].id,
                    account:store.state.account
                },
                success(res){
                  if(res.code===200)
                    aaa()
                },error(res){
                  console.log(res.message)
                }
            })
            }
        }
        let aaa = ()=>{
            $.ajax({
              url:`${config.API_URL}/user/admin/list/`,
              type:'post',
              headers:{
                  Authorization:"Bearer " + store.state.token
              },
              data:{
                  page:vue.page,
                  name:vue.sou
              },
              success(res){
                if(res.code===0){
                  error("获取失败")
                  return 
                }
                vue.count = res.count
                vue.tableData = res.records
                for (let i in vue.tableData){
                  vue.tableData[i].date = moment(vue.tableData[i].date). utcOffset( 480). format( 'YYYY-MM-DD')
                  vue.tableData[i].loginTime = moment(vue.tableData[i].loginTime). utcOffset( 480). format( 'YYYY-MM-DD')
                }
                vue.loading = false
                vue.table = vue.tableData
              },error(){
                error("获取失败")
              }
          })
        }
        const ppage = (i)=>{
            vue.page += i;
            aaa()
        }
        const handleCurrentChange = (number)=>{
          vue.page = number
          aaa()
        }
        const store = useStore();
        onMounted(()=>{
          document.getElementsByTagName('body')[0].style.backgroundImage = ``
          $('html').css({'--backColor':''})  
          $('html').css({'--color':''})
          const jwt = localStorage.getItem("jwt");
          if(jwt){
              store.commit("updateToken",jwt)
              store.dispatch("getinfo",{
                  success(){
                      aaa()
                  },
                  error(){
                      router.push({name:'main'})
                  }
              })
          }
        })
        return {
          handleSelectionChange,
          excel,multiple,
          ...toRefs(vue),handleDelete,editUser,
          query,ppage,handleCurrentChange,
          updata
        }
      }
  }
  </script>
  
  <style scoped>
    .el-card{
      text-align: center;
    }
  </style>