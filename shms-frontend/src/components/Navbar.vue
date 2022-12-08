<template>
  <div class="common-layout">
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-menu class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff"
                 active-text-color="#ffd04b" @select="handleSelect" :ellipsis="false">
          <el-menu-item index="1" style="margin-left:3%">健康管理系统</el-menu-item>
          <div class="flex-grow"/>
          <el-sub-menu style="margin-right:3%">
            <template #title>你好，{{ username }}</template>
            <el-menu-item @click="handlelogout">
              退出登录
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>
      <el-container>
        <el-aside>
          <el-menu default-active="1" v-model="nowIndex" class="el-menu-vertical-demo" :collapse="isCollapse" router
                   style="height:100vh">
            <el-sub-menu>
              <template #title>
                <el-icon>
                  <location/>
                </el-icon>
                <span>个人中心</span>
              </template>
              <el-menu-item-group>
                <template #title><span>个人基本信息</span></template>
                <el-menu-item index="main">健康信息维护</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <location/>
                </el-icon>
                <span>核酸专区</span>
              </template>
              <el-menu-item-group>
                <template #title><span>核酸相关</span></template>
                <el-menu-item index="natbook">核酸预约</el-menu-item>
                <el-menu-item index="natquery">核酸信息查询</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item index="riskarea">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <template #title>中高风险地区查询</template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main>
            <el-row type="flex" justify="center" align="middle">
              <router-view></router-view>
            </el-row>
          </el-main>
          <el-footer>

          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref, onMounted, h} from 'vue'
import cookies from "vue-cookies";
import {useRouter} from "vue-router"
import {
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import {ElMessage, ElNotification} from 'element-plus'
import {getValidNotice} from "../api/api.js";


const nowIndex = ref("1-1")

const handleSelect = (key, keyPath) => {
  nowIndex.value = key
}
const username = ref(cookies.get("username"))

const router = useRouter()

const handlelogout = () => {
  cookies.remove('userid')
  cookies.remove('username')
  router.push('/login')
}

onMounted(() => {
  getValidNotice()
      .then((res) => {
        if (res.data.code === 200) {
          const notices = res.data.data
          console.log(notices)
          for (const notice of notices) {
            ElNotification({
              title: notice.notice_name,
              message: notice.notice_text,
              type: notice.notice_type,
              duration:0,
            })
          }
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
.el-aside {
  width: 200px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.el-header {
  padding: 0%
}

.flex-grow {
  flex-grow: 1;
}
</style>