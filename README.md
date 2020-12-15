# ruoyiact-vue
对若依框架工作流执行封装，通过 Mediator 分离工作流于业务
1. 业务 -> 工作流： 工作流候选人通过全局任务 listener 动态赋值，在 listener 中通过 Mediator 从业务中查询任务执行候选人（含会签等多实例任务）（activiti.context 包，activit.listener 包）
2. 业务 -> 工作流： 业务调用 Mediator 设置工作流流程变量 (activiti.engine 包)
3. 工作流 -> 业务： 工作流调用 Mediator 更新业务主表状态 (flow.engine 包)



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
