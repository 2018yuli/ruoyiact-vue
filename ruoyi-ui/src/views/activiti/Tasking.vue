<template>
  <div class="app-container" >
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="流程定义key">
        <el-input 
          size="small"
          style="width: 240px"
          placeholder="请输入" v-model="queryParams.processDefKey" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
       <!-- <el-col :span="1.5">
        <el-button 
          :disabled="selectedRowKeys.length===0"
          type="primary" 
          size="mini" 
          icon="el-icon-circle-check" 
          @click="handelAuditBatch(selectedRowKeys,2)">批量同意</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          :disabled="selectedRowKeys.length == 0" 
          type="danger"
          icon="el-icon-circle-close"
          size="mini"
          @click="handelAuditBatch(selectedRowKeys,3)"
        >批量驳回</el-button>
      </el-col> -->
      <div class="top-right-btn">
        <el-tooltip class="item" effect="dark" content="刷新" placement="top">
          <el-button size="mini" circle icon="el-icon-refresh" @click="handleQuery" />
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
          <el-button size="mini" circle icon="el-icon-search" @click="showSearch=!showSearch" />
        </el-tooltip>
      </div>
    </el-row>

    <el-table ref="table" rowKey="id" v-loading="loading" @selection-change="handleSelectionChange" :data="taskingData">
      <el-table-column type="selection" align="center" />
      <el-table-column label="#">
        <span slot-scope="scope" width="45">
          {{ scope.$index + 1 }}
        </span>
      </el-table-column>
      <el-table-column label="流程标题" prop="processName"></el-table-column>
      <el-table-column label="所属流程" prop="processDefName"></el-table-column>
      <el-table-column label="节点名称" prop="name"></el-table-column>
      <el-table-column label="申请人" prop="applyer"></el-table-column>
      <el-table-column label="状态" prop="suspended">
        <span slot-scope="scope">
          <el-tag effect="dark" size="small" :type="scope.row.suspended ?'danger':'success'"  >{{scope.row.suspended?'挂起':'激活'}}</el-tag>
        </span>
      </el-table-column>
      <el-table-column label="操作" >
        <span slot-scope="scope">
          <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-button type="text" size="mini" icon="el-icon-check" @click="handelAudit(scope.row)" :disabled="scope.row.suspended">处理</el-button>
          <el-button type="text" size="mini" icon="el-icon-more" @click="handFlow(scope.row.procInstId)"></el-button>
        </span>
      </el-table-column>
    </el-table>
    <leave-modal ref="leavemodal" @ok="handleOk"/>
    <purchase-modal ref="purchasemodal" @ok="handleOk"/>
    <proc-flow-modal ref="modal" @ok="handleOk"/>
    <pms-task-modal ref="tskmodal" @ok="handleQuery" />
  </div>
</template>

<script>
import { getTasking, auditTaskBatch, modalMap } from '@/api/activiti'
import LeaveModal from './modules/LeaveModal.vue'
import PurchaseModal from './modules/PurchaseModal.vue'
import ProcFlowModal from './modules/ProcFlowModal.vue'
import PmsTaskModal from './modules/PmsTaskModal.vue'
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
        processDefKey: undefined
      },
      // 加载数据方法 必须为 Promise 对象
      taskingData: [],
      previewVisible: false,
      previewImage: '',
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  computed: {
  },
  filters: {
  },
  created () {
    this.getList()
  },
  methods: {
    getList(){
      this.loading = true;
      getTasking(this.queryParams).then((response) => {
        this.taskingData = response.data.rows
        this.loading = false
      })
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.selectedRowKeys = selection.map(item => item.deploymentId)
    },
     /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
     /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.processDefKey = undefined
      this.handleQuery();
    },
    handleOk () {
      handleQuery()
    },
    handFlow (procInstId) {
      this.$refs.modal.show(procInstId)
    },
    handleEdit (record) {
      this.msgSuccess('想什么呢！');
    },
    handelAudit (record) {
      const modal = modalMap[record.processDefKey]
      this.$refs[modal].show(record)
    },
    handelAuditBatch (taskIds, result) {
      auditTaskBatch({ taskIds: taskIds, result: result }).then(res => {
        if (res.code === 0) {
          this.handleOk()
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
