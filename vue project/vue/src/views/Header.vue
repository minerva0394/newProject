<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1; ">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>
      <b style="color: black" class="ml-5">学生信息管理系统</b>
    </div>


    <el-dropdown style="width: 70px; cursor: pointer">
      <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    pathName: String,
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  methods: {
    collapse() {
      this.$emit("asideCollapse")
    }
  }
}
</script>

<style scoped>

</style>