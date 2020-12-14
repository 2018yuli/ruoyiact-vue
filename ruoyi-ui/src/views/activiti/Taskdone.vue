<template>
  <div class="app-container" >
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="任务名称">
        <el-input size="small" style="width: 240px" placeholder="请输入" v-model="queryParams.taskName" clearable />
      </el-form-item>
      <el-form-item label="已删除">
        <el-select size="small" style="width: 240px" v-model="queryParams.delFlag">
          <el-option :value="false" label="否">否</el-option>
          <el-option :value="true" label="是">是</el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          :disabled="selectedRowKeys.length == 0" 
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="delByIds(selectedRowKeys)"
        >删除</el-button>
      </el-col>
      <div class="top-right-btn">
        <el-tooltip class="item" effect="dark" content="刷新" placement="top">
          <el-button size="mini" circle icon="el-icon-refresh" @click="handleQuery" />
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
          <el-button size="mini" circle icon="el-icon-search" @click="showSearch=!showSearch" />
        </el-tooltip>
      </div>
    </el-row>

    <el-table size="medium" ref="table" rowKey="auditId" v-loading="loading" :data="taskData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" />
      <el-table-column label="#" width="45">
        <span slot-scope="scope">
          {{ scope.$index + 1 }}
        </span>
      </el-table-column>
      <el-table-column label="申请标题" prop="title"></el-table-column>
      <el-table-column label="所属流程" prop="procName"></el-table-column>
      <el-table-column label="任务名称" prop="taskName"></el-table-column>
      <el-table-column label="申请人" prop="applyer"></el-table-column>
      <el-table-column label="审批结果" prop="result">
        <span slot-scope="text">
          {{ text |resultFilter }}
        </span>
      </el-table-column>
      <el-table-column label="耗时" prop="duration">
        <span slot-scope="scope">
          {{ scope.row.duration | timeFilter }}
        </span>
      </el-table-column>
      <el-table-column label="指派时间" prop="startTime"></el-table-column>
      <el-table-column label="审批时间" prop="endTime"></el-table-column>
      <el-table-column label="操作">
        <span slot-scope="scope">
          <el-button type="text" size="mini" icon="el-icon-edit" v-if="editEnabel" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-button type="text" size="mini" @click="handelAudit(scope.row)">详情</el-button>
          <el-button type="text" size="mini" @click="handFlow(scope.row.procInstId)">历史</el-button>
          <el-button type="text" size="mini" v-if="!scope.row.delFlag" @click="delByIds([scope.row.auditId])">删除</el-button>
        </span>
      </el-table-column>
    </el-table>
    <leave-modal ref="leavemodal" @ok="handleOk" :audit="audit"/>
    <purchase-modal ref="purchasemodal" @ok="handleOk" :audit="false"/>
    <proc-flow-modal ref="modal" @ok="handleOk"/>
    <pms-task-modal ref="tskmodal" @ok="handleOk" />
  </div>
</template>

<script>
import { millsToTime } from '@/utils/util'
import { getTaskdone, delTaskLogic, processInsState, modalMap } from '@/api/activiti'
import LeaveModal from './modules/LeaveModal.vue'
import PurchaseModal from './modules/PurchaseModal.vue'
import ProcFlowModal from './modules/ProcFlowModal.vue'
import PmsTaskModal from './modules/PmsTaskModal.vue'
const resultMap = {
  '1': '处理中',
  '2': '通过',
  '3': '驳回',
  '4': '撤销'
}
export default {
  name: 'Tasking',
  components: {
    LeaveModal,
    ProcFlowModal,
    PurchaseModal,
    PmsTaskModal
  },
  data () {
    return {
      visible: false,
      editEnabel: true,
      loading: true,
      form: {},
      mdl: {},
      permissions: [],
      // 高级搜索 展开/关闭
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: undefined,
        delFlag: false 
      },
      // 加载数据方法
      taskData: [],
      audit: false,
      previewVisible: false,
      previewImage: '',
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  computed: {
  },
  filters: {
    resultFilter (status) {
      return resultMap[status]
    },
    timeFilter (mills) {
      return millsToTime(mills)
    }
  },
  created () {
    this.getList()
  },
  methods: {
    getList(){
      this.loading = true;
      getTaskdone(this.queryParams).then((response) => {
        this.taskData = response.data.rows
        this.loading = false
      })
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.selectedRowKeys = selection.map(item => item.id)
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
     /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.taskName = undefined;
      this.queryParams.delFlag = false;
      this.handleQuery();
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    handFlow (procInstId) {
      this.$refs.modal.show(procInstId)
    },
    handleEdit (record) {
      this.msgSuccess('想什么呢！');
    },
    handelAudit (record) {
      const modal = modalMap[record.procDefKey]
      this.$refs[modal].show(record)
    },
    handleState (record) {
      const state = record.isSuspended ? 'active' : 'suspend'
      processInsState(record.procInstId, state).then(res => {
        if (res.code === 0) {
          record.isSuspended = !record.isSuspended
        }
      })
    },
    delByIds (ids) {
      if (ids.length > 0) {
        delTaskLogic({ ids: ids.join(',') }).then(res => {
          if (res.code === 200) {
            this.msgSuccess(`删除成功`)
            this.resetQuery()
          } else {
            this.$message.error(res.msg)
          }
        })
      } else {
        this.$message.error('没有选择数据')
      }
      this.selectedRowKeys = []
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
