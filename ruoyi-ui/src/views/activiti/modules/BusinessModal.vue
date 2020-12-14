<template>
  <el-dialog title="操作" style="top: 20px;" width="800" :visible.sync="visible" >
    <!-- :grid="{gutter: 24, lg: 3, md: 2, sm: 1, xs: 1}" -->
    <el-row :gutter="24">
      <el-col :lg="8" :md="6" :sm="1" :xs="1" v-for="(item,index) in profList" :key="index" >
        <el-card shadow="hover" style="margin-top: 5px;" class="box-card">
            <div slot="header" class="clearfix">
              <el-link @click="handelClick(item)"  type="primary" ><b>{{ (index+1)+'.'+item.name }} </b></el-link>
            </div>
            <div class="meta-content" >
              <p>{{ item.description }}</p>
              <p>版本:v{{ item.version }}</p>
            </div>
        </el-card>
      </el-col>
    </el-row>
  </el-dialog>
</template>
<script>
import { getProfAllLatest, routerMap } from '@/api/activiti'
export default {
  name: 'ActBusinessModal',
  props: {},
  components: {},
  data () {
    return {
      visible: false,
      profList: []
    }
  },
  beforeCreate () {},
  created () {
    getProfAllLatest().then(res => {
      this.profList = res.rows.map(r => {
          return r
      })
    })
  },
  methods: {
    add () {
      this.visible = true
    },
    handelClick (prof) {
      const router = routerMap[prof.key]
      if (router) {
        this.visible = false
        this.$router.push({ path: "/system/activiti/task", query: { key: prof.key, procDefId: prof.id, procName: prof.name }});
        //this.$router.push({ name: router, query: { key: prof.key, procDefId: prof.id, procName: prof.name } })
      } else {
        this.$message.error('路由配置错误')
      }
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
