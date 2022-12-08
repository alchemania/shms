<template>
  <div class="common-layout">
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-menu class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff"
                 active-text-color="#ffd04b" @select="handleSelect" :ellipsis="false">
          <el-menu-item index="1" style="margin-left:3%">健康管理系统 Admin</el-menu-item>
          <div class="flex-grow"/>
          <el-sub-menu style="margin-right:3%">
            <template #title>你好，管理员 {{ username }}</template>
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
            <el-menu-item index="admin">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <template #title>用户管理</template>
            </el-menu-item>

            <el-menu-item index="jiankang">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <template #title>健康管理</template>
            </el-menu-item>

            <el-menu-item index="student">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <template #title>学生管理</template>
            </el-menu-item>

            <el-menu-item index="notice">
              <el-icon>
                <icon-menu/>
              </el-icon>
              <template #title>公告管理</template>
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
import {ref} from 'vue'
import cookies from "vue-cookies";
import {useRouter} from "vue-router"
import {
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'

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