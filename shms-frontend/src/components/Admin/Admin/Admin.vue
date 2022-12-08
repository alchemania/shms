<template>
  <vue3-pro-table title="列表" ref="proTable" :request="getList" :columns="state.columns" :search="state.searchConfig">
    <template #toolbar>
      <el-button type="danger" @click="proTable.value.refresh()">刷新
      </el-button>
    </template>
    <template #priv="scope">
      <el-tag :type="scope.row.privilege=== 0 ? 'info' :'success' " disable-transitions>
        {{ scope.row.privilege === 0 ?  "普通用户": "管理员" }}
      </el-tag>
    </template>
    <template #operate="scope">
      <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
      <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
    </template>
  </vue3-pro-table>

  <el-drawer v-model="drawer" :show-close="false">
    <template #default>
      <el-form :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" clearable/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" clearable show-password placeholder="*******"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" clearable/>
        </el-form-item>
        <el-form-item label="特权等级">
          <el-select v-model="form.privilege" placeholder="Select">
            <el-option :key=0 label=普通用户 :value=0></el-option>
            <el-option :key=1 label="管理员" :value=1></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handlePut">确认</el-button>
          <el-button @click="(drawer = !drawer)">Cancel</el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-drawer>
</template>

<script setup>
import {defineComponent, reactive, ref, toRefs} from "vue";
import {getAllUser, editOneUser, deleteOneUser} from "../../../api/api.js"
import {ElMessage} from "element-plus";


const drawer = ref(false)
const form = reactive({})
const proTable = ref(null)

const state = reactive({
  // 表格列配置，大部分属性跟el-table-column配置一样
  columns: [
    {label: "ID", prop: "userid", width: 300},
    {label: "名称", prop: "username", width: 180},
    {label: "邮箱", prop: "email"},
    {label: "特权等级", prop: "privilege", tdSlot: "priv"},
    {
      label: "操作",
      fixed: "right",
      width: 180,
      align: "center",
      tdSlot: "operate", // 自定义单元格内容的插槽名称
    },
  ],
  // searchConfig: {
  //   labelWidth: "80px",
  //   inputWidth: "150px",
  //   fields: [
  //     {
  //       type: "text",
  //       label: "账户名称",
  //       name: "username",
  //       defaultValue: "",
  //     },
  //     {
  //       label: "特权等级",
  //       name: "privilege",
  //       type: "select",
  //       options: [
  //         {
  //           name: "管理员",
  //           value: 1,
  //         },
  //         {
  //           name: "普通用户",
  //           value: 0,
  //         },
  //       ],
  //     },
  //   ]
  // }
});

// 请求函数
const getList = async (params) => {
  // params是从组件接收的，包含分页和搜索字段。
  const res = await getAllUser();
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


const handleDelete = (row) => {
  deleteOneUser({id: row.userid})
      .then((res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          ElMessage({
            message: "更新成功！",
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

const handlePut = () => {
  editOneUser(form)
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
}

</script>