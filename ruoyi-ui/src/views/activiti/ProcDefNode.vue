<template>
  <div class="app-container" >
    <el-row :gutter="48">
      <el-col :span="5">
        <el-menu style="width: 200px" mode="vertical" @select="handleSelect">
          <el-menu-item v-for="node in nodes" :key="node.nodeId" :index="node.nodeId" :title="node.name" >
            <i :class="node.icon"></i>
            {{ node.name }}
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="12">
        <div class="table-page-search-wrapper">
          <el-form :model="form" ref="form" :rules="rules">
            <el-form-item >
              <el-alert description="温馨提示：若流程运行至未分配审批人员的审批节点时，流程将自动中断取消" type="warning" show-icon close-text="知道了" />
            </el-form-item>
            <el-form-item v-show="false">
              <el-input :v-model="form.nodeId" />
            </el-form-item>
            <el-form-item v-show="false">
              <el-input :v-model="form.deptHeader" />
            </el-form-item>
            <el-form-item
              label="节点名称">
              <span>{{ mdl.name }}</span>
            </el-form-item>
            <el-form-item
              label="审批人">
              <el-checkbox @change="onChangeRole" v-model="roleChecked" label="角色" />
              <el-checkbox @change="onChangeDept" v-model="deptChecked" label="部门" />
              <el-checkbox @change="onChangeUser" v-model="userChecked" label="用户" />
              <el-checkbox @change="onChangeDeptHeader" v-model="deptHeader" label="所属部门负责人" />
            </el-form-item>
            <el-form-item
              label="选择角色"
              v-show="roleChecked"
            >
              <el-select
                v-model="form.roleIds"
                style="width: 450px;"
                multiple
                clearable
                placeholder="角色">
                <el-option v-for="(item, index) in roles" :key="index" :label="item.roleName" :value="item.roleId">{{ item.roleName }}</el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item
              label="选择部门"
              v-show="deptChecked"
            >
                <treeselect 
                  v-model="form.deptIds"
                  style="width: 450px;"
                  :options="depts"
                  multiple
                  :disable-branch-nodes="true"
                  @select="handleChangeDept"
                  placeholder="部门" />
            </el-form-item>

            <el-form-item
              label="选择用户"
              v-show="userChecked"
            >
              <el-select
                style="width: 450px;"
                v-model="form.userIds"
                filterable
                multiple
                clearable
                placeholder="用户">
                <el-option v-for="(item, index) in users" :key="index" :label="item.userName" :value="item.userId">{{ item.nickName+' - '+item.userName }}</el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              style="text-align: center"
            >
              <el-button type="primary" :loading="confirmLoading" :disabled="mdl.type!==1" @click="handleSubmit">提交</el-button>
              <el-button style="margin-left: 8px" @click="toList">返回</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getNodeList, getNodeInfo, updateNodeInfo } from '@/api/activiti'
import { treeselect} from '@/api/system/dept'
import { listUser} from '@/api/system/user'
import { listRole } from '@/api/system/role'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: 'TableList',
  components: {
    Treeselect
  },
  data () {
    return {
      visible: false,
      mdl: {},
      confirmLoading: false,
      form: {},
      deptChecked: false,
      depts: [],
      userChecked: false,
      users: [],
      roleChecked: false,
      roles: [],
      deptHeader: false,
      nodes: [],
      rules: {
        roleIds: [{ required: false, message: '至少选择一个用户' }],
        deptIds: [{ required: false, message: '至少选择一个部门' }],
        userIds: [{ required: false, message: '至少选择一个用户' }]
      }
    }
  },
  created () {
    const procDefId = this.$route.query.procDefId
    getNodeList(procDefId).then(res => {
      this.nodes = res.data.rows.map(n => {
        let icon = ''
        switch (n.type) {
          case 0:
            icon = 'el-icon-video-play'
            break
          case 2:
            icon = 'el-icon-switch-button'
            break
          default:
            icon = 'el-icon-s-custom'
        }
        return { name: n.name, nodeId: n.nodeId, icon: icon, type: n.type }
      })
      this.nodes.sort(function(a, b) {
        return a.type - b.type
      })
    })
    listRole().then(res => {
      this.roles = res.rows
    })
    treeselect().then(response => {
      this.depts = response.data;
    });
    listUser().then(res => {
      this.users = res.rows
    })
  },
  methods: {
    handleSelect (nodeId) {
      const node = this.nodes.filter(item => nodeId === item.nodeId)[0]
      if (node.type === 1) {
        getNodeInfo(nodeId).then(res => {
          const data = res.data
          this.mdl = Object.assign(data, node)
          
          this.roleChecked = data.roleIds.length > 0
          this.deptChecked = data.deptIds.length > 0
          this.userChecked = data.userIds.length > 0
          this.deptHeader = data.deptHeader
          console.log(this.roleChecked);
          console.log('mdl:', this.mdl)
          this.$nextTick(() => {
            //this.form.setFieldsValue(pick(this.mdl, 'nodeId', 'userIds', 'deptIds', 'roleIds', 'deptHeader'))
            this.form = {
              nodeId: this.mdl.nodeId,
              userIds: this.mdl.userIds,
              deptIds: this.mdl.deptIds,
              roleIds: this.mdl.roleIds,
              deptHeader: this.mdl.deptHeader
            };
            console.log('form.deptHeader:', this.form.deptHeader)
            // this.form.setFieldsValue({ ...record })
          })
        })
      } else {
        this.$message.error('只能编辑任务节点')
        this.mdl = {}
        this.roleChecked = this.deptChecked = this.userChecked = this.deptHeader = false
      }
    },
    onChangeRole (checked) {
      //this.roleChecked = checked
      this.rules.roleIds.forEach(rule => {
        rule.required = checked
      })
      if (!checked) { this.form.roleIds = [] }
    },
    onChangeDept (checked) {
      //this.deptChecked = checked
      this.rules.deptIds.forEach(rule => {
        rule.required = checked
      })
      if (!checked) { this.form.deptIds = []}
    },
    
    onChangeUser (checked) {
      //this.userChecked = checked
      this.rules.userIds.forEach(rule => {
        rule.required = checked
      })
      if (!checked) { this.form.userIds = [] }
    },
    onChangeDeptHeader (checked) {
      this.deptHeader = checked
      this.form.deptHeader = this.deptHeader
    },
    handleChangeDept (extra, instance) {
    },
    toList () {
      this.$router.push({ path: '/system/activiti/procdef/list' })
    },
    handleSubmit (e) {
      e.preventDefault()

      this.$refs["form"].validate((valid) => {
        if (valid) {
          console.log('Received values of form: ', this.form)
          this.confirmLoading = true
          updateNodeInfo(this.form).then(res => {
            if (res.code === 200) {
              this.msgSuccess('保存成功')
              this.toList()
            } else {
              this.$message.error(res.msg)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      });
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
.ant-menu-vertical .ant-menu-item{
    display: flex;
}
.ant-menu-vertical .ant-menu-item .anticon{
  font-size: 24px;
  display: flex;
  align-items: center;
}
</style>
