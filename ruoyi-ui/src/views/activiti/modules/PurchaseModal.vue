<template>
  <el-dialog
    title="操作"
    style="top: 20px;"
    width="80%"
    :visible.sync="visible"
  >

    <el-form :model="form">

      <el-form-item
        label="标题"
      >
        <span class="spanClass">{{ mdl.title }}</span>
      </el-form-item>

      <el-form-item
        label="描述">
        <el-input rows="2" type="textarea" v-model="mdl.description" placeholder=""></el-input>
      </el-form-item>

      <el-form-item
        label="金额"
      >
        <span>{{ mdl.money }} 元</span>
      </el-form-item>

      <el-form-item
        label="审批意见"
        v-show="audit"
      >
        <el-input type="textarea" rows="2" v-model="formData.comment" placeholder=""></el-input>
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
import { getPurchaseByBusiness, auditTask } from '@/api/activiti'
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
      form: {}
    }
  },
  beforeCreate () {
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
      getPurchaseByBusiness(record.businessKey).then(res => {
        if (res.code === 0) {
          this.mdl = Object.assign(res.data)
          this.visible = true
        }
      })
    },
    handleSubmit (result) {
      this.confirmLoading = true
      const formData = this.formData
      formData.result = result
      auditTask(formData).then(res => {
        if (res.code === 0) {
          this.$message.success('保存成功')
          this.$emit('ok')
          this.visible = false
        } else {
          this.$message.success(res.msg)
        }
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
