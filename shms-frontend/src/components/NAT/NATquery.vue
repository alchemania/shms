<template>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="natid" label="预约ID" width="400" />
        <el-table-column prop="realname" label="真实姓名" />
        <el-table-column prop="identity" label="身份证号" />
        <el-table-column prop="hospital" label="预约医院" />
        <el-table-column prop="book_time" label="预约时间" />
    </el-table>
</template>
  
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { queryNATBook } from '../../api/api'
import cookies from 'vue-cookies'
import { ElMessage } from 'element-plus';

const tableData = ref([])

onMounted(() => {
    queryNATBook({ id: cookies.get('userid') })
        .then((res) => {
            console.log(res.data)
            if (res.data.code === 200) {
                tableData.value = res.data.data
                ElMessage({
                    message: "查询成功！",
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
})

</script>

<style scoped>

</style>
  