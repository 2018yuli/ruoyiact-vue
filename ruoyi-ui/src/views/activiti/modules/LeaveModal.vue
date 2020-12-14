<template>
  <el-dialog
    title="操作"
    style="top: 20px;"
    width="80%"
    :visible.sync="visible"
  >

    <el-form :model="form">

      <el-form-item
        label="请假类型"
      >
        <span>{{ mdl.leaveType }}</span>
      </el-form-item>

      <el-form-item
        label="标题"
      >
        <span class="spanClass">{{ mdl.title }}</span>
      </el-form-item>

      <el-form-item
        label="描述">
        <el-input
          type="textarea"
          disabled
          v-model="mdl.description"
        />
      </el-form-item>
      <el-form-item
        label="起止日期">
        <span> {{ mdl.startDate }}   &nbsp;&nbsp;至&nbsp;&nbsp;   {{ mdl.endDate }}</span>
      </el-form-item>

      <el-form-item
        label="时长"
      >
        <span>{{ mdl.duration }} 小时</span>
      </el-form-item>

      <el-form-item
        label="审批意见"
        v-show="audit"
      >
        <el-input
          type="textarea"
          :rows="2"
          v-model="formData.comment"
        />
      </el-form-item>

      <el-form-item
        style="text-align: center"
        v-show="audit"
      >
        <el-button type="primary" :loading="confirmLoading" @click="handleSubmit(2)">同意</el-button>
        <el-button style="margin-left: 8px" @click="handleSubmit(3)">驳回</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
import { getLeaveByBusiness, auditTask } from '@/api/activiti'
import { getDicts } from "@/api/system/dict/data";
export default {
  name: 'LeaveModal',
  props: {
    audit: {
      type: Boolean,
      default: true
    }
  },
  components: {

  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      mdl: {},
      options: [],
      dateFormat: 'YYYY-MM-DD HH:mm',
      formData: {},
      leaveTypeMap: {},
      form: {},
      rules:[]
    }
  },
  beforeCreate () {
    getDicts('act_leave_type').then(res => {
      this.leaveTypeMap = res.data
    })
  },
  created () {
  },
  methods: {
    show (record) {
      // record 是任务 如果只是查看可以只传入一个businessKey
      if (this.audit) {
        this.formData = Object.assign(
          {
            taskId: record.id,
            procInstId: record.procInstId,
            procName: record.processDefName,
            businessKey: record.businessKey,
            procDefKey: record.processDefKey,
            applyer: record.applyer
          }
        )
      }
      getLeaveByBusiness(record.businessKey).then(res => {
        if (res.code === 200) {
          const { type } = res.data
          this.mdl = Object.assign(res.data)
          this.mdl.leaveType = this.leaveTypeMap.find(l => l.dictValue == type.toString()).dictLabel
          this.visible = true
        }
      })
    },
    handleSubmit (result) {
      this.confirmLoading = true
      const formData = this.formData
      formData.result = result
      auditTask(formData).then(res => {
        if (res.code === 200) {
          this.msgSuccess('审批成功')
          this.$emit('ok')
          this.visible = false
        } else {
          this.$message.success(res.msg)
        }
      }).catch(() => {
        this.$message.error('系统错误，请稍后再试')
      }).finally(() => {
        this.confirmLoading = false
      })
    }
  },
  watch: {
    /*
      'selectedRows': function (selectedRows) {
        this.needTotalList = this.needTotalList.map(item => {
          return {
            ...item,
            total: selectedRows.reduce( (sum, val) => {
              return sum + val[item.dataIndex]
            }, 0)
          }
        })
      }
      */
  }
}
</script>
<style scoped>
.ant-input-disabled{
    color: #000;
    background-color: #fff;
}
.ant-select-disabled{
    color: #000;
}
.ant-select-selection{
    background-color: #fff;
}
</style>
