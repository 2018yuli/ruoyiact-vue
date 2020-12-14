/*
 * @(#)AuditForm.java 2020年1月9日 上午11:19:51
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ruoyi.activiti.consts.ActivitiConstant;

/**
 * <p>File：AuditForm.java</p>
 * <p>Title: 审批表单</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月9日 上午11:19:51</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
public class AuditForm implements Serializable
{//
    private static final long serialVersionUID = 4220317062676914258L;

    // 任务id
    @NotBlank(message = "任务id不能为空")
    private String            id;

    // 流程实例id
    @NotBlank(message = "流程实例id不能为空")
    private String            procInstId;

    // 审批意见
    private String            comment;

    // 审批结果 2通过(默认) 3驳回
    private Integer           result           = ActivitiConstant.RESULT_PASS;

    @NotNull(message = "业务key不能为空")
    private Long              businessKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Long getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(Long businessKey) {
		this.businessKey = businessKey;
	}
    
    
}
