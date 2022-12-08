<template>
  <div class="loginbody">
    <div class="logindata">
      <div class="logintext">
        <h2>Welcome</h2>
      </div>
      <div class="formdata">
        <el-form :model="loginform" :rules="rules">
          <el-form-item prop="username">
            <el-input v-model="loginform.username" clearable placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginform.password" clearable placeholder="请输入密码" show-password></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="tool">
        <div>
          <el-checkbox v-model="checked" @change="remember">记住密码</el-checkbox>
        </div>
        <div>
          <span class="shou" @click="(drawer2 = !drawer2)">忘记密码？</span>
        </div>
      </div>
      <div class="butt">
        <el-button type="primary" @click="handlelogin">登录</el-button>
        <el-button class="shou" @click="(drawer = !drawer)">注册</el-button>
      </div>
    </div>
  </div>
  <el-drawer v-model="drawer" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <h4 :id="titleId" :class="titleClass">欢迎注册学生健康管理系统</h4>
      <el-button type="danger" @click="close">
        <el-icon class="el-icon--left">
          <CircleCloseFilled/>
        </el-icon>
        关闭
      </el-button>
    </template>
    <template #default>
      <template v-if="(registerStep == 0)">
        <el-form :model="registerform" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="registerform.username" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="registerform.password" clearable show-password>
            </el-input>
          </el-form-item>
          <el-form-item label="电子邮箱">
            <el-input v-model="registerform.email" clearable>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRegister"
                       :loading="registerIconLoading">Create
            </el-button>
            <el-button @click="drawer = !drawer">Cancel</el-button>
          </el-form-item>
        </el-form>
      </template>

      <template v-if="(registerStep == 1)">
        <el-form :model="registerform" label-width="200px">
          <el-form-item label="用户id">
            <el-input v-model="extendInfoForm.userid" disabled></el-input>
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input v-model="extendInfoForm.realname" clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="extendInfoForm.age" clearable>
              <template #append>岁</template>
            </el-input>
          </el-form-item>
          <el-form-item label="身高">
            <el-input v-model="extendInfoForm.height" clearable>
              <template #append>cm</template>
            </el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="extendInfoForm.gender" placeholder="Select">
              <el-option v-for="item in options" :key="item.value" :label="item.label"
                         :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="体重">
            <el-input v-model="extendInfoForm.weight" clearable>
              <template #append>kg</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleExtend"
                       :loading="registerIconLoading">Create
            </el-button>
            <el-button @click="drawer = !drawer">Cancel</el-button>
          </el-form-item>
        </el-form>
      </template>

      <template v-if="registerStep == 2">
        <el-result icon="success" title="注册成功!" sub-title="接下来去登陆吧">
        </el-result>
      </template>

    </template>
    <template #footer>
      <el-steps :active="registerStep" finish-status="success" simple>
        <el-step title="基本信息"/>
        <el-step title="完善健康资料"/>
      </el-steps>
    </template>
  </el-drawer>
  <!-- 以下为忘记密码drawer -->
  <el-drawer v-model="drawer2" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <h4 :id="titleId" :class="titleClass">忘记密码</h4>
      <el-button type="danger" @click="(drawer2 = !drawer2)">
        <el-icon class="el-icon--left">
          <CircleCloseFilled/>
        </el-icon>
        关闭
      </el-button>
    </template>
    <template #default>
      <el-form :model="resetform" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="resetform.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="验证码" :inline="true">
          <el-input v-model="resetform.validcode" clearable>
            <template #suffix>
              <el-button type="primary" @click="handleValidCode" :disabled="disablevalidcode">
                <template v-if="(disablevalidcode == false)">
                  获取验证码
                </template>
                <template v-else>
                  {{ pausetime }}秒
                </template>
              </el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="resetform.password" clearable show-password>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleReset" :loading="registerIconLoading">Create</el-button>
          <el-button @click="(drawer2 = !drawer2)">Cancel</el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-drawer>
</template>

<script setup>
import {h, reactive, ref} from 'vue';
import {ElMessage, ElMessageBox, ElButton, ElDrawer, ElNotification} from 'element-plus'
import {
  login,
  register,
  alterHealthData,
  getValidCode,
  validValidCode,
  redirectAdmin,
  getValidNotice
} from '../../api/api'
import cookies from "vue-cookies";
import {useRouter} from "vue-router"
import {CircleCloseFilled} from '@element-plus/icons-vue'

