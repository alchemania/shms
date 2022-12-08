<template>
  <vue3-pro-table ref="proTable" title="列表" :request="getList" :columns="state.columns">
    <template #toolbar>
      <el-button
          type="primary"
          @click="drawer = !drawer">创建公告
      </el-button>
      <el-button type="danger" @click="proTable.value.refresh()">刷新</el-button>
    </template>
    <template #operate="scope">
      <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
      <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
    </template>
  </vue3-pro-table>

  <el-drawer v-model="drawer" :show-close="true" @closed="handleBack">
    <template #default>
      <el-form :model="form" label-width="120px">
        <el-form-item label="ID">
          <el-input v-model="form.id" clearable disabled/>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="form.notice_name" clearable/>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.notice_text" clearable type="textarea"/>
        </el-form-item>
        <el-form-item label="公告类型">
          <el-select v-model="form.notice_type" placeholder="Select">
            <el-option :key=0 label="success" value="success"></el-option>
            <el-option :key=1 label="info" value="info"></el-option>
            <el-option :key=2 label="warning" value="warning"></el-option>
            <el-option :key=3 label="error" value="error"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
              v-model="form.start_time"
              type="datetime"
              placeholder="Select date and time"
          />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
              v-model="form.end_time"
              type="datetime"
              placeholder="Select date and time"
          />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
              v-model="form.create_time"
              type="datetime"
              placeholder="Select date and time"
              disabled
          />
        </el-form-item>
        <el-form-item label="更新时间">
          <el-date-picker
              v-model="form.update_time"
              type="datetime"
              placeholder="Select date and time"
              disabled
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handlePut">提交</el-button>
          <el-button @click="(drawer = !drawer)">取消</el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-drawer>
</template>

<script setup>
import {defineComponent, reactive, ref, toRefs} from "vue";
import {deleteOneNotice, editOneNotice, getAllNotice, addOneNotice} from "../../../api/api.js"
import {ElMessage} from "element-plus";


const drawer = ref(false)
const form = reactive({
  id: "",
  notice_name: "",
  notice_text: "",
  notice_type: "",
  start_time: "",
  end_time: "",
  create_time: "",
  update_time: "",
})
const proTable = ref(null)
const eraseform = () => {
  Object.assign(form, {
    id: "",
    notice_name: "",
    notice_text: "",
    notice_type: "",
    start_time: "",
    end_time: "",
    create_time: "",
    update_time: "",
  })
}

const state = reactive({
  // 表格列配置，大部分属性跟el-table-column配置一样
  columns: [
    {label: "ID", prop: "id", width: 300},
    {label: "标题", prop: "notice_name", width: 100},
    {label: "内容", prop: "notice_text", width: 200},
    {label: "公告种类", prop: "notice_type", width: 60},
    {label: "开始时间", prop: "start_time"},
    {label: "结束时间", prop: "end_time"},
    {
      label: "操作",
      fixed: "right",
      width: 180,
      align: "center",
      tdSlot: "operate", // 自定义单元格内容的插槽名称
    },
  ],
});
// 请求函数
const getList = async (params) => {
  // params是从组件接收的，包含分页和搜索字段。
  const res = await getAllNotice();
  const data = res.data.data
  console.log(data)
  // 必须要返回一个对象，包含data数组和total总数
  const count = data.reduce((counter, obj) => {
    return counter = counter + 1;
  }, 0);
  return {
    data: data,
    total: +count,
  };
};

const handleEdit = (row) => {
  Object.assign(form, row)
  console.log(form)
  drawer.value = !drawer.value
}
const handleBack = (row) => {
  drawer.value = !drawer.value
  eraseform()
}

const handleDelete = (row) => {
  deleteOneNotice({id: row.id})
      .then((res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          ElMessage({
            message: "删除成功！",
            type: "success",
            showClose: true,
          });
          proTable.value.refresh()
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

const handlePut = (val) => {
  if (form.id !== "") {
    editOneNotice(form)
        .then((res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            ElMessage({
              message: "更新成功！",
              type: "success",
              showClose: true,
            });
            drawer.value = !drawer.value
            proTable.value.refresh()
          } else {
            ElMessage({
              message: res.data.msg,
              type: "error",
              showClose: true,
            });
            drawer.value = !drawer.value
          }
        })
        .catch((err) => {
          console.log(err)
          ElMessage({
            message: "Internal Error",
            type: "error",
            showClose: true,
          });
          drawer.value = !drawer.value
        });
  } else if (form.id === "") {
    addOneNotice(form).then((res) => {
      console.log(res.data)
      if (res.data.code === 200) {
        ElMessage({
          message: "新增成功！",
          type: "success",
          showClose: true,
        });
        drawer.value = !drawer.value
        proTable.value.refresh()
      } else {
        ElMessage({
          message: res.data.msg,
          type: "error",
          showClose: true,
        });
        drawer.value = !drawer.value
      }
    })
        .catch((err) => {
          console.log(err)
          ElMessage({
            message: "Internal Error",
            type: "error",
            showClose: true,
          });
          drawer.value = !drawer.value
        });
  }
  eraseform()
}

</script>