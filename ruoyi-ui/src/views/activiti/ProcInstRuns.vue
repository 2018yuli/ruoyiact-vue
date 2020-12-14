<template>
  <div class="app-container">
    <el-form :inline="true" ref="queryForm" v-show="showSearch" :model="queryParams">
    
      <el-form-item label="流程定义key">
        <el-input 
          placeholder="请输入" 
          v-model="queryParams.key"
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          clearable />
      </el-form-item>
  
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['activiti:prof:remove']" 
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

    <el-table ref="table" rowKey="id" v-loading="loading" @selection-change="handleSelectionChange" :data="procInstData">
      <el-table-column label="流程实例ID" prop="procInstId"></el-table-column>
      <el-table-column label="流程名称" prop="name"></el-table-column>
      <el-table-column label="版本" prop="version"></el-table-column>
      <el-table-column label="申请人" prop="applyer"></el-table-column>
      <el-table-column label="标识key" prop="key"></el-table-column>
      <el-table-column label="当前环节" prop="currTaskName"></el-table-column>
      <el-table-column label="部署ID" prop="deployId"></el-table-column>
      <el-table-column label="状态" prop="isSuspended">
        <span slot-scope="text">
          {{ text?'挂起':'激活' }}
        </span>
      </el-table-column>
      <el-table-column label="操作" prop="action" width="150px">
        <span slot="action" slot-scope="text, record">
        <el-popover
          placement="topRight"
          trigger="click"
          content="该操作会一并 激活/挂起 所有当前实例?"
          okText="是"
          cancelText="否"
        >
          <a @click="handleState(record)">{{ record.isSuspended?'激活':'挂起' }}</a>
        </el-popover>

        <a @click="delByIds([record.id])">删除</a>
      </span>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getProfInfRuns, delProcessIns, processInsState } from '@/api/activiti'

export default {
  name: 'RunsProcess',
  components: {
  },
  data () {
    return {
      visible: false,
      loading: true,
      form: {},
      mdl: {},
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        key: undefined
      },
      // 加载数据方法
      procInstData: [],
      selectedRowKeys: [],
      selectedRows: [],
    }
  },
  filters: {
  },
  created () {
    this.getList();
  },
  methods: {
    getList(){
      this.loading = true;
      getProfInfRuns(this.queryParams).then((response) => {
        this.procDefData = response.data.rows
        this.loading = false
      })
    },
    // 多选框选中数据
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
      this.queryParams.key = '';
      this.handleQuery();
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
      delProcessIns({ ids: ids.join(','), reason: '流程中止' }).then(res => {
        if (res.code === 200) {
          this.msgSuccess(`删除成功`)
          this.handleQuery()
        } else {
          this.$message.error(res.msg)
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
