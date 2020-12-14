package com.ruoyi.activiti.listener;

import com.ruoyi.activiti.consts.ActivitiConstant;
import com.ruoyi.activiti.context.FlowContext;
import com.ruoyi.activiti.domain.BizBusiness;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.activiti.service.IBizNodeService;
import com.ruoyi.activiti.engine.IBaseFlowService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Workflow listeners each node
 * 
 * @author Lenovo
 * @Description:
 * @Date: 2020年9月15日上午11:21:50
 * @Version: 1.0.0
 */
@Component("globalTaskListener")
public class MyExecutionListener implements ExecutionListener,TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8016558621174601799L;
	
//	public static ThreadLocal<String> bussinessKeyLocal = new ThreadLocal<String>();
	
	@Autowired
	private FlowContext flowContext;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private IBizNodeService bizNodeService;
	
	@Autowired
	private IBizBusinessService businessService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		String procInstId = delegateTask.getProcessInstanceId();
		if (delegateTask.getCandidates().size() > 0)
			return;
		
		// 在任务开始前，设置任务候选人
		if (ActivitiConstant.TASK_EVENT_CREATE.endsWith(eventName)) {
			String tskDefKey = delegateTask.getTaskDefinitionKey();
			String businessKey = delegateTask.getExecution().getProcessBusinessKey();
			if (StringUtils.isEmpty(businessKey)) {
//				businessKey = bussinessKeyLocal.get();
			}
			
			// 更新当前业务
			BizBusiness business = businessService.selectBizBusinessById(businessKey);
			BizBusiness saveModel = new BizBusiness().setId(business.getId());
			// 配置的审核候选人
			List<String> auditors = new ArrayList<String>(0);
			IBaseFlowService tskService = flowContext.get(tskDefKey);
            if (tskService == null)
            	return;
            // 如果是多实例任务
            if (tskService.isMultiInst()) {
            	Object subUser = delegateTask.getVariable(ActivitiConstant.CONFIRM_MULTIINST_USER);
            	if (subUser != null)
            		taskService.addCandidateUser(delegateTask.getId(), subUser.toString());
            }
            // 任务为多实例任务不需要配置候选人
            if (!tskService.isMultiInst()) {
            	auditors = tskService.getAuditors(businessKey);
            	// 从全局配置中获取审核候选人
                if (auditors.isEmpty()) {
    				Set<String> theauditors = bizNodeService.getAuditors(tskDefKey, business.getUserId());
                	auditors = theauditors.stream().collect(Collectors.toList());
                }
                if (null != auditors && auditors.size() > 0)
                {
                    // 添加审核候选人
                    for (String auditor : auditors)
                    {
                        taskService.addCandidateUser(delegateTask.getId(), auditor);
                    }
                }
                else
                {
                    runtimeService.deleteProcessInstance(procInstId,
                            ActivitiConstant.SUSPEND_PRE + "no auditor");
					saveModel.setCurrentTask(ActivitiConstant.END_TASK_NAME)
                    	.setStatus(ActivitiConstant.STATUS_SUSPEND)
                            .setResult(ActivitiConstant.RESULT_SUSPEND);
					businessService.updateBizBusiness(saveModel);
                }
            }
			// 更新业务对象
			saveModel.setProcInstId(procInstId)
					.setProcDefKey(tskDefKey)
					.setCurrentTask(delegateTask.getName());
			businessService.updateBizBusiness(saveModel);
		}else if (ActivitiConstant.TASK_EVENT_ASSIGNMENT.endsWith(eventName)) {
//			System.out.println("assignment========++++++++++++++");
		}else if (ActivitiConstant.TASK_EVENT_COMPLETE.endsWith(eventName)) {
			System.out.println("complete===========++++++++++++++");
		}else if (ActivitiConstant.TASK_EVENT_DELETE.endsWith(eventName)) {
			System.out.println("delete=============++++++++++++++");
		}
	}
	@Override
	public void notify(DelegateExecution exec) throws Exception {
		 // Get the information start approver
//		String activityId = execution.getCurrentActivityId();
//		 String processDefinitionId = execution.getProcessDefinitionId (); // Get the process definition id 
// 
//		ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) execution.getEngineServices().getRepositoryService()
//				.getProcessDefinition(processDefinitionId); 
 
//		 ActivityImpl activityImpl = processDefinitionEntity.findActivity (activityId); // Get Events The event instance id 
//		TaskDefinition taskDef = (TaskDefinition)activityImpl.getProperties().get("taskDefinition");
//		 String zpr = taskDef.getAssigneeExpression () == null ? "": taskDef.getAssigneeExpression().getExpressionText (); // agents.
//		 Set <Expression> userCodes = taskDef.getCandidateUserIdExpressions (); // candidates
//		 Set <Expression> roleCodes = taskDef.getCandidateGroupIdExpressions (); // candidate set
		 // Get the information end approver
 
		String eventName = exec.getEventName();
		//start
		if (ActivitiConstant.EXECUTION_EVENT_START.equals(eventName)) {
			ExecutionEntity thisEntity = (ExecutionEntity)exec;
		    if (StringUtils.isEmpty(thisEntity.getBusinessKey())) {
		    	ExecutionEntity superExecEntity = thisEntity.getSuperExecution();
		    	String key = superExecEntity.getBusinessKey();
		    	
		    	if (StringUtils.isNotEmpty(key)) {
		    		thisEntity.setBusinessKey(key);
		    		thisEntity.updateProcessBusinessKey(key);
		    	}
		    }
			//System.out.println("start=========++++++++++++++");
		}else if (ActivitiConstant.EXECUTION_EVENT_END.equals(eventName)) {
			//System.out.println("end=========++++++++++++++");
		}
		else if (ActivitiConstant.EXECUTION_EVENT_TAKE.equals(eventName)) {
			//System.out.println("take=========++++++++++++++");
		}
	}
	
}