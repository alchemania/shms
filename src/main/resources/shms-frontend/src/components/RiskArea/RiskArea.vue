<template>
    <div style="width: 100%">
        <el-cascader :props="props" size="large" v-model="casform" />
        <el-button type="primary" @click="handleQuery" size="large">查询</el-button>
        <el-button type="primary" @click="(drawer = !drawer)" size="large">详细信息</el-button>
        <el-table :data="riskarea" style="width: 100%">
            <el-table-column prop="area_name" label="地区名称" width="400" />
            <el-table-column prop="community" label="社区" />
            <el-table-column prop="type" label="风险等级">
                <template #default="scope">
                    <el-tag :type="scope.row.type == 2 ? 'danger' : 'success'" disable-transitions>
                        {{ scope.row.type == 2 ? "风险等级：高风险" : "风险等级：低风险" }}
                    </el-tag>
                </template>
            </el-table-column>
        </el-table>
        <el-drawer :destroy-on-close="true" :show-close="true" v-model="drawer">
            <el-card class="box-card">
                <template #header>
                    <div class="card-header">
                        <span>详细信息</span>
                    </div>
                </template>
                <div>高风险地区个数：{{ epdetail.hcount }}</div>
                <div>低风险地区个数：{{ epdetail.lcount }}</div>
                <div>数据更新时间：{{ epdetail.time }}</div>
            </el-card>
        </el-drawer>

    </div>
</template>
  
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { threeLevelQuery, riskAreaQuery, epDetail } from '../../api/api'
import { ElMessage } from 'element-plus'

let id = 0

const update_time = ref("")

const casform = ref({
    "province": "",
    "city": "",
    "county": "",
})

const drawer = ref(false)
const riskarea = ref()

const props = {
    lazy: true,
    lazyLoad(node, resolve) {
        const { level, value } = node
        if (level == 0) {
            threeLevelQuery({})
                .then((res) => {
                    if (res.data.code == 200) {
                        const province = res.data.data
                        const nodes = province.map((item) => ({
                            value: item,
                            label: item,
                            leaf: level >= 2
                        }))
                        resolve(nodes)
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
        else if (level == 1) {
            casform.province = value
            threeLevelQuery({ "province": value })
                .then((res) => {
                    if (res.data.code == 200) {
                        const province = res.data.data
                        const nodes = province.map((item) => ({
                            value: item,
                            label: item,
                            leaf: level >= 2
                        }))
                        resolve(nodes)
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
        else if (level == 2) {
            casform.city = value
            console.log(casform)
            threeLevelQuery({ "province": casform.province, "city": value })
                .then((res) => {
                    casform.county = value
                    if (res.data.code == 200) {
                        const province = res.data.data
                        const nodes = province.map((item) => ({
                            value: item,
                            label: item,
                            leaf: level >= 2
                        }))
                        resolve(nodes)
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
    },
}

const handleQuery = () => {
    var array = casform._value;

    riskAreaQuery({ "province": array[0], "city": array[1], "county": array[2] })
        .then((res) => {
            if (res.data.code == 200) {
                console.log(res.data.data)
                riskarea.value = res.data.data
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

const epdetail = reactive({
    hcount: "",
    lcount: "",
    time: null,
})

onMounted(() => {
    epDetail()
        .then((res) => {
            if (res.data.code == 200) {
                epdetail.hcount = res.data.data.hcount
                epdetail.lcount = res.data.data.lcount
                epdetail.time =  new Date(res.data.data.time)
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

<style>

</style>