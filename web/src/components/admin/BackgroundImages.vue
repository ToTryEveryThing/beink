<template>
    <uploadfile/>
    <el-button @click="load">加载图片</el-button>
    <el-select  v-model="vue.value" @change="CChange" placeholder="Select" size="lagre">
        <el-option
          v-for="item in vue.options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <input  type="file"  v-if="$store.state.role==='admin'"  @change="previewImage"  id="XXX"/>
    <el-row :gutter="20"  >
        <el-col :span="6" v-loading="loading" v-if="PreviewImage!==''">
            <el-card>
                <el-tooltip content="点击上传" placement="top">
                    <el-image  class="PreviewImage"
                        fit="fit"
                        style="width:320px;height:200px;"
                        :src="PreviewImage"
                        @click="upload"
                    />
                </el-tooltip>
            </el-card>
        </el-col>
        <el-col :span="6" v-for="i in $store.state.images.ImagesList" :key="i">
            <el-card>
                <template #header>
                    <div class="card-header">
                        <el-button type="danger" link size="large" @click="deleteImage(i)">
                            delete
                         </el-button>
                    </div>
                  </template>
                <el-image :src="'https://cdn.beink.cn/'+i"  >
                </el-image>
            </el-card>
        </el-col>
      </el-row>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useStore } from 'vuex';
import uploadfile from './UploadFiles.vue'
    

    const store = useStore()

    let PreviewImage = ref("")
    let loading = ref(false)

    const previewImage = (event)=>{
        const file = event.target.files[0];
            // 判断是否是图片类型
        if (file.type.startsWith('image/')) {
            // 创建 FileReader 对象
            const reader = new FileReader();
            // 读取图片文件
            reader.readAsDataURL(file);
            reader.onload = () => {
                PreviewImage.value = reader.result;
            }
        }
    }

    const upload = ()=>{
        let e = document.getElementById('XXX')
        if(e.value=='')return
        var formData = new FormData(); 
        //表单可以增加数据 如下
        formData.append('file', e.files[0]); //传给后端的路径
        formData.append("keyPrefix",vue.value)
        loading.value = true
        store.dispatch("upload",{
            token:store.state.token,
            formData:formData,
            success(){
                loading.value = false
                document.getElementById('XXX').value=''
                PreviewImage.value = ''
            }
        })
    }

    const vue = reactive({
        options : [
        {
            value: 'background',
            label: 'background',
        },
        {
            value: 'study',
            label: 'study',
        },
        ],
        value:'background'
    })

    const load = ()=>{
        store.dispatch("getList",{keyPrefix:"background"})
    }

    const deleteImage = i=>{
        store.dispatch("delete",{
            url:i,
            keyPrefix:vue.value,
            account:store.state.account,
            token:store.state.token,
        })
    }

    const CChange = (val)=>{
        store.dispatch("getList",{keyPrefix: val})
    }


</script>

<style scoped>
.load{
    position: relative;
    cursor: pointer;
    margin-bottom: 10px;
}
</style>