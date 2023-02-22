<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 20%; height: 35%; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="student" :rules="rules" ref="studentForm">
        <el-form-item prop="studentName">
          <el-input size="large" style="margin: 3% 0" prefix-icon="el-icon-user" v-model="student.studentName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="large" style="margin: 3% 0" prefix-icon="el-icon-lock" show-password
                    v-model="student.password"></el-input>
        </el-form-item>
        <!-- 验证码-->
        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" size="large" v-model="student.validCode"
                      style="width: 50%;margin: 5px 0"
                      placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode"/>
          </div>
        </el-form-item>

        <el-form-item style="margin: 7% 0; text-align: right;float:bottom;">
          <el-button type="primary" size="large" autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="large" autocomplete="off">注册</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import ValidCode from "../components/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode,
  },
  data() {
    return {
      validCode: '',
      student: {}
    }
  },
  methods: {
    createValidCode(data) {
      this.validCode = data
    },
    login: function () {
      this.$refs['studentForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (!this.student.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if (this.student.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          this.request.post("/student/login", this.student).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))

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
  background-color: #0093E9;
  background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
  overflow: hidden;
}
</style>