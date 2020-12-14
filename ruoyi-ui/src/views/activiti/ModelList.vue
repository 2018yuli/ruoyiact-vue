<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="名称">
        <el-input 
          placeholder="请输入" 
          size="small" 
          v-model="queryParams.name"
          style="width: 240px" 
          clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleOk()">搜索</el-button>
        <el-button  icon="el-icon-refresh" size="mini" @click="() => {queryParams = {};handleOk()}">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['activiti:models:add']" type="primary" size="mini" icon="el-icon-plus" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-dropdown v-hasPermi="['activiti:models:remove']" size="mini" v-if="selectedRowKeys.length > 0">
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="delByIds(selectedRowKeys)">删除</el-button>
        </el-dropdown>
      </el-col>
      <div class="top-right-btn">
        <el-tooltip class="item" effect="dark" content="刷新" placement="top">
          <el-button size="mini" circle icon="el-icon-refresh" @click="handleOk" />
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
          <el-button size="mini" circle icon="el-icon-search" @click="showSearch=!showSearch" />
        </el-tooltip>
      </div>
    </el-row>
    
    <el-table ref="table" v-loading="loading" rowKey="id" :data="modelData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="编号" prop="id"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="版本" prop="version"></el-table-column>
      <el-table-column label="发布编号" prop="deploymentId"></el-table-column>
      <el-table-column label="创建时间" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后时间" prop="lastUpdateTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row.id)"
            v-hasPermi="['activiti:models:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="delByIds([scope.row.id])"
            v-if="scope.row.deploymentId==null"
            v-hasPermi="['activiti:models:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-release"
            @click="deploy(scope.row.id)"
            v-if="scope.row.deploymentId==null"
            v-hasPermi="['activiti:models:remove']"
          >发布</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="modelVisible"
      title="Activiti工作流在线流程设计编辑"
      width="100%"
      center
      @cancel="modelCancel">
      <div style="position:relative;height: 100%;">
        <iframe
          id="iframe"
          :src="modelerUrl"
          frameborder="0"
          width="100%"
          height="720px"
          scrolling="auto"
        ></iframe>
        <Spin fix size="large" v-if="modelerLoading"></Spin>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import { getModelList, delModel, deploy } from '@/api/activiti'
const modelerBaseUrl = process.env.VUE_APP_BASE_URL
export default {
  name: 'ModelList',
  components: {
  },
  data () {
    return {
      // 遮罩层
      loading: true,
      visible: false,
      showSearch: true,
      modelerUrl: '',
      modelerLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined
      },
      form: {},
      mdl: {},
      permissions: [],
      // 高级搜索 展开/关闭
      advanced: false,
      // 加载数据方法 必须为 Promise 对象
      modelData: [],
      modelVisible: false,
      selectedRowKeys: [],
      selectedRows: [],
    }
  },
  computed: {
  },
  filters: {
  },
  created () {
    this.getList();
  },
  methods: {
    getList(){
      this.loading = true;
      getModelList(this.queryParams).then((response) => {
        this.modelData = response.data.rows
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectedRowKeys = selection.map(item => item.id);
    },
    handleAdd () {
      this.modelVisible = true
      this.modelerUrl = modelerBaseUrl + '/act/models/newModel?time=' + new Date().getTime()
    },
    handleEdit (id) {
      this.modelVisible = true
      this.modelerUrl = modelerBaseUrl + '/act/modeler.html?modelId=' + id + '&time=' + new Date().getTime()
    },
    handleOk () {
      this.getList();
    },
    dataModal (dictType) {
      this.$refs.datamodal.show(dictType)
    },
    modelCancel () {
      this.modelVisible = false
      this.handleOk()
    },
    deploy (id) {
      deploy(id).then(res => {
          this.msgSuccess(`发布成功`)
          this.handleOk()
      })
    },
    delByIds (ids) {
      this.$confirm('是否确认删除这"' + ids.length + '"条数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delModel({ ids: ids.join(',') });
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
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
