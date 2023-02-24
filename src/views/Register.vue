<template>
  <div class="wrapper">
    <div class="loginTest"
         style="margin: 200px auto; background-color: rgba(255,255,255,0.65); width: 370px; height: 520px; padding: 20px; border-radius: 10px;">
      <div style="margin: 15px 0; text-align: center; font-size: 24px;color: #587284FF"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model="user.username"
                    clearable></el-input>
        </el-form-item>
        <el-form-item prop="nickname">
          <el-input placeholder="请输入昵称" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model="user.nickname"
                    clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password" clearable></el-input>
        </el-form-item>
        <el-form-item prop="comfirmPassword">
          <el-input placeholder="确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.comfirmPassword" clearable></el-input>
        </el-form-item>

        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" size="medium" v-model="user.validCode" style="width: 50%;margin: 5px 0"
                      placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode"/>
          </div>
        </el-form-item>


        <el-form-item style="margin: 40px 0; text-align: right">
          <el-button type="primary" size="large" autocomplete="off" @click="login">注册</el-button>
          <el-button type="warning" size="large" autocomplete="off" @click="toLogin">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import ValidCode from "@/components/ValidCode";

export default {
  components: {
    ValidCode,
  },
  props: {
    width: {
      type: String,
      default: '100px'
    },
    height: {
      type: String,
      default: '34px'
    },
    length: {
      type: Number,
      default: 4
    }
  },
  name: "Login",
  data() {
    return {
      codeList: [],
      user: {},
      validCode: '',

      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
        ],
        nickname: [
          {required: true, message: '请输入昵称', trigger: 'blur'},
          {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        comfirmPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  mounted() {
    this.createdCode()
  },
  methods: {
    createValidCode(data) {
      this.validCode = data
    },
    refreshCode() {
      this.createdCode()
    },
    toLogin() {
      this.$router.push('/login')
      this.load()
    },

    getStyle(data) {
      return `color: ${data.color}; font-size: ${data.fontSize}; padding: ${data.padding}; transform: ${data.transform}`
    },
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法

          if (!this.user.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if(this.user.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }

          if (this.user.password !== this.user.comfirmPassword) {
            this.$message.error("两次输入密码不一致")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === '200') {
              this.$message.success("注册成功！")
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

.ValidCode {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

span {
  display: inline-block;
}


</style>

