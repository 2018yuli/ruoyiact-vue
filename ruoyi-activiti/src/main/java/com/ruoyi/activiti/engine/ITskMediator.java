package com.ruoyi.activiti.engine;

import com.ruoyi.activiti.domain.BizAudit;

/**
 * 多个流程手工任务中介者
 * 处理 并发执行，全局暂停任务，等……
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年10月13日下午5:26:12
 * @Version: 1.0.0
 */
public interface ITskMediator {

	/**
	 * 流程推进
	 * 
	 * @param service
	 * @param audit
	 * @return 1 正常 -1 异常
	 */
    int process(IBaseFlowService service, BizAudit audit);
	
	/**
	 * 向流程引擎添加变量
	 * 
	 * @param procInstId
	 * @param variableName
	 * @param value
	 */
    void setVariables(String procInstId, String variableName, Object value);
	
	/**
	 * 获取流程引擎中的变量
	 * 
	 * @param procInstId
	 * @param variableName
	 * @return
	 */
    Object getVariables(String procInstId, String variableName);
}
