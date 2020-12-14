<template>
  <div :body-style="{padding: '24px 32px'}">
    <el-form @submit="handleSubmit" :model="form" :rules="rules">
      <el-form-item label="标题">
        <el-input v-model="title" placeholder="请假标题" />
      </el-form-item>
      <el-form-item label="金额">
        <el-input style="width: 150px;" v-model="money" placeholder="金额">
          <template slot="append">元</template>
        </el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" rows="4" v-model="description" placeholder="请输入描述"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" :loading="confirmLoading">提交</el-button>
        <router-link :to="{name:'businessList'}">
          <a-button style="margin-left: 8px">返回</a-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { savePurchase } from "@/api/activiti";
//import pick from 'lodash.pick'
export default {
  name: "LeaveForm",
  data() {
    return {
      confirmLoading: false,
      leaveTypes: [],
      formData: {},
      // form
      form: {},
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        money: [
          { required: true, message: "请输入金额" },
          { validator: validateMoney },
        ],
        description: [{ required: true, message: "请输入描述" }],
      },
    };
  },
  async created() {
    const query = this.$route.query;
    //this.formData = Object.assign(pick(query, 'key', 'procDefId', 'procName'))
    this.formData = {
      key: query.key,
      procDefId: query.procDefId,
      procName: query.procName
    }
  },
  methods: {
    validateMoney(rule, value, callback) {
      if (value > 0) {
        callback();
      } else {
        callback(new Error("金额必须大于0"));
      }
    },
    // handler
    handleSubmit(e) {
      e.preventDefault();
      this.$refs["form"].validate((valid) => {
        if (valid) {
          values = Object.assign(values, this.formData);
          // eslint-disable-next-line no-console
          console.log("Received values of form: ", values);
          this.confirmLoading = true;
          savePurchase(values).then((res) => {
            if (res.code === 0) {
              this.$message.success("保存成功");
              this.$router.push({ name: "businessList" });
            } else {
              this.$message.error(res.msg);
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      });
    },
  },
};
</script>
