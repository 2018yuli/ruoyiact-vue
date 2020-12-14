package com.ruoyi.leave.engine;

import java.io.Serializable;
import java.util.List;

import com.ruoyi.activiti.context.FlowContext;
import com.ruoyi.activiti.engine.IBaseFlowService;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xvshu on 2019/1/22 0022.
 */
@Service("FLOW")
public class ActivitiService implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ActivitiService.class);
	
	@Autowired
	private FlowContext tskContext;
 
 
	public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("ProductFactoryNewProductTask begin！");
        //Boolean val = (Boolean)delegateExecution.getVariable("pass");
//        Boolean val = (Boolean) pass.getValue(delegateExecution);
        logger.info("=ProductFactoryNewProductTask=>business:{} prossId:{}",delegateExecution.getProcessBusinessKey(),delegateExecution.getProcessInstanceId());
        //修改产品状态为审核中
        logger.info("ProductFactoryNewProductTask end！");
    }
    
    public List<String> resolveUsersForFlow(DelegateExecution delegateExecution) {
    	String businessKey = delegateExecution.getProcessBusinessKey();
    	IBaseFlowService tskService = tskContext.get("ConfirmAnd");
    	return tskService.getAuditors(businessKey);
	}
}