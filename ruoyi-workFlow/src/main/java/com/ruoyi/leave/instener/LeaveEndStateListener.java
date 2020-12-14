package com.ruoyi.leave.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.service.IWorkflowLeaveService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

/**
 * 不再使用 Listener 来整合业务，使用 IFlowMediator 来解决
 */
//@Deprecated
//public class LeaveEndStateListener implements ExecutionListener {
//    private Expression state;
//
//    @Override
//    public void notify(DelegateExecution delegateExecution) {
//        WorkflowLeave workflowLeave = new WorkflowLeave();
//        workflowLeave.setId(delegateExecution.getProcessInstanceBusinessKey());
//        workflowLeave.setState(state.getValue(delegateExecution).toString());
//        SpringUtils.getBean(IWorkflowLeaveService.class).updateWorkflowLeave(workflowLeave);
//    }
//}
