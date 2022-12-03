<template>
  <el-row class="row-bg" v-loading="loading" justify="space-evenly">
    <el-col :span="18">
      <el-col :span="24">
        <el-card shadow="never">
          <el-row >
            <el-col :span="4" :offset="1">
              <el-col :span="24"  >
                <el-card shadow="never" class="admin"> Admin </el-card>
              </el-col>
            </el-col>
            <el-col :span="16" :offset="1">
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
                    <el-table-column sortable  prop="id" label="ID"  />
                    <el-table-column prop="account" label="Name"  />
                    <el-table-column sortable prop="date" label="Date"   />
                    <el-table-column label="Operations">
                      <template #default="scope">
                        <el-button size="small" @click="editUser(scope.$index)" type="info" >Edit</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index)">Delete</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
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
      <el-input  type="text" v-model="tableData[index].id">
        <template #prepend>id</template>
      </el-input>
      <el-input  maxlength="10" show-word-limit v-model="tableData[index].account">
        <template #prepend>account</template>
      </el-input>
      <el-input  type="date" v-model="tableData[index].date">
        <template #prepend>date</template>
      </el-input>
    </el-card>
  </el-col>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
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
export default {
    setup(){
      const vue = reactive({
        tableData:[],
        table:[],
        loading:false,
        dialogVisible :false,
        index:0,
        sou:'',
        select:'Name'
      }) 
      const editUser = (i)=>{
        vue.dialogVisible = true
        vue.index = i
      }
      const query = ()=>{
        if(vue.sou===''){
          vue.tableData = vue.table
        }else{
          if(vue.select==='Name'){
            vue.tableData = vue.table.filter((i)=>{
               return i.account==vue.sou
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
              },
              success(res){
                for (let i in res){
                  res[i].date = moment(res[i].date). utcOffset( 480). format( 'YYYY-MM-DD')
                }
                 vue.tableData=res
                 vue.table = vue.tableData
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
                account:store.state.account,
            },
            success(res){
              for (let i in res){
               res[i].date = moment(res[i].date). utcOffset( 480). format( 'YYYY-MM-DD')
              }
              vue.loading = false
              vue.tableData = res
              vue.table = vue.tableData

            },error(){
            }
        })
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
        query
      }
    }
}
</script>

<style scoped>
  .el-card{
    text-align: center;
  }
  .admin {
    width: 100%;
    height: 100%;
		color: #3079ed; font-weight:bold; font-size:4em;
		
		animation-name: text-shadow-multicolor2;
		animation-duration: 1s;
		animation-timing-function: ease;
		animation-delay: 0s;
		animation-iteration-count: 1;
		animation-direction: normal;
		animation-fill-mode: forwards;
			
		
		animation: text-shadow-multicolor2 1s ease 0s 1 normal forwards;
	}
	@keyframes text-shadow-multicolor2 {
			
		0% {
			text-shadow:0 0 black, 0 0 red, 0 0 orange,0 0 yellow,0 0 green,0 0 skyblue,0 0 blue,0 0 white;
			transform:translateX(0) translateY(0);
		}
		100% {
			text-shadow:-1px -1px black,-3px -3px red,-5px -5px orange,-7px -7px yellow,-9px -9px green,-11px -11px skyblue,-13px -13px blue,-15px -15px white;
			transform:translateX(-1px) translateY(5px);
		}
	}
</style>