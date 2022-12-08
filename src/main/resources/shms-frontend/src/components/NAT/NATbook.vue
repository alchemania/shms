<template>
    <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="真实姓名">
            <el-input v-model="bookform.realname" size="large" />
        </el-form-item>
        <el-form-item label="身份证号码">
            <el-input v-model="bookform.identity" size="large" />
        </el-form-item>
        <el-form-item label="预约医院">
            <el-select v-model="bookform.hospital" placeholder="请选择医院" size="large">
                <el-option v-for="item in hospitals" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="预约时间">
            <el-date-picker v-model="bookform.book_time" type="datetime" placeholder="请选择预约时间" size="large"
                value-format="YYYY-MM-DDTHH:mm:ss" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleSubmit">提交</el-button>
            <el-button @click="handleReset">清空</el-button>
        </el-form-item>
    </el-form>
</template>
  
<script setup>
import { reactive, ref } from 'vue'
import { createNATBook } from '../../api/api'
import { GMTToStr } from '../../utils/utils'
import cookies from 'vue-cookies'
import { ElMessage } from 'element-plus';

const bookform = reactive({
    realname: "",
    userid: cookies.get("userid"),
    hospital: "",
    book_time: "",
    identity: "",
})

const hospitals = [
    {
        value: '松江区第一人民医院',
        label: '松江区第一人民医院'
    },
    {
        value: '方松街道卫生所',
        label: '方松街道卫生所'
    },
    {
        value: '东华大学卫生所',
        label: '东华大学卫生所'
    }
]

const handleSubmit = () => {
    console.log(bookform)
    createNATBook(bookform)
        .then((res) => {
            console.log(res.data)
            if (res.data.code === 200) {
                ElMessage({
                    message: "预约成功啦",
                    type: "success",
                    showClose: true,
                });
            } else {
                ElMessage({
                    message: res.data.msg,
                    type: "error",
                    showClose: true,
                });
            }
        })
        .catch((err) => {
            console.log(err)
            ElMessage({
                message: "Internal Error",
                type: "error",
                showClose: true,
            });
        });
}

const handleReset = () => {
    bookform.realname = ""
    bookform.hospital = ""
    bookform.book_time = ""
    bookform.identity = ""
}
</script>
  