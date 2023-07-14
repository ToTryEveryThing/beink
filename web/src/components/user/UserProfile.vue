<template>
    <el-row  style="padding-top:20px;"  justify="space-evenly">
        <el-col :span="8" >
            <el-tabs tab-position="left" style="height: 90%" class="demo-tabs">
            <el-tab-pane label="setting">
                <userView/>
            </el-tab-pane>
            <el-tab-pane label="配置">
                <el-skeleton :rows="5" animated />
            </el-tab-pane>
            <el-tab-pane disabled label="角色">
                Role：{{ $store.state.role }}
            </el-tab-pane>
            <el-tab-pane disabled label="其他">other</el-tab-pane>
            </el-tabs>
        </el-col>
      </el-row>
</template>

<script>
import userView from './UserView.vue'

export default {
    components:{userView}
}
</script>

<style>

</style>