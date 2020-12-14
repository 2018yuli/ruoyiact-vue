/*
 * @(#)BizAudit.java 2020年1月9日 下午5:16:02
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * <p>File：BizAudit.java</p>
 * <p>Title: 审批记录</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月9日 下午5:16:02</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
public class BizAudit
{
    @Id
    private Long     id;

    private String   taskId;

    private Integer  result;

    private String   comment;

    private String   procDefKey;

    private String   procName;

    private String   applyer;

    private String   auditor;

    private Long     auditorId;

    private Date     createTime;
    
    private Boolean delFlag;

    @Transient
    private String   procInstId;
    
    @Transient
    private String taskDefKey;
    
    @Transient
    private Integer rate;
    
    @Transient
    private String assignee;

    private Long     businessKey;
    
    @Transient
    private String[] taskIds;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @return 
	 */
	public BizAudit setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 * @return 
	 */
	public BizAudit setTaskId(String taskId) {
		this.taskId = taskId;
		return this;
	}

	/**
	 * @return the result
	 */
	public Integer getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 * @return 
	 */
	public BizAudit setResult(Integer result) {
		this.result = result;
		return this;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 * @return 
	 */
	public BizAudit setComment(String comment) {
		this.comment = comment;
		return this;
	}

	/**
	 * @return the procDefKey
	 */
	public String getProcDefKey() {
		return procDefKey;
	}

	/**
	 * @param procDefKey the procDefKey to set
	 * @return 
	 */
	public BizAudit setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
		return this;
	}

	/**
	 * @return the procName
	 */
	public String getProcName() {
		return procName;
	}

	/**
	 * @param procName the procName to set
	 * @return 
	 */
	public BizAudit setProcName(String procName) {
		this.procName = procName;
		return this;
	}

	/**
	 * @return the applyer
	 */
	public String getApplyer() {
		return applyer;
	}

	/**
	 * @param applyer the applyer to set
	 * @return 
	 */
	public BizAudit setApplyer(String applyer) {
		this.applyer = applyer;
		return this;
	}

	/**
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * @param auditor the auditor to set
	 * @return 
	 */
	public BizAudit setAuditor(String auditor) {
		this.auditor = auditor;
		return this;
	}

	/**
	 * @return the auditorId
	 */
	public Long getAuditorId() {
		return auditorId;
	}

	/**
	 * @param auditorId the auditorId to set
	 * @return 
	 */
	public BizAudit setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
		return this;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 * @return 
	 */
	public BizAudit setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	
	public Boolean getDelFlag() {
		return delFlag;
	}

	public BizAudit setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	/**
	 * @return the procInstId
	 */
	public String getProcInstId() {
		return procInstId;
	}

	/**
	 * @param procInstId the procInstId to set
	 * @return 
	 */
	public BizAudit setProcInstId(String procInstId) {
		this.procInstId = procInstId;
		return this;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public BizAudit setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
		return this;
	}

	public Integer getRate() {
		return rate;
	}

	public BizAudit setRate(Integer rate) {
		this.rate = rate;
		return this;
	}

	public String getAssignee() {
		return assignee;
	}

	public BizAudit setAssignee(String assignee) {
		this.assignee = assignee;
		return this;
	}

	/**
	 * @return the businessKey
	 */
	public Long getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey the businessKey to set
	 * @return 
	 */
	public BizAudit setBusinessKey(Long businessKey) {
		this.businessKey = businessKey;
		return this;
	}

	/**
	 * @return the taskIds
	 */
	public String[] getTaskIds() {
		return taskIds;
	}

	/**
	 * @param taskIds the taskIds to set
	 * @return 
	 */
	public BizAudit setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
		return this;
	}

	
    
    
}
