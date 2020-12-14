package com.ruoyi.leave.engine;

import com.ruoyi.activiti.domain.BizAudit;
import com.ruoyi.activiti.domain.BizBusiness;
import com.ruoyi.activiti.engine.IBaseFlowService;
import com.ruoyi.activiti.engine.ITskMediator;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.service.IWorkflowLeaveService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月10日下午3:55:33
 * @Version: 1.0.0
 */
public abstract class AbstractBaseFlowService implements IBaseFlowService {

	@Autowired
	private IBizBusinessService bizBusinessService;
	
	@Autowired
	private IWorkflowLeaveService leaveService;
	
	@Autowired
	private ITskMediator tskMediator;

	public int execTsk(BizAudit audit) {
		return tskMediator.process(this, audit);
	}
	
    @Override
	public boolean isMultiInst() {
		return false;
	}

	/**
     * @param businessKey
     * @return
     */
    protected WorkflowLeave getFlow(String businessKey) {
    	BizBusiness bizBusiness = bizBusinessService.selectBizBusinessById(businessKey);
    	return leaveService.selectWorkflowLeaveById(bizBusiness.getTableId());
	}
    
    /**
     * @param workflowLeave
     * @return
     */
    protected int updateFlow(WorkflowLeave workflowLeave) {
    	return leaveService.updateWorkflowLeave(workflowLeave);
    }
    
    /**
     * 向工作流执行引擎添加参数
     * @param procInstId
     * @param variableName
     * @param value
     */
    protected void setVariables(String procInstId, String variableName, Object value) {
    	tskMediator.setVariables(procInstId, variableName, value);
    }
    
    /**
     * 从工作流执行引擎中获得参数
     * @param procInstId
     * @param variableName
     */
    protected Object getVariables(String procInstId, String variableName) {
    	return tskMediator.getVariables(procInstId, variableName);
    }
    
    /**
     * 从工作流执行引擎中获得参数
     * @param procInstId
     * @param variableName
     */
    protected Integer getIntVariables(String procInstId, String variableName) {
    	Object variables = getVariables(procInstId, variableName);
    	if (variables != null)
    		return Integer.parseInt(variables + "");
    	return -1;
    }
    
    /**
     * 从父流程中找 businessKey
     * @param exEntity
     * @return
     */
    protected String getSuperProcessInstanceBusinessKey(ExecutionEntity exEntity) {
        if (exEntity != null && StringUtils.isNotEmpty(exEntity.getBusinessKey())) {
        	return exEntity.getBusinessKey();
        } else if (exEntity != null && exEntity.getSuperExecution() != null) {
        	return getSuperProcessInstanceBusinessKey(exEntity.getSuperExecution());
        } else if (exEntity != null && exEntity.getParent() != null) {
        	return getSuperProcessInstanceBusinessKey(exEntity.getParent());
        }
        return null;
    }
    
    /**
     * 任务重启，以及自由跳转
     * 
     * @param businessKey
     */
    protected void restartFlow(String businessKey) {

	}

}