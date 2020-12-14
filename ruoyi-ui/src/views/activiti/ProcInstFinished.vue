<template>
  <div class="app-container" >
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true" >
      <el-form-item label="流程定义key">
        <el-input 
          placeholder="请输入" 
          v-model="queryParams.procDefKey" 
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"/>
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
      <el-table-column label="流程实例ID" prop="id"></el-table-column>
      <el-table-column label="流程名称" prop="procName"></el-table-column>
      <el-table-column label="申请人" prop="applyer"></el-table-column>
      <el-table-column label="标识key" prop="procDefKey"></el-table-column>
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
      <el-table-column label="结束原因" prop="deleteReason"></el-table-column>
      <el-table-column label="开始时间" prop="startTime"></el-table-column>
      <el-table-column label="结束时间" prop="endTime"></el-table-column>
      <el-table-column label="操作">
        <span slot-scope="text, record">
          <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
        </span>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { millsToTime } from '@/utils/util'
import { getProfInfFinished, delHiProcessIns, resultMap } from '@/api/activiti'
export default {
  name: 'ProfList',
  components: {
  },
  data () {
    return {
      visible: false,
      loading: true,
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        procDefKey: undefined
      },
      // 加载数据方法
      procInstData: [],
      selectedRowKeys: [],
      selectedRows: [],
    }
  },
  computed: {
  },
  filters: {
    resultFilter (result) {
      return resultMap[result]
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
      getProfInfFinished(this.queryParams).then((response) => {
        this.procDefData = response.data.rows
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
      this.queryParams.procDefKey = '';
      this.handleQuery();
    },
    
    delByIds (ids) {
      delHiProcessIns({ ids: ids.join(',') }).then(res => {
        if (res.code === 200) {
          this.msgSuccess(`删除成功`)
          this.handleQuery()
        } else {
          this.$message.error(res.msg)
        }
      }).finally(
        this.selectedRowKeys = []
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
