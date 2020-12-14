# ruoyiact-vue
对若依框架工作流执行封装


## 改动
1. 基于 [ruoyi-cloud](https://gitee.com/zhangmrit/ruoyi-cloud) 改版而来，去掉 mybatis-plus 改为纯粹 mybatis, jpa 好像也顺手去掉了（需要手动添加 activiti 的表）
2. 开发使用 IFlowMediator 中介解耦工作流与业务 (engine 包)
3. 使用全局监听器，设置工作流候选人 （listener 包）
4. 支持调用子任务，会签，或签等

## 详见 my blog [学习activiti 工作流](https://blog.csdn.net/u012296499/article/details/108602127)

## TODO
1. 前端：使用 状态机控制流转
2. 后端：在 IFlowMediator 中，创建全局状态机，执行状态流转控制和工作流相辅相成
3. 研究任务自由流转
