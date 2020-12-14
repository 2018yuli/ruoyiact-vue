<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="流程定义key">
        <el-input 
          placeholder="请输入" 
          v-model="queryParams.key" 
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
      <el-col :span="1.5">
        <el-upload
          v-hasPermi="['activiti:prof:add']"
          name="file"
          :action="uploadUrl"
          :headers="headers"
          :show-file-list="false"
          @change="uploadChange"
        >
          <el-button type="warning" size="mini" icon="el-icon-upload">部署流程文件</el-button>
        </el-upload>
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

    <el-table ref="table" v-loading="loading" rowKey="deploymentId" :data="procDefData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" />
      <el-table-column label="#" width="45">
        <template slot-scope="scope">
            {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="流程定义key" prop="key"></el-table-column>
      <el-table-column label="版本" prop="version"></el-table-column>
      <el-table-column label="部署ID" prop="deploymentId"></el-table-column>
      <el-table-column label="资源名称" prop="resourceName">
        <template slot-scope="scope">
            <el-button type="text" @click="handleModelXml(scope.row)">{{ scope.row.resourceName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程图名称" prop="dgrmResourceName">
        <template slot-scope="scope">
            <el-button type="text" @click="handleShowImage(scope.row.deploymentId)">{{ scope.row.dgrmResourceName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-popover
            placement="top"
            :ref="`popover-${scope.$index}`"
            title="警告"
            v-hasPermi="['activiti:prof:edit']"
          >
            <p>
              <i class="el-icon-warning-outline" style="color: red" />
              该操作会一并 激活/挂起 所有流程实例！
            </p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="scope._self.$refs[`popover-${scope.$index}`].doClose()">取消</el-button>
              <el-button type="primary" size="mini" @click="handleState(scope.row)">确定</el-button>
            </div>
            <el-button slot="reference" type="text">{{ scope.row.suspensionState===1?'挂起':'激活' }}</el-button>
          </el-popover>
          &nbsp;
          <el-button type="text" v-hasPermi="['activiti:prof:edit']" @click="handleNode(scope.row.id)">节点配置</el-button>
          <el-button type="text" v-hasPermi="['activiti:prof:remove']" @click="delByIds([scope.row.deploymentId])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="previewVisible" width="900" @close="previewCancel">
      <img style="width: 100%" :src="previewImage" />
    </el-dialog>
  </div>
</template>

<script>
// import storage from 'store'
// TODO
//import { ACCESS_TOKEN } from '@/store/mutation-types'
// eslint-disable-next-line no-unused-vars
import { getProfList, delProf, diagram, modelXml, processState, deployByFileURL } from '@/api/activiti'

export default {
  name: 'ProfList',
  components: {
  },
  data () {
    return {
      loading: true,
      showSearch: true,
      visible: false,
      form: {},
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        key: undefined
      },
      procDefData: [],
      previewVisible: false,
      previewImage: '',
      uploadUrl: process.env.VUE_APP_BASE_URL + deployByFileURL,
      headers: {
        //token: storage.get(ACCESS_TOKEN)
      },
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
      getProfList(this.queryParams).then((response) => {
        this.procDefData = response.data.rows
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.key = '';
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectedRowKeys = selection.map(item => item.deploymentId)
    },
    handleNode (procDefId) {
      this.$router.push({ path: '/system/activiti/procdef/node', query: { procDefId: procDefId } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    dataModal (dictType) {
      this.$refs.datamodal.show(dictType)
    },
    previewCancel () {
      this.previewVisible = false
    },
    handleShowImage (did) {
      diagram(did).then(res => {
        const blob = new Blob([res], { type: 'image/png' })
        this.previewImage = URL.createObjectURL(blob)
      })
      this.previewVisible = true
    },
    uploadChange (info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList)
      }
      if (info.file.status === 'done') {
        this.$refs.table.refresh(true)
      }
    },
    handleModelXml (record) {
      modelXml(record.deploymentId).then(res => {
        const aLink = document.createElement('a')
        var blob = new Blob([res], { type: 'application/xml' })
        var fileName = record.resourceName
        aLink.href = URL.createObjectURL(blob)
        aLink.setAttribute('download', fileName) // 设置下载文件名称
        document.body.appendChild(aLink)
        aLink.click()
        document.body.removeChild(aLink)
        window.URL.revokeObjectURL(aLink.href)
      })
    },
    /** 流程挂起，激活 */
    handleState (record) {
      const state = record.suspensionState === 1 ? 'suspend' : 'active'
      processState(record.id, state).then(res => {
        if (res.code === 200) {
          record.suspensionState = record.suspensionState === 1 ? 2 : 1
        }
      })
    },
    delByIds (ids) {
        this.$confirm('是否确认删除编号为"' + ids.join(',') + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProf({ ids: ids.join(',') });
        }).then(() => {
          this.getList();
          this.selectedRowKeys = []
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
