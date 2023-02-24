<template>
  <div>
    <div>
      <el-row>
        <el-col :span="4">&nbsp;</el-col>
        <el-col :span="8" class="table-header">成绩(输入百分之成绩)</el-col>
        <el-col :span="8" class="table-header">学分(不是绩点，是学分)</el-col>
      </el-row>
      <transition-group>
        <div class="row-item" v-for="(item, index) in input" :key="item">
          <el-form :model="item" :rules="rules" ref="item">
            <el-row :gutter="10">
              <el-col :span="4" class="table-header">
                科目{{ index + 1 }}
              </el-col>
              <el-col :span="8">
                <el-form-item prop="grade">
                  <el-input v-model="item.grade"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="credit">
                  <el-input v-model="item.credit"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button @click.prevent="removeItem(index)" :disabled="stopRemove" icon="el-icon-delete"></el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </transition-group>
      <el-row class="controler-wrapper">
        <el-col :span="4">&nbsp;</el-col>
        <el-col :span="20">
          <div @click="calculateGPA()" class="query-dialog-controler">
            <query-result-dialog :statusNo="queryStatusNo" :gpa="gpa" ></query-result-dialog>
          </div>
          <el-button @click="addItem()">新增科目</el-button>
          <el-button @click="resetInput()">重置</el-button>
        </el-col>
      </el-row>
    </div>
    <el-row >
      <el-col :span="12">
        <div id="main" style="weight:500px;height: 600px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="weight:500px;height: 600px;"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import queryResultDialog from  '../views/queryResult/queryResult.vue';

const STATUS_OK = 0
const STATUS_ERR = 1
const STATUS_EMPTY = 2

let checkGrade = function(rules, value, callback) {
  let grade = parseInt(value, 10)
  setTimeout(function() {
    if (!Number.isInteger(grade) && value !== '') {
      callback(new Error('请输入数字值'))
    } else {
      if (grade > 100 || grade < 0) {
        callback(new Error('必须介于0～100'))
      } else {
        callback()
      }
    }
  }, 800)
}
let checkCredit = function(rules, value, callback) {
  let credit = parseInt(value, 10)
  setTimeout(function() {
    if (!Number.isInteger(credit) && value !== '') {
      callback(new Error('请输入数字值'))
    } else {
      if (credit > 10 || credit < 0) {
        callback(new Error('必须介于0～10'))
      } else {
        callback()
      }
    }
  }, 800)
}

