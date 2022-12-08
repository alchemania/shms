<template>
  <vue3-pro-table ref="proTable" title="列表" :request="getList" :columns="state.columns">
    <template #toolbar>
      <el-button
          type="primary"
          @click="handleNew">新建学生档案
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
        <el-form-item label="学号">
          <el-input v-model="form.student_number" clearable :disabled="disabled"/>
        </el-form-item>
        <el-form-item label="关联用户">
          <el-select
              v-model="form.username"
              filterable
              remote
              reserve-keyword
              placeholder="Please enter a keyword"
              remote-show-suffix
              :remote-method="remoteMethod"
              :loading="loading"
          >
            <el-option
                v-for="item in options"
                :label="item"
                :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.student_name" clearable/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.student_master" clearable/>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="form.student_class" clearable/>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.student_phone" clearable/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.student_text" clearable type="textarea"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handlePut">提交</el-button>
          <el-button @click="handleBack">取消</el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-drawer>
</template>

<script setup>
import {defineComponent, reactive, ref, toRefs} from "vue";
import {
  getAllStudents,
  addOneStudent,
  editOneStudent,
  deleteOneStudent,
  getUserByfuzzyUsername
} from "../../../api/api.js"
import {ElMessage} from "element-plus";

const drawer = ref(false)
const form = reactive({
  student_number: "",
  username: "",
  student_name: "",
  student_master: "",
  student_class: "",
  student_phone: "",
  student_text: "",
})
const proTable = ref(null)
const eraseform = () => {
  Object.assign(form, {
    student_number: "",
    username: "",
    student_name: "",
    student_master: "",
    student_class: "",
    student_phone: "",
    student_text: "",
  })
}
const disabled = ref(false)
const list = ref([])
const options = ref([])
const loading = ref(false)

const remoteMethod = (query) => {
  if (query) {
    loading.value = true
    console.log(query)
    getUserByfuzzyUsername({username: query})
        .then((res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            loading.value = false
            options.value = res.data.data
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
  } else {
    options.value = []
  }
}


const state = reactive({
  // 表格列配置，大部分属性跟el-table-column配置一样
  columns: [
    {label: "学号", prop: "student_number"},
    {label: "关联用户", prop: "username"},
    {label: "名称", prop: "student_name", width: 180},
    {label: "专业", prop: "student_master"},
    {label: "班级", prop: "student_class"},
    {label: "电话", prop: "student_phone"},
    {label: "备注", prop: "student_text"},
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
  const res = await getAllStudents();
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


const handleNew = () => {
  drawer.value = !drawer.value
  disabled.value = false
}

const handleEdit = (row) => {
  Object.assign(form, row)
  console.log(form)
  disabled.value = true
  drawer.value = !drawer.value
}
const handleBack = (row) => {
  drawer.value = !drawer.value
  eraseform()
}

const handleDelete = (row) => {
  deleteOneStudent({id: row.student_number})
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
  if (form.student_number !== "") {
    editOneStudent(form)
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
  } else if (form.student_number === "") {
    isEditable.value = false
    addOneStudent(form).then((res) => {
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