<template>
    <el-card class="box-card">
        <template #header>
            <div class="card-header">
                <span>{{ healthform.realname }}</span>
                <el-button class="button" text @click="drawer = !drawer">修改基本信息</el-button>
            </div>
        </template>
        <div class="text item">{{ healthform.age }}岁</div>
        <div class="text item">{{ healthform.height }}cm / {{ healthform.weight }}kg</div>
        <div class="text item">BMI {{ healthform.BMI }}</div>
        <div>
            <el-tag class="ml-2" type="success">Healthy</el-tag>
        </div>
        <time class="time">{{ "更新时间:" + healthform.update_time }}</time>
    </el-card>
    <el-drawer v-model="drawer" title="I am the title" :with-header="false">
        <el-form :model="healthform" label-width="120px">
            <el-form-item label="姓名">
                <el-input v-model="healthform.realname" clearable @change=""></el-input>
            </el-form-item>
            <el-form-item label="年龄">
                <el-input v-model="healthform.age" clearable @change=""><template #append>岁</template>
                </el-input>
            </el-form-item>
            <el-form-item label="身高">
                <el-input v-model="healthform.height" clearable @change=""><template #append>cm</template>
                </el-input>
            </el-form-item>
            <el-form-item label="体重">
                <el-input v-model="healthform.weight" clearable @change=""><template #append>kg</template>
                </el-input>
            </el-form-item>
            <el-form-item label="BMI">
                <el-input v-model="healthform.BMI" clearable @change="" disabled><template
                        #append>kg/cm^2</template></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">修改</el-button>
                <el-button @click="drawer = !drawer">取消</el-button>
            </el-form-item>
        </el-form>
    </el-drawer>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { fetchHealthData, alterHealthData } from '../../api/api'
import { ElMessage } from 'element-plus'
import cookies from 'vue-cookies'

onMounted(() => {
    const userid = cookies.get('userid')
    fetchHealthData({ id: userid })
        .then((res) => {
            console.log(res.data)
            if (res.data.code === 200) {
                healthform.realname = res.data.data.realname
                healthform.age = res.data.data.age
                healthform.height = res.data.data.height
                healthform.weight = res.data.data.weight
                healthform.update_time = new Date(res.data.data.update_time)
                healthform.BMI = healthform.weight / ((healthform.height / 100) ** 2)
                console.log(healthform)
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


const healthform = reactive({
    userid: cookies.get("userid"),
    realname: "Wei. shao",
    age: 20,
    height: 200,//cm
    weight: 50,//kg
    BMI: 0, //auto generate
    update_time: null,
})

const drawer = ref(false)

watch(healthform, (newval) => {
    healthform.BMI = healthform.weight / ((healthform.height / 100) ** 2)
})

const onSubmit = () => {

    alterHealthData(healthform)
        .then((res) => {
            console.log(res.data)
            if (res.data.code == 200) {
                ElMessage({
                    message: "修改成功！",
                    type: "success",
                    showClose: true,
                });
                drawer.value = false
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
</script>


<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    width: 480px;
}

.time {
    font-size: 12px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.button {
    padding: 0;
    min-height: auto;
}

.image {
    width: 100%;
    display: block;
}
</style>