export default {
  components:{
    'query-result-dialog': queryResultDialog
  },
  name: "Dashbord",
  data() {
    return {
      gpa: -1,
      rowNum: 5,
      stopRemove: false,
      input: [{
        grade: '',
        credit: ''
      }, {
        grade: '',
        credit: ''
      }, {
        grade: '',
        credit: ''
      }, {
        grade: '',
        credit: ''
      }, {
        grade: '',
        credit: ''
      }],
      queryStatusNo: -1,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        grade: [{
          validator: checkGrade,
          trigger: 'change,blur'
        }],
        credit: [{
          validator: checkCredit,
          trigger: 'change,blur'
        }]
      }
    }
  },
  methods:{
    addItem() {
      if (this.stopRemove) {
        this.stopRemove = false
      }
      this.input.push({
        grade: '',
        credit: ''
      })
      this.rowNum++
    },
    removeItem(index) {
      if (this.rowNum <= 2) {
        this.stopRemove = true
      }
      this.input.splice(index, 1)
      this.rowNum--
    },
    resetInput() {
      for (let i = 0; i < this.input.length; i++) {
        this.input[i].grade = ''
        this.input[i].credit = ''
      }
    },
    getGpaFromPoint(point) {
      let gpa = 0
      switch (true) {
        case (point <= 100) && (point >= 90):
          gpa = 4
          break
        case (point < 90) && (point >= 80):
          gpa = 3
          break
        case (point < 80) && (point >= 70):
          gpa = 2
          break
        case (point < 70) && (point >= 60):
          gpa = 1
          break
        case (point < 60) && (point >= 0):
          gpa = 0
          break
        default:
          gpa = -1
          break
      }
      return gpa
    },
    calculateGPA() {
      let i = 0
      let flag = 1
      let up = 0
      let down = 0
      let input = this.input
      let getGpaFromPoint = this.getGpaFromPoint
      console.log("进入循环,rowNum="+this.rowNum)
      for (i = 0; i < this.rowNum; i++) {
        if (input[i].grade === '' && input[i].credit === '') {
          continue
        }
        if ((parseInt(input[i].grade, 10) >= 0 && parseInt(input[i].grade, 10) <= 100) && (parseInt(input[i].credit, 10) >= 0 && parseInt(input[i].credit, 10) <= 10)) {
          up += (getGpaFromPoint(parseInt(input[i].grade, 10)) * parseInt(input[i].credit, 10))
          down += parseInt(input[i].credit, 10)
        } else {
          flag = 0
          break
        }
        console.log("此时的input[i].grade="+this.input[i].grade)
        console.log("此时的input[i].credit="+this.input[i].credit)
        console.log("此时的flag="+flag)
      }
      if (flag === 0) {
        this.queryStatusNo = STATUS_ERR
        console.log("程序错误"+this.queryStatusNo)
      } else if (isNaN(up * 4 / down * 100)) {
        this.queryStatusNo = STATUS_EMPTY
        console.log("空表报警"+this.queryStatusNo)
      } else {
        this.queryStatusNo = STATUS_OK
        this.gpa = (up / down).toFixed(2)
        console.log('此时输出' + this.queryStatusNo + 'gpa:' + this.gpa)
      }
    }
  },
  mounted() {
    // 页面元素渲染后触发
    var option = {
      title: {
        text: '当前已有绩点',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一学期", "第二学期", "第三学期", "第四学期", "第五学期", "第六学期", "第七学期", "第八学期"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "绩点—折线",
          data: [this.user.gpa1st, this.user.gpa2nd, this.user.gpa3rd, this.user.gpa4th, this.user.gpa5th, this.user.gpa6th, this.user.gpa7th, this.user.gpa8th],
          type: 'line'
        },
        {
          name: "绩点-柱状图",
          data: [this.user.gpa1st, this.user.gpa2nd, this.user.gpa3rd, this.user.gpa4th, this.user.gpa5th, this.user.gpa6th, this.user.gpa7th, this.user.gpa8th],
          type: 'bar'
        }
      ]
    };
    //ToDo 改成预测绩点的折线图
    var pieOption = {
      title: {
        text: '各季度学校教职工及学生总数',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '60%',
          label: {            //饼图图形上的文本标签
            normal: {
              show: true,
              position: 'inner', //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 14,    //文字的字体大小
                color: "#fff"
              },
              formatter: '{d}%'
            }
          },
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/echarts/haveGPA").then(res => {
      //todo 获取当前用户信息
      // var userDataUserName = this.user.gpa3rd
      // console.log(userDataUserName)
      // 请求数据
      // option.xAxis.data = res.data.x
      // todo 修改左侧折线图数据
      // option.series[0].data = res.data
      // todo 修改左侧柱状图数据
      // option.series[1].data = res.data
      myChart.setOption(option);
      // console.log(typeof res.data)


      // pieOption.series[0].data = [
      //   {name: "第一季度", value: res.data[0]},
      //   {name: "第二季度", value: res.data[1]},
      //   {name: "第三季度", value: res.data[2]},
      //   {name: "第四季度", value: res.data[3]}
      // ]
      // pieChart.setOption(pieOption)
    })
  }

}
</script>

<style lang="stylus"  rel="stylesheet/stylus" scoped>
@import "../common/stylus/mixin.styl"
.table-header
  text-align center
  font-size 1.2em
  line-height 36px
.controler-wrapper
  padding-bottom 300px
  .query-dialog-controler
    display inline-block
    margin-right 10px
.slide-enter-active
  transition all .65s ease
.slide-leave-active
  transition all .65s ease
.slide-enter,
.slide-leave-active
  transform translate3d(500px, 0, 0)
  opacity 0


</style>
