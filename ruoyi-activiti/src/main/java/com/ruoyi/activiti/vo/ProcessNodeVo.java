package com.ruoyi.activiti.vo;

import java.util.List;

public class ProcessNodeVo
{
    // 节点id
    private String nodeId;

    // 节点名
    private String name;

    // 节点类型 0开始 1用户任务 2结束
    private Integer type;

    // 关联角色
    private List<Long> roleIds;

    // 关联用户
    private List<Long> userIds;

    // 关联部门
    private List<Long> deptIds;

    // 选操作人的部门负责人
    private Boolean deptHeader = false;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public Boolean getDeptHeader() {
		return deptHeader;
	}

	public void setDeptHeader(Boolean deptHeader) {
		this.deptHeader = deptHeader;
	}
    
    
}