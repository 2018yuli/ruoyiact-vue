# ruoyiact-vue
对若依框架工作流执行封装，通过 Mediator 分离工作流和业务
1. 业务 -> 工作流： 工作流候选人通过全局任务 listener 动态赋值（含会签等多实例任务）（activiti.context 包，activit.listener 包）
2. 业务 -> 工作流： 业务调用 Mediator 设置工作流流程变量 (activiti.engine 包)
3. 工作流 -> 业务： 工作流调用 Mediator 更新业务主表状态 (flow.engine 包)


## 说明
1. 基于 [ruoyi-cloud](https://gitee.com/zhangmrit/ruoyi-cloud) 改版而来，去掉 mybatis-plus 改为纯粹 mybatis, jpa 好像也顺手去掉了（需要手动添加 activiti 的表）
2. 原作者已经将工作流接口封装到了 bizBusiness 的中间结构（/👍）。已经封装好了一套工作流的对外接口，对于不是特别复杂的业务，直接使用原作者的封装就已经 ok 了
3. 如果流程业务场景比较复杂，可以考虑使用本项目中的中介者模式进一步解耦（模块分离）
4. 时间仓促，仅作参考，还有许多部分未上传，栗如工作流待办任务 Native 查询封装，BPMN 流程图等……
## 改动
1. 使用 IFlowMediator 中介解耦工作流与业务 (engine 包)
2. 使用全局监听器，设置工作流候选人 （listener 包）
3. 支持调用子任务，会签，或签等

## 详见 my blog [学习activiti 工作流](https://blog.csdn.net/u012296499/article/details/108602127)

## TODO
1. 前端：使用 状态机控制流转
2. 后端：在 IFlowMediator 中，创建全局状态机，执行状态流转控制和工作流相辅相成
3. 研究任务自由流转
