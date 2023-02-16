<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(0, 92, 175)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 65px; line-height: 60px; text-align: center">
      <!--      <img src="../assets/logo.png" alt="" style="width: 60px; position: relative; top: 5px; right: 2px;left: 1px">-->
      <img src="../assets/logo.png" alt="" style="width: 65px;  top: 5px; right: 5px">
    </div>
    <el-menu-item index="/home">
        <i class="el-icon-s-home"></i>
        <span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/student">
        <i class="el-icon-s-custom"></i>
        <span slot="title">学生用户管理</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods:{

  }
}

</script>

<style scoped>
.el-menu--collapse span {
  visibility: hidden;
}


</style>