<template>
  <div>
    <div style="padding-bottom: 20px">
      <b>欢迎你！{{ user.nickname }}</b>
    </div>
    <el-button type="primary"><i class="el-icon-link"></i> <a style="color: #fff;text-decoration: none" href="https://github.com/minerva0394/vue_springboot_final">项目源码</a></el-button>
    <el-row :gutter="30">
      <el-col :span="12">
        <div style="padding: 20px 0; font-size: 20px">技术栈</div>
        <el-row>
          <el-col :span="12" style="line-height: 30px">
            <div><b>后端</b></div>
            <div>SpringBoot2</div>
            <div>Hutool</div>
            <div>Lombok</div>
            <div>Mybatis/Mybatis-plus</div>
          </el-col>
          <el-col :span="12" style="line-height: 30px">
            <b>前端</b>
            <div>Vue2</div>
            <div>Vue-Router</div>
            <div>VueX</div>
            <div>ElementUI</div>
            <div>Axios</div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>你也可以选择给我打钱</span>
          </div>
          <div style="text-align: center;">
            <img src="../assets/IMG_9786.jpg" alt="" style="height: 50%;width: 50%"/>
            <img src="../assets/IMG_9789.jpg" alt="" style="height: 50%;width: 50%"/>
            <div class="pd-10"><h1>我是学生，打钱</h1></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },

}
</script>

<style scoped>

</style>