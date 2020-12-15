# ruoyiact-vue
对若依框架工作流执行封装，通过 Mediator 分离工作流和业务
1. 业务 -> 工作流： 工作流候选人通过全局任务 listener 动态赋值（含会签等多实例任务）（activiti.context 包，activit.listener 包）
2. 业务 -> 工作流： 业务调用 Mediator 设置工作流流程变量 (activiti.engine 包)
3. 工作流 -> 业务： 工作流调用 Mediator 更新业务主表状态 (flow.engine 包)

## 总流程
1. 创建业务表对象  
2. 启动工作流  
3. 工作流推进遇到用户任务，创建任务  
	3.1. 监听创建任务  
		3.1.1. 监听器中调用业务，获得任务获选人  
		3.1.2. 设置工作流候选人  
			3.1.2.1. 通过节点ID 从提前缓存的 Map 中获得对应的处理类  
			3.1.2.2. 判断是否并行处理任务，如果是，不需要配置候选人，如果不是，调用处理 Bean 的 getAuditors 方法动态设置流程节点候选人  
		3.1.2. 任务创建成功  
4. 用户审批待办任务(audit)  
	4.1. 调用 activiti 包中封装好的 bizBusiness 执行审批  
		4.1.1. 调用 IFlowMediator 更新业务表  
			4.1.1.1. IFlowMediator 获得当前工作流任务节点ID  
			4.1.1.2. 通过节点ID 从提前缓存的 Map 中获得对应的处理类  
			4.1.1.3. 调用处理 Bean 中的 updateFlow 更新业务表  
			4.1.1.4. 调用处理 Bean 中的 updateVariable 更新工作流执行参数  
		4.1.2. 调用工作流推进流程  

## 说明
1. 基于 [ruoyi-cloud](https://gitee.com/zhangmrit/ruoyi-cloud) 改版而来，去掉 mybatis-plus 改为纯粹 mybatis, jpa 好像也顺手去掉了（需要手动添加 activiti 的表）
2. 原作者已经将工作流接口封装到了 bizBusiness 的中间结构（/👍）。已经封装好了一套工作流的对外接口，对于不是特别复杂的业务，直接使用原作者的封装就已经 ok 了
3. 如果流程业务场景比较复杂，可以考虑使用本项目中的中介者模式进一步封装（新增调用子任务支持，动态候选人，并行流程等），建议先看懂原作者的封装 activiti 包下的 bizBusiness 模块
4. 时间仓促，仅作参考，还有许多部分未上传，栗如工作流待办任务 Native 查询封装，项目的 BPMN 流程图等……
## 改动
1. 使用 IFlowMediator 中介解耦工作流与业务 (engine 包)
2. 使用全局监听器，设置工作流候选人 （listener 包）
3. 支持调用子任务，会签，或签等

## 详见 my blog [学习activiti 工作流](https://blog.csdn.net/u012296499/article/details/108602127)

## TODO
1. 前端：使用 状态机控制流转
2. 后端：在 IFlowMediator 中，创建全局状态机，执行状态流转控制和工作流相辅相成
3. 研究任务自由流转
