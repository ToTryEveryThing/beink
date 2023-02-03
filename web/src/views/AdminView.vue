<template>
  <el-row class="row-bg" v-loading="loading" justify="space-evenly">
    <el-col :span="18">
      <el-col :span="24">
        <el-card shadow="never">
          <el-row >
            <el-col :span="4">
              <el-col :span="24"  >
                  <el-image  style="width: 100px; height: 100px" src="https://cdn.acwing.com/media/user/profile/photo/71127_lg_5c719f083a.png" :fit="fit" />
                </el-col>
            </el-col>
            <el-col :span="20" >
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
                        <el-select v-model="select" placeholder="Select" style="width: 90px">
                          <el-option label="ID" value="2" />
                          <el-option label="Name" value="1" />
                          <!-- <el-option label="Date" value="3" /> -->
                        </el-select>
                      </template>
                      <template #append>
                        <el-button @click="query" >查询</el-button>
                      </template>
                    </el-input>
                    </el-col>
                  </el-row>
                  <el-table :data="tableData" stripe border >
                    <el-table-column   prop="id" label="ID" />
                    <el-table-column prop="account" label="Name"  />
                    <el-table-column  label="Role"   >
                      <template #default="scope">
                            <el-tag v-if="scope.row.role==='admin'">{{ scope.row.role }}</el-tag>
                            <el-tag type="info" v-else>{{ scope.row.role }}</el-tag>
                      </template>
                    </el-table-column>>
                    <el-table-column prop="backimg" label="Img"  />
                    <el-table-column  prop="date" label="Date"   />
                    <el-table-column label="Operations" fixed="right" width="150">
                      <template #default="scope">
                        <el-button size="small" @click="editUser(scope.$index)" type="info" >Edit</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index)">Delete</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <div style="margin-top:10px;">
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
      </el-col>
    </el-col>
  </el-row>
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
import router from '../router/index'
import {onMounted, reactive, toRefs} from 'vue'
import moment from 'moment/moment'
import {success,error} from '../utiles/message'
export default {
    setup(){
      const vue = reactive({
        tableData:[],
        table:[],
        loading:false,
        dialogVisible :false,
        index:0,
        sou:'',
        select:'Name',
        count:'',
        page :1,
        isAdmin:false
      }) 
      const editUser = (i)=>{
        vue.dialogVisible = true
        vue.index = i
        vue.isAdmin = vue.tableData[i].role === "admin" ? true : false 
      }
      const updata =()=>{
        vue.dialogVisible = false
        $.ajax({
              url:'https://so.beink.cn/user/account/updata/',
              type:'post',
              headers:{
                  Authorization:"Bearer " + store.state.token
              },
              data:{
                  id:vue.tableData[vue.index].id,
                  account:vue.tableData[vue.index].account,
                  backimg:vue.tableData[vue.index].backimg,
                  role:vue.isAdmin === true ? "admin" : "use",
              },
              success(res){
                if(res.code===1){
                  success("修改成功")
                  vue.tableData[vue.index].role = vue.isAdmin === true ? "admin" : "use"
                }else{
                  error("修改失败")
                }
              },error(){
                error("error")
              }
        })
      }
      const query = ()=>{
        if(vue.sou===''){
          vue.tableData = vue.table
        }else{
          if(vue.select==='Name'){
            vue.tableData = vue.table.filter((i)=>{
                return i.account.indexOf(vue.sou)!=-1
            })
          }
          else{
            vue.tableData = vue.table.filter((i)=>{
               return i.id==vue.sou
            })
          }
        }
      }
      let handleDelete= (a)=>{
        let f = confirm("确认删除" + vue.tableData[a].account)
        
          if(f){
            $.ajax({
              url:'https://so.beink.cn/user/admin/delete/',
              type:'post',
              headers:{
                  Authorization:"Bearer " + store.state.token
              },
              data:{
                  id:vue.tableData[a].id,
                  account:store.state.account
              },
              success(res){
                if(res.msg==="success")
                  aaa()
              },error(res){
                console.log(res)
              }
          })
          }
      }
      let aaa = ()=>{
          $.ajax({
            url:'https://so.beink.cn/user/admin/list/',
            type:'post',
            headers:{
                Authorization:"Bearer " + store.state.token
            },
            data:{
                page:vue.page
            },
            success(res){
              vue.count = res.count
              vue.tableData = res.records
              for (let i in vue.tableData){
                vue.tableData[i].date = moment(vue.tableData[i].date). utcOffset( 480). format( 'YYYY-MM-DD')
              }
              vue.loading = false
              vue.table = vue.tableData

            },error(res){
              console.log(res)
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