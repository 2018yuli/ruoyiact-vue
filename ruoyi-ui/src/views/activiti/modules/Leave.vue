<template>
  <div class="app-container" >
    <el-row type="flex" justify="center">
      <el-col :span="9">
        <el-form :model="form" ref="form" :rules="rules" label-width="80px" size="small">
          <el-form-item
            label="请假类型">
            <el-select style="width: 90%" placeholder="类型" v-model="form.type">
              <el-option 
                v-for="(d, index) in leaveTypes" 
                :key="index"
                :label="d.dictLabel"
                :value="d.dictValue">
                {{ d.dictLabel }}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="标题">
            <el-input style="width: 90%" v-model="form.title"
              placeholder="请假标题" />
          </el-form-item>
          <el-form-item
            label="起止日期">
            <el-date-picker
              format="yyyy-MM-dd HH:mm"
              v-model="form.buildTime"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              >
            </el-date-picker>
          </el-form-item>
          <el-form-item
            label="时长">

            <el-input
              style="width: 150px;"
              v-model="form.duration"
              disabled
            >
              <template slot="append">小时</template>
            </el-input>
          </el-form-item>
          <el-form-item
            label="描述">
            <el-input
              type="textarea"
              placeholder="请输入描述"
              v-model="form.description"
              maxlength="30"
              show-word-limit
            >
            </el-input>
          </el-form-item>
          <el-form-item
            style="text-align: center"
          >
            <el-button type="primary" :loading="confirmLoading" @click="handleSubmit" >提交</el-button>
            <router-link :to="{path:'/system/activiti/businessList'}">
              <el-button style="margin-left: 8px">返回</el-button>
            </router-link>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
import { saveLeave } from '@/api/activiti'
import { getDicts } from "@/api/system/dict/data";
import { parseTime } from "@/utils/ruoyi"
export default {
  name: 'LeaveForm',
  data () {
    return {
      confirmLoading: false,
      leaveTypes: [],
      formData: {},
      // form
      form: {},
      // 字段表单校验
      rules: {
        type: [{ required: true, message: '请选择请假类型', trigger: "blur"  }],
        title: [{ required: true, message: '请输入标题', trigger: "blur" }],
        buildTime: [{ required: true, message: '请选择起止日期' , trigger: "blur" }],
        duration: [{  required: true}],
        description: [{ required: true, message: '请输入描述' }]
      },
      startDate: new Date(),
      pickerOptions: {
        disabledDate: (time) => {
          if(this.startDate != null) {
              return time.getTime() < this.startDate.getTime() || time.getTime() >= this.startDate.getTime() + 7 * 8.64e7
          }
          return false;
        },
        onPick: (dateRange) => {
            this.form.startDate = parseTime(dateRange.minDate)
            this.form.endDate = parseTime(dateRange.maxDate)
            if (dateRange.minDate && dateRange.maxDate)
              this.onOk([dateRange.minDate, dateRange.maxDate])
        }
      },
    }
  },
  async created () {
    const query = this.$route.query
    //this.formData = Object.assign(pick(query, 'key', 'procDefId', 'procName'))
    this.form = {
      key: query.key, 
      procDefId: query.procDefId, 
      procName: query.procName
    }
    getDicts('act_leave_type').then(res => {
      this.leaveTypes = res.data
    })
  },
  methods: {
    onOk (v) {
      if (v[0]) {
        const dateBegin = v[0]
        const dateEnd = v[1]
        const dateDiff = dateEnd.getTime() - dateBegin.getTime()
        const hours = Math.floor(dateDiff / (3600 * 1000))
        this.form.duration = hours
        console.log(hours);
        //this.formData = Object.assign(this.formData, { startDate: dateBegin.format('YYYY-MM-DD HH:mm:00'), endDate: dateEnd.format('YYYY-MM-DD HH:mm:00') })
      } else {
        this.form.duration = 0
        //this.formData = Object.assign(this.formData, { startDate: '', endDate: '' })
      }
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.confirmLoading = true
          saveLeave(this.form).then(res => {
            if (res.code === 200) {
              this.msgSuccess('保存成功')
              this.$router.push({ path: '/system/activiti/businessList' })
            } else {
              this.$message.error(res.msg)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      });
    }
  }
}
</script>
