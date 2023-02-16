<template>
  <div>

    <div style="margin: 10px 0">
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入学生学号" suffix-icon="el-icon-search"
                v-model="studentNo" clearable></el-input>
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入学生名称" suffix-icon="el-icon-search"
                v-model="studentName" clearable></el-input>
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入学生性别" suffix-icon="el-icon-search"
                v-model="studentSex" clearable></el-input>
      <el-input style="width: 200px;margin-right: 10px" placeholder="请输入学生专业" suffix-icon="el-icon-search"
                v-model="studentMajor" clearable></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div class="ml-5" style="margin-bottom: 10px">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text="好的"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确定批量删除这些数据?"
          @confirm="batchDelete"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:8085/student/import" :show-file-list="false" accept="xlsx"
                 :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="export_student" class="ml-5">导出 <i class="el-icon-bottom"></i></el-button>
      <el-button type="primary" @click="downloadExcel">下载模板 <i class="el-icon-bottom"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40px"></el-table-column>
      <el-table-column prop="studentNo" label="学生学号" width="120"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="120"></el-table-column>
      <el-table-column prop="studentSex" label="学生性别" width="80"></el-table-column>
      <el-table-column prop="studentCollege" label="学院"></el-table-column>
      <el-table-column prop="studentMajor" label="专业"></el-table-column>
      <el-table-column prop="studentGpa" label="绩点"></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="好的"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="确定删除?"
              @confirm="handleDelete(scope.row.studentNo)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页查询-->
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="20%">
      <el-form label-width="80px" size="small">
        <el-form-item label="学生学号">
          <el-input v-model="form.studentNo" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="form.studentName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学生性别">
          <el-select v-model="form.studentSex" placeholder="请选择学生性别" width="80px">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.studentMajor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="绩点">
          <el-input v-model="form.studentGpa" autocomplete="off"></el-input>
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
  name: "StudentVue",
  props: {
    collapseBtnClass: String,
    collapse: Function,
  },
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      studentName: "",
      studentNo: "",
      studentSex: "",
      studentMajor: "",
      studentGpa: "",
      multipleSelection: [],
      dialogFormVisible: false,
      form: {},
      headerBg: 'headerBg',

    }
  },
  created() {
    //  请求分页查询数据
    this.load()
  },
  methods: {
    load() {
      this.request.get("/student/page?", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          studentName: this.studentName,
          studentNo: this.studentNo,
          studentSex: this.studentSex,
          studentMajor: this.studentMajor
        }
      }).then(res => {
        this.total = res.total
        this.tableData = res.records
        console.log(this.tableData, this.total)
      })
    },
    reset() {
      this.studentName = ""
      this.studentSex = ""
      this.studentNo = ""
      this.studentMajor = ""
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
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    handleDelete(studentNo) {
      this.request.delete("/student/delete/" + studentNo).then(res => {
        if (res) {
          this.$message.success("删除成功")
          //设置删除最后一页的时候跳转
          this.pageNum = this.pageSize
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    batchDelete() {
      let ids = this.multipleSelection.map(v => v.studentNo)
      this.request.post("/student/batchDelete", ids).then(res => {
        if (res) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    save() {
      this.request.post("/student/save", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功")
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    export_student() {
      // todo 使用服务器的时候记得改ip
      window.open("http://localhost:8085/student/export")
    },
    downloadExcel() {
      // todo 使用服务器的时候记得改ip
      window.open("http://localhost:8085/student/export/template")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}

.el-input {
  width: 80px;
}

.el-select {
  width: 100%;
}
</style>