<template>
  <div class="wrapper">
    <div class="loginTest"
         style="margin: 200px auto; background-color: rgba(255,255,255,0.65); width: 350px; height: 360px; padding: 20px; border-radius: 10px;">
      <div style="margin: 20px 0; text-align: center; font-size: 24px;color: #587284FF"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model="user.username"
                    clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" size="medium" v-model="user.validCode" style="width: 50%;margin: 5px 0"
                      placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode"/>
          </div>
        </el-form-item>

        <el-form-item style="margin: 20px 0; text-align: right">
          <el-button type="primary" size="large" autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="large" autocomplete="off" @click="toRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRouter} from "@/router";
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode,
  },
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 2, max: 12, message: '长度在 2 到 12 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      },
      validCode: ''
    }
  },
  methods: {
    createValidCode(data) {
      this.validCode = data
    },
    toRegister() {
      this.$router.push('/register')
      this.load();
    },
    login: function () {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (!this.user.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if (this.user.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))

              // 动态设置当前用户路由
              setRouter()

              this.$router.push("/")
              this.$message.success("登录成功！")
            } else {
              this.$message.error("用户名或密码错误")
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  width: 100%;
  background-size: cover;
  background-image: url("../assets/bg.jpg");
  overflow: hidden;
}

.loginTest {
  opacity: 0.45;
}

.loginTest:hover {
  opacity: 1;
}


</style>

