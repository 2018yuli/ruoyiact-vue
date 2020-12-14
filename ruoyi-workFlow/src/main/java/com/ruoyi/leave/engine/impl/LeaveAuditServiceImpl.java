package com.ruoyi.leave.engine.impl;

import com.ruoyi.activiti.engine.IBaseFlowService;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.engine.AbstractBaseFlowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请假审批时调用，ID 为 audit 的用户任务对应的业务处理类
 * 工作流中，每个用户任务，建立和本类类似的，以 ID 为 Bean ID 的业务处理类
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月10日下午4:11:33
 * @Version: 1.0.0
 */
@Service(value = "audit")
public class LeaveAuditServiceImpl extends AbstractBaseFlowService implements IBaseFlowService {


    @Override
	public int updateFlow(String procInstId, String businessKey, int result, String comment, String assignee) {
		WorkflowLeave leave = getFlow(businessKey);
		if (1 == result) {
			leave.setState("已同意");
		}
		else if (2 == result) {
			leave.setState("已拒绝");
		}
		updateFlow(leave);
		return result;
	}

	@Override
	public void updateVariables(String procInstId, String comment, int rate, int resultAfterTsk) {
		// 此处可以向工作流中添加执行参数
    	setVariables(procInstId, "result", resultAfterTsk == 1);
	}

	@Override
	public List<String> getAuditors(String businessKey) {
		WorkflowLeave leave = getFlow(businessKey);
		List<String> rtnList = new ArrayList<String>();
		// 这里根据业务查询审批人，通过任务 Listener 设置任务执行候选人
		if (StringUtils.isNotEmpty(leave.getCreateName()))
			rtnList.add("经理审批");
		return rtnList;
	}

	@Override
	public String getFlowType() {
		return "audit";
	}

}