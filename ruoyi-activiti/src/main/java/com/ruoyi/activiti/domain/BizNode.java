/*
 * @(#)BizNode.java 2020年1月14日 上午10:59:12
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.domain;

import javax.persistence.Id;

/**
 * <p>File：BizNode.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月14日 上午10:59:12</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
public class BizNode
{
    @Id
    private Long    id;

    /** 节点ID*/
    private String  nodeId;

    /** 类型 1：角色 2：部门负责人 3：用户 4：所属部门负责人*/
    private Integer type;

    /** 类型对应负责人的值*/
    private Long    auditor;

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
	public BizNode setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 * @return 
	 */
	public BizNode setNodeId(String nodeId) {
		this.nodeId = nodeId;
		return this;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 * @return 
	 */
	public BizNode setType(Integer type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the auditor
	 */
	public Long getAuditor() {
		return auditor;
	}

	/**
	 * @param auditor the auditor to set
	 * @return 
	 */
	public BizNode setAuditor(Long auditor) {
		this.auditor = auditor;
		return this;
	}
    
    
}
