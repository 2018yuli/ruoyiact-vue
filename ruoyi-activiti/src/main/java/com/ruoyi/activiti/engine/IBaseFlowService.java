package com.ruoyi.activiti.engine;

import java.util.List;

import com.ruoyi.activiti.domain.BizAudit;

/**
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月10日下午3:34:39
 * @Version: 1.0.0
 */
public interface IBaseFlowService {

	/**
	 * 更新业务表业务表状态以及流程参数
	 * 
	 * @param audit
	 *    procInstId
	 *    businessKey
	 *    result
	 *    comment
	 *    rate
	 *    assignee
	 * @return 1 正常 -1 异常
	 */
    int execTsk(BizAudit audit);
	
	/**
	 * 获得业务表执行者
	 * 
	 * @param businessKey
	 * @return
	 */
    List<String> getAuditors(String businessKey);
	
	/**
	 * 是否多实例业务表
	 * 
	 * @return
	 */
    boolean isMultiInst();
	
	/**
	 * 业务表类型
	 * @return
	 */
    String getFlowType();
	
    /***
     * 更新业务表状态
     * @param businessKey
     * @param result 审批结果
     * @param comment 评论
     * @param assignee
     */
	int updateFlow(String procInstId, String businessKey, int result, String comment, String assignee);

    /**
     * 更新流程参数
     * @param procInstId
     * @param comment
     * @param rate
     * @param resultAfterTsk 处理后的结果
     */
    void updateVariables(String procInstId, String comment, int rate, int resultAfterTsk);

}