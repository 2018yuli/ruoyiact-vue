<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="申请标题">
        <el-input 
          size="small"
          style="width: 240px" 
          placeholder="请输入申请标题" 
          v-model="queryParams.title" 
          clearable/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select size="small" style="width: 240px" placeholder="请选择" v-model="queryParams.status" >
          <el-option :value="''">全部</el-option>
          <el-option v-for="(val, key) in statusMap" :key="key" :label="val" :value="key">{{ val }}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审批结果">
        <el-select size="small" style="width: 240px" placeholder="请选择" v-model="queryParams.result">
          <el-option :value="''">全部</el-option>
          <el-option v-for="(val, key) in resultMap" :key="key" :label="val" :value="key">{{ val }}</el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
       <el-col :span="1.5">
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="$refs.modal.add()">新增</el-button>
      </el-col>
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

    <el-table size="medium" ref="table" rowKey="id" v-loading="loading" :data="businessData" @selection-change="handleSelectionChange"
      :default-sort="{prop: 'applyTime', order: 'descending'}" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请标题" align="center" prop="title" :show-overflow-tooltip="true" />
      <el-table-column label="所属流程" align="center" prop="procName"  />
      <el-table-column label="当前审批环节" align="center" prop="currentTask"  />
      <el-table-column label="审批结果" align="center" prop="result" :formatter="resultFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="申请时间" align="center" prop="applyTime" :sortable="true" width="180" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view"  @click="handelAudit(scope.row)">详情</el-button>
          
          <el-button size="mini" type="text" icon="el-icon-refresh-left" :disabled="scope.row.status!==1" @click="cancel([scope.row.procInstId])">撤销</el-button>
          
          <el-button size="mini" type="text" icon="el-icon-delete" :disabled="scope.row.status===1" @click="delByIds([scope.row.id])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <business-modal ref="modal" @ok="handleQuery"/>
    <leave-modal ref="leavemodal" @ok="handleQuery" :audit="false"/>
    <purchase-modal ref="purchasemodal" @ok="handleQuery" :audit="false"/>
  </div>
</template>

<script>
import { getBusinessListMy, delProcessIns, delBusiness, modalMap, resultMap, statusMap } from '@/api/activiti'
import BusinessModal from './modules/BusinessModal.vue'
import LeaveModal from './modules/LeaveModal.vue'
import PurchaseModal from './modules/PurchaseModal.vue'

export default {
  name: 'TableList',
  components: {
    BusinessModal,
    LeaveModal,
    PurchaseModal
  },
  data () {
    return {
      description: '处理中的业务无法被删除',
      showSearch: true,
      loading: true,
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        status: undefined,
        result: undefined
      },
      // 加载数据方法
      businessData: [],
      selectedRowKeys: [],
      selectedRows: [],
      resultMap,
      statusMap,
      firstActed: true
    }
  },
  computed: {
  },
  created () {
    this.getList()
  },
  activated () {
    if (this.firstActed) {
      this.firstActed = false
    } else {
      this.handleQuery()
    }
  },
  methods: {
    getList(){
      this.loading = true;
      getBusinessListMy(this.queryParams).then((response) => {
        this.businessData = response.data.rows
        this.loading = false
      })
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.selectedRowKeys = selection.map(item => item.id)
    },
    // 字典状态字典翻译
    statusFormat(row, column) {
      return this.statusMap[row.status];
    },
     // 字典状态字典翻译
    resultFormat(row, column) {
      return this.resultMap[row.result];
    },
    handleAdd () {
      this.$refs.modal.add()
    },
    handelAudit (record) {
      const modal = modalMap[record.procDefKey]
      this.$refs[modal].show({ businessKey: record.id })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
     /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.title = undefined;
      this.queryParams.status = undefined;
      this.queryParams.result = undefined;
      this.handleQuery();
    },
    // 撤销
    cancel (ids) {
      // const idstr = ids.map(r => { return '\'' + r + '\'' }).join('')
      this.$confirm('是否确认撤销编号为"' + ids.join(',') + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
         delProcessIns({ ids: ids.join(','), reason: '主动撤销' })
      }).then(() => {
        this.msgSuccess('撤销成功')
        this.handleQuery()
        this.selectedRowKeys = []
      })
    },
    delByIds (ids) {
      // const idstr = ids.map(r => { return '\'' + r + '\'' }).join('')
      this.$confirm('是否确认删除编号为"' + ids.join(',') + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        delBusiness({ ids: ids.join(',') })
      }).then(() => {
        this.msgSuccess('删除成功')
        this.handleQuery()
        this.selectedRowKeys = []
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
