package com.ruoyi.activiti.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 流程对象 
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月7日下午4:52:09
 * @Version: 1.0.0
 */
public class BizBusiness implements Serializable
{
    //
    private static final long serialVersionUID = -7562556845627977390L;

    @Id
    private Long              id;

    private String            title;

    private Long              userId;

    /**
     * 业务ID
     */
    private String            tableId;

    private String            procDefId;

    private String            procDefKey;

    private String            procInstId;

    // 流程名称
    private String            procName;

    // 当前任务节点名称
    private String            currentTask;

    private String            applyer;

    private Integer           status;

    private Integer           result;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              applyTime;

    private Boolean           delFlag;
    
    // 任务重启次数
    private Integer			  restartTimes = 0;

	/**
	 * 父任务ID
	 */
	private String 			  parentId;
	private Long			  tableIndex;

    public BizBusiness() {
	}
    
	public BizBusiness(Long id, String title, Long userId, String tableId, String procDefId, String procDefKey,
			String procInstId, String procName, String currentTask, String applyer, Integer status, Integer result,
			Date applyTime, Boolean delFlag) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
		this.tableId = tableId;
		this.procDefId = procDefId;
		this.procDefKey = procDefKey;
		this.procInstId = procInstId;
		this.procName = procName;
		this.currentTask = currentTask;
		this.applyer = applyer;
		this.status = status;
		this.result = result;
		this.applyTime = applyTime;
		this.delFlag = delFlag;
		this.restartTimes = 0;
	}

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
	public BizBusiness setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 * @return 
	 */
	public BizBusiness setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 * @return 
	 */
	public BizBusiness setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the tableId
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * @param tableId the tableId to set
	 * @return 
	 */
	public BizBusiness setTableId(String tableId) {
		this.tableId = tableId;
		return this;
	}

	/**
	 * @return the procDefId
	 */
	public String getProcDefId() {
		return procDefId;
	}

	/**
	 * @param procDefId the procDefId to set
	 * @return 
	 */
	public BizBusiness setProcDefId(String procDefId) {
		this.procDefId = procDefId;
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
	public BizBusiness setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
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
	public BizBusiness setProcInstId(String procInstId) {
		this.procInstId = procInstId;
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
	public BizBusiness setProcName(String procName) {
		this.procName = procName;
		return this;
	}

	/**
	 * @return the currentTask
	 */
	public String getCurrentTask() {
		return currentTask;
	}

	/**
	 * @param currentTask the currentTask to set
	 * @return 
	 */
	public BizBusiness setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
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
	public BizBusiness setApplyer(String applyer) {
		this.applyer = applyer;
		return this;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 * @return 
	 */
	public BizBusiness setStatus(Integer status) {
		this.status = status;
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
	public BizBusiness setResult(Integer result) {
		this.result = result;
		return this;
	}

	/**
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime the applyTime to set
	 * @return 
	 */
	public BizBusiness setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
		return this;
	}

	/**
	 * @return the delFlag
	 */
	public Boolean getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 * @return 
	 */
	public BizBusiness setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	public Integer getRestartTimes() {
		return restartTimes;
	}

	public BizBusiness setRestartTimes(Integer restartTimes) {
		this.restartTimes = restartTimes;
		return this;
	}

	public String getParentId() {
		return this.parentId;
	}

	public BizBusiness setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	public Long getTableIndex() {
		return this.tableIndex;
	}

	public void setTableIndex(Long tableIndex) {
		this.tableIndex = tableIndex;
	}
}
