package com.ruoyi.activiti.engine.impl;

import com.ruoyi.activiti.domain.BizAudit;
import com.ruoyi.activiti.engine.IBaseFlowService;
import com.ruoyi.activiti.engine.ITskMediator;
import com.ruoyi.activiti.service.IBizAuditService;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.concurrent.LockUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 做为工作流程手工任务与业务的中介者 
 * 
 * 做为工作流的补充实现，
 * 栗如并发控制，
 * 栗如异常事件的异常抛出，
 * 在此中介者中统一处理
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年10月13日下午5:25:10
 * @Version: 1.0.0
 */
@Service
public class TskMediatorImple implements ITskMediator {

	@Autowired
	private TaskService tskService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IBizBusinessService businessService;

	@Autowired
	private IBizAuditService bizAuditService;

	
	public static final String LOCK_PREFX = "TskMediator";
	public static final String LOCKBYJOB_MSG = "自动流程执行中，请重试！";
	public static final String NOTEXIST_MSG = "该任务已自动开始或他人处理，请刷新任务列表！";
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int process(IBaseFlowService service, BizAudit audit) {
		/***
		 * 1. 工作流，异步任务并发处理，每个人的 flowTask 不同，无需控制并发
		 * 2. 处理全局暂停，恢复，终止，重启，删除流程请求
		 * 3. 调用 service 的  updateTsk 方法获得 工作流业务 result, comment, assignee
		 * 4. 调用 service 的 updateVariables 方法更新工作流
		 * TODO 业务表中使用状态模式规范状态流转
		 */
		String tskType = service.getFlowType();
		String procInstId = audit.getProcInstId();
		String businessKey = audit.getBusinessKey() + "";
		int result = audit.getResult();
		String comment = audit.getComment();
		String assignee = audit.getAssignee();
		int rate = audit.getRate();
		String taskId = audit.getTaskId();
		
		// 验证任务是否已经被其他人完成
		long count = tskService.createTaskQuery().active().taskId(taskId).count();
		if (count == 0)
			throw new CustomException(NOTEXIST_MSG);
		// 任务已经由工作流定时任务自动推进
		if (LockUtils.isLockedByWorkflowJob(LOCK_PREFX + businessKey + tskType)) {
			throw new CustomException(LOCKBYJOB_MSG);
		}
		int resultAfterTsk = service.updateFlow(procInstId, businessKey, result, comment, assignee);
		service.updateVariables(procInstId, comment, rate, resultAfterTsk);
		return 1;
	}

	/**
     * 向工作流执行引擎添加参数
     * @param procInstId
     * @param variableName
     * @param value
     */
	public void setVariables(String procInstId, String variableName, Object value) {
    	runtimeService.setVariable(procInstId, variableName, value);
    }

    /**
     * 从工作流执行引擎中获得参数
     * @param procInstId
     * @param variableName
     */
    public Object getVariables(String procInstId, String variableName) {
    	return runtimeService.getVariable(procInstId, variableName);
    }

//    /**
//     * TODO 任务并发执行锁
//     *
//     * @author yu.li l898974766@outlook.com
//     * @Description:
//     * @Date: 2020年10月21日上午11:07:09
//     * @Version: 1.0.0
//     */
//    private static class ILockMementoImpl implements ILockMemento {
//    	public static void setLocked(String businessKey, String tskType) {
//    		LockUtils.getLock(LOCK_PREFX + businessKey + tskType);
//    	}
//    	public static boolean getLocked(String businessKey, String tskType) {
//    		return LockUtils.isLockedByWorkflowJob(LOCK_PREFX + businessKey + tskType);
//    	}
//    	public static void releaseLock(String businessKey, String tskType) {
//    		LockUtils.releaseWorkflowJobLock(LOCK_PREFX + businessKey + tskType);
//    	}
//    }
}
