<template>
    <el-row class="hhhhh" style="padding-top:20px;"  justify="space-evenly">
        <el-col :span="8" >
            <el-tabs tab-position="left" 
            class="demo-tabs">
                <el-tab-pane label="设置">
                    <userView/>
                </el-tab-pane>
                <el-tab-pane label="粉丝">
                    <fans/>
                </el-tab-pane>
                <el-tab-pane  label="关注">
                    <follow/>
                </el-tab-pane>
                <el-tab-pane  label="收藏">
                    <el-skeleton :rows="5" animated />
                </el-tab-pane>
            </el-tabs>
        </el-col>
      </el-row>
</template>

<script>
import userView from './UserView.vue'
import fans from './FansVIew.vue'
import follow from './FollowView.vue'

export default {
    components:{userView, fans, follow},
}
</script>

<style scoped>
    .hhhhh{
        background-color: #ffffff;
    }
</style>