const options = [
  {
    value: 0,
    label: "男"
  },
  {
    value: 1,
    label: "女"
  }
]

const loginform = reactive({
  password: "",
  username: "",
})

const registerform = reactive({
  username: "",
  password: "",
  email: "",
})

const extendInfoForm = reactive({
  userid: "",
  realname: "",
  age: null,
  gender: 0,
  height: null,//cm
  weight: null,//kg
})

const resetform = reactive({
  userid: "",
  username: "",
  validcode: "",
  password: "",
})

const checked = ref(false)
const drawer = ref(false)
const drawer2 = ref(false)
const disablevalidcode = ref(false)
const pausetime = ref(5)
const registerIconLoading = ref(false)

const registerStep = ref(0)

const rules = {
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
    {max: 10, message: "不能大于16个字符", trigger: "blur"},
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {max: 10, message: "不能大于16个字符", trigger: "blur"},
  ],
}

const router = useRouter()

const handlelogin = () => {
  login(loginform)
      .then((res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          cookies.set('userid', res.data.data.userid)
          cookies.set('username', res.data.data.username)

          if (res.data.data.privilege === 1) {
            ElMessage({
              message: "管理员登录成功",
              type: "success",
              showClose: true,
            });
            router.push({
              path: "/admin"
            })
          } else {
            ElMessage({
              message: "用户登录成功",
              type: "success",
              showClose: true,
            });
            router.push({
              path: "/main"
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
}

const handleRegister = () => {
  registerIconLoading.value = true;
  register(registerform)
      .then((res) => {
        console.log(res.data)
        if (res.data.code == 200) {
          registerIconLoading.value = false
          ElMessage({
            message: "基本信息填写成功，接下来补充健康信息吧！",
            type: "success",
            showClose: true,
          });
          registerStep.value++
          extendInfoForm.userid = res.data.data.userid
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

const handleExtend = () => {
  registerIconLoading.value = true;
  alterHealthData(extendInfoForm)
      .then((res) => {
        console.log(res.data)
        if (res.data.code == 200) {
          registerIconLoading.value = false
          ElMessage({
            message: "已经全部完成啦，马上登录吧",
            type: "success",
            showClose: true,
          });
          registerStep.value++
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

const remember = (data) => {
  this.checked = data
  if (this.checked) {
    localStorage.setItem("news", JSON.stringify(this.form))
  } else {
    localStorage.removeItem("news")
  }
}


const handleValidCode = () => {
  disablevalidcode.value = true
  var timesstop = setInterval(() => {
    pausetime.value--;
    if (pausetime.value == 0) {
      clearInterval(timesstop)
      disablevalidcode.value = false
      pausetime.value = 5
    }
  }, 1000)
  getValidCode({username: resetform.username})
      .then((res) => {
        console.log(res.data)
        if (res.data.code == 200) {
          resetform.userid = res.data.data
          ElMessage({
            message: "邮件获取成功！",
            type: "success",
            showClose: true,
          });
          registerStep.value++
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
  registerIconLoading.value = true
  validValidCode(resetform)
      .then((res) => {
        console.log(res.data)
        if (res.data.code == 200) {
          ElMessage({
            message: "重置密码成功！",
            type: "success",
            showClose: true,
          });
          registerIconLoading.value = false
          drawer2.value = false
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

<style scoped>
.loginbody {
  width: 100%;
  height: 100%;
  min-width: 1000px;
  background-image: url("../../assets/login_background.jpeg");
  background-size: 100% 100%;
  background-position: center center;
  overflow: auto;
  background-repeat: no-repeat;
  position: fixed;
  line-height: 100%;
  padding-top: 150px;
}

.logintext {
  margin-bottom: 20px;
  line-height: 50px;
  text-align: center;
  font-size: 30px;
  font-weight: bolder;
  color: rgb(23, 40, 126);
  text-shadow: 2px 2px 4px #000000;
}

.logindata {
  width: 400px;
  height: 300px;
  transform: translate(-50%);
  margin-left: 50%;
}

.tool {
  display: flex;
  justify-content: space-between;
  color: #606266;
}

.butt {
  margin-top: 10px;
  text-align: center;
}

.shou {
  cursor: pointer;
  color: #606266;
}
</style>