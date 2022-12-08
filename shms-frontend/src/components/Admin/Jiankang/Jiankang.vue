<template>
  <vue3-pro-table title="列表" :request="getList" :columns="columns">
    <template #operate="scope">
      <el-button size="mini" type="primary">编辑</el-button>
      <el-button size="mini" type="danger">删除</el-button>
    </template>
  </vue3-pro-table>
</template>

<script>
import {defineComponent, reactive, ref, toRefs} from "vue";
import {getAllJiankang} from "../../../api/api.js"

export default defineComponent({
  setup() {
    const state = reactive({
      // 表格列配置，大部分属性跟el-table-column配置一样
      columns: [
        {label: "填报ID", prop: "userid"},
        {label: "名称", prop: "name"},
        {label: "日期", prop: "date"},
        {label: "体温", prop: "jiankang_tiwen"},
        {label: "名称", prop: "jianhang_huan"},
        {label: "邮箱", prop: "jiankang_yi"},
        {label: "邮箱", prop: "jiankang_jia"},
        {label: "邮箱", prop: "jiankang_text"},
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
      const res = await getAllJiankang();
      const data = res.data.data
      console.log(data)
      // 必须要返回一个对象，包含data数组和total总数
      const count = data.reduce((counter, obj) => {
        return counter=counter+1;
      }, 0);
      return {
        data: data,
        total: +count,
      };
    };
    return {
      ...toRefs(state),
      getList,
    };
  },
});
</script>