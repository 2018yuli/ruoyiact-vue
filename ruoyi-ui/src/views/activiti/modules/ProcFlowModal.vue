<template>
  <el-dialog
    title="操作"
    style="top: 20px;"
    width="100%"
    :visible.sync="visible"
  >
    <el-table ref="table" rowKey="id" :data="taskList">
      <el-table-column label="#">
        <span slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
      </el-table-column>
      <el-table-column label="申请标题" prop="title"></el-table-column>
      <el-table-column label="任务名称" prop="taskName"></el-table-column>
      <el-table-column label="审批人" prop="auditor"></el-table-column>
      <el-table-column label="审批结果" prop="result">
        <span slot-scope="text">
          {{ text | resultFilter }}
        </span>
      </el-table-column>
      <el-table-column label="耗时" prop="duration">
        <span slot-scope="text">
          {{ text | timeFilter }}
        </span>
      </el-table-column>
      <el-table-column label="指派时间" prop="startTime"></el-table-column>
      <el-table-column label="审批时间" prop="endTime"></el-table-column>
    </el-table>
    <img style="" :src="procFlowUrl" />
  </el-dialog>
</template>
<script>
import { millsToTime } from '@/utils/util'
import { getTaskFlow, hightlightFlow } from '@/api/activiti'
const resultMap = {
  '1': '处理中',
  '2': '通过',
  '3': '驳回',
  '4': '撤销'
}
export default {
  name: 'ProcFlowModal',
  props: {
  },
  components: {
  },
  data () {
    return {
      visible: false,
      loading: false,
      procFlowUrl: '',
      taskList: [],
    }
  },
  beforeCreate () {
  },
  created () {
  },
  filters: {
    resultFilter (type) {
      return type ? resultMap[type] : '待审批'
    },
    timeFilter (mills) {
      return millsToTime(mills)
    }
  },
  methods: {
    show (procInstId) {
      this.loading = true
      getTaskFlow({ procInstId: procInstId }).then(res => {
        if (res.code === 0) {
          this.taskList = res.rows
          hightlightFlow(procInstId).then(data => {
            const raw = data.data
            this.procFlowUrl = URL.createObjectURL(raw)
          })
          this.visible = true
        }
      }).finally(
        this.loading = false
      )
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
