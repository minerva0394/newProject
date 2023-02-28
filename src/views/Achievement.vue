<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 300px" placeholder="请输入课程名" suffix-icon="el-icon-search" v-model="courseName" clearable></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
      <el-button type="primary" class="ml-5" @click="exp">导出 <i class="el-icon-bottom"></i></el-button>
    </div>


    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange" style="align: center; width: 60%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="stuNumber" label="学号" width="120" align="center"></el-table-column>
      <el-table-column prop="academicYear" label="学年" width="150" align="center"></el-table-column>
      <el-table-column prop="semester" label="学期" width="100" align="center"></el-table-column>
      <el-table-column prop="courseName" label="课程名" width="280" align="center"></el-table-column>
      <el-table-column prop="credit" label="学分" width="180" :formatter="rounding" align="center"></el-table-column>
      <el-table-column prop="performancePoint" label="绩点" width="100" align="center"></el-table-column>
      <el-table-column prop="achievement" label="成绩" align="center"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">查看<i class="el-icon-search"></i></el-button>

        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <el-dialog title="成绩信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="large">
        <el-form-item label="课程名">
          <el-input v-model="form.courseName" autocomplete="off" readonly ></el-input>
        </el-form-item>
        <el-form-item label="学年">
          <el-input v-model="form.academicYear" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="form.semester" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="成绩">
          <el-input v-model="form.achievement" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="绩点">
          <el-input v-model="form.performancePoint" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="任课老师">
          <el-input v-model="form.teacherName" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="考核方式">
          <el-input v-model="form.assessmentMethods" autocomplete="off" readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Achievement",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      courseName: "",
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      ids: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    formatRound(num) {
      return (Math.round(num * 100) / 100).toFixed(2);
    },
    rounding(row, column) {
      return parseFloat(row[column.property]).toFixed(1)
    },

    load() {
      this.request.get("/achievement/page/" + this.user.username, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          courseName: this.courseName,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },


    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },

    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },

    reset() {
      this.courseName = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    exp() {
      window.open("http://localhost:9091/achievement/export")
      // TODO 上传到服务器的时候记得改
      // window.open("http://120.24.6.28:9091/user/export")
    },

  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}

</style>
