package com.ruoyi.activiti.vo;

import java.util.Date;

import org.activiti.engine.task.Task;

import com.ruoyi.activiti.domain.ActRuTask;

public class RuTask
{
    // 编号
    private String  id;

    // 版本
    private Integer rev;

    // 执行实例ID
    private String  executionId;

    // 流程实例编号
    private String  procInstId;

    // 流程定义编号
    private String  procDefId;

    // 节点定义名称
    private String  name;

    // 父节点实例ID
    private String  parentTaskId;

    // 节点定义描述
    private String  description;

    // 任务定义的ID
    private String  taskDefKey;

    // 委托人（一般情况下为空，只有在委托时才有值）
    private String  owner;

    // 签收人或委托人
    private String  assignee;

    // 发起人
    private String  applyer;

    // 委托类型，DelegationState分为两种：PENDING，RESOLVED。如无委托则为空
    private String  delegation;

    // 优先级别，默认为：50
    private Integer priority;

    private Date    createTime;

    // 执行时间
    private Date    dueDate;

    private String  category;

    // 是否挂起
    private Boolean suspended;

    private String  tenantId;

    private String  formKey;

    // 流程名称（请假标题）
    private String  processName;

    // 流程定义名称
    private String  processDefName;

    // 流程定义key
    private String  processDefKey;

    private String  businessKey;
    
    // 业务类型
    private String tableName;
    // 业务表ID
    private String tableId;
    // 申请人ID、姓名
    private String userId;
    private String userName;
    
	public RuTask() {
	}
	
    public RuTask(String id, Integer rev, String executionId, String procInstId, String procDefId, String name,
			String parentTaskId, String description, String taskDefKey, String owner, String assignee, String applyer,
			String delegation, Integer priority, Date createTime, Date dueDate, String category, Boolean suspended,
			String tenantId, String formKey, String processName, String processDefName, String processDefKey,
			String businessKey, String tableName, String tableId, String userId, String userName) {
		this.id = id;
		this.rev = rev;
		this.executionId = executionId;
		this.procInstId = procInstId;
		this.procDefId = procDefId;
		this.name = name;
		this.parentTaskId = parentTaskId;
		this.description = description;
		this.taskDefKey = taskDefKey;
		this.owner = owner;
		this.assignee = assignee;
		this.applyer = applyer;
		this.delegation = delegation;
		this.priority = priority;
		this.createTime = createTime;
		this.dueDate = dueDate;
		this.category = category;
		this.suspended = suspended;
		this.tenantId = tenantId;
		this.formKey = formKey;
		this.processName = processName;
		this.processDefName = processDefName;
		this.processDefKey = processDefKey;
		this.businessKey = businessKey;
		this.tableName = tableName;
		this.tableId = tableId;
		this.userId = userId;
		this.userName = userName;
	}

	public RuTask(Task task)
    {
        setId(task.getId());
        setName(task.getName());
        setAssignee(task.getAssignee());
        setExecutionId(task.getExecutionId());
        setTenantId(task.getTenantId());
        setParentTaskId(task.getParentTaskId());
        setProcDefId(task.getProcessDefinitionId());
        setProcInstId(task.getProcessInstanceId());
        setCategory(task.getCategory());
        setFormKey(task.getFormKey());
        setDescription(task.getDescription());
        setOwner(task.getOwner());
        setTaskDefKey(task.getTaskDefinitionKey());
        setDelegation("");
        setCreateTime(task.getCreateTime());
        setDueDate(task.getDueDate());
        setPriority(task.getPriority());
        setSuspended(task.isSuspended());
    }
	public RuTask(ActRuTask task)
    {
        setId(task.getId());
        setName(task.getName());
        setAssignee(task.getAssignee());
        setExecutionId(task.getExecutionId());
        setTenantId(task.getTenantId());
        setParentTaskId(task.getParentTaskId());
        setProcDefId(task.getProcDefId());
        setProcInstId(task.getProcInstId());
        setCategory(task.getCategory());
        setFormKey(task.getFormKey());
        setDescription(task.getDescription());
        setOwner(task.getOwner());
        setTaskDefKey(task.getTaskDefKey());
        setDelegation("");
        setCreateTime(task.getCreateTime());
        setDueDate(task.getDueDate());
        setPriority(task.getPriority());
        setSuspended(task.getSuspensionState() != 1);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRev() {
		return rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getDelegation() {
		return delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessDefName() {
		return processDefName;
	}

	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}

	public String getProcessDefKey() {
		return processDefKey;
	}

	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}