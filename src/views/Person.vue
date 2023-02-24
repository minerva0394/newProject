<template>
  <el-card style="width: 500px; border: 1px solid #ccc">
    <el-form label-width="80px" size="large">

      <el-upload
          class="avatar-uploader"
          action="http://localhost:9091/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess">
        <img v-if="form.avatar" :src="form.avatar" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名:">
        <el-input v-model="form.username" autocomplete="off" disabled></el-input>
      </el-form-item>
      <el-form-item label="密码:">
        <el-input v-model="form.password" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称:">
        <el-input v-model="form.nickname" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item label="邮箱:">
        <el-input v-model="form.email" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item label="电话:">
        <el-input v-model="form.phone" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item label="学籍:">
        <el-input type="textarea" v-model="form.address" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res =>{
      console.log(res)
      this.form = res
    })

  },
  methods: {
    async getUser(){
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          // 触发父级更新
          this.$emit("refreshUser")
        } else {
          this.$message.error("保存失败")
        }
      })
      this.load()
    },
    handleAvatarSuccess(res){
      this.form.avatar = res
    }
  }
}
</script>

<style scoped>
.avatar-uploader{
  text-align: center;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 170px;
  height: 170px;
  line-height: 170px;
  text-align: center;
}

.avatar {
  width: 170px;
  height: 170px;
  display: block;
}

</style>
