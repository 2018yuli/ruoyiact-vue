/*
 * @(#)ActReProcdef.java 2020年1月3日 下午6:19:47
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.domain;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <p>File：ActReProcdef.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月3日 下午6:19:47</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
public class ActReProcdef
{
    /** id */
    @Id
    @Column(name = "ID_")
    private String  id;

    /** 流程名称（该编号就是流程文件process元素的name属性值） */
    @Column(name = "NAME_")
    private String  name;

    /** 流程编号(该编号就是流程文件process元素的id属性值) */
    @Column(name = "KEY_")
    private String  key;

    /** 版本号 */
    @Column(name = "REV_")
    private String  rev;

    /** 流程命名空间（该编号就是流程文件targetNamespace的属性值） */
    @Column(name = "CATEGORY_")
    private String  category;

    /** 流程版本号（由程序控制，新增即为1，修改后依次加1来完成的） */
    @Column(name = "VERSION_")
    private String  version;

    /** 部署编号 部署表ID*/
    @Column(name = "DEPLOYMENT_ID_")
    private String  deploymentId;

    /** 资源文件名称 流程bpmn文件名称*/
    @Column(name = "RESOURCE_NAME_")
    private String  resourceName;

    /** 图片资源文件名称 png流程图片名称*/
    @Column(name = "DGRM_RESOURCE_NAME_")
    private String  dgrmResourceName;

    /** 描述信息 */
    @Column(name = "DESCRIPTION_")
    private String  description;

    /** 是否从key启动 start节点是否存在formKey 0否  1是 */
    @Column(name = "HAS_START_FORM_KEY_")
    private Boolean hasStartFormKey;

    /** 是否挂起 1激活 2挂起 */
    @Column(name = "SUSPENSION_STATE_")
    private Integer  suspensionState;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @return 
	 */
	public ActReProcdef setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @return 
	 */
	public ActReProcdef setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 * @return 
	 */
	public ActReProcdef setKey(String key) {
		this.key = key;
		return this;
	}

	/**
	 * @return the rev
	 */
	public String getRev() {
		return rev;
	}

	/**
	 * @param rev the rev to set
	 * @return 
	 */
	public ActReProcdef setRev(String rev) {
		this.rev = rev;
		return this;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 * @return 
	 */
	public ActReProcdef setCategory(String category) {
		this.category = category;
		return this;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 * @return 
	 */
	public ActReProcdef setVersion(String version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the deploymentId
	 */
	public String getDeploymentId() {
		return deploymentId;
	}

	/**
	 * @param deploymentId the deploymentId to set
	 * @return 
	 */
	public ActReProcdef setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 * @return 
	 */
	public ActReProcdef setResourceName(String resourceName) {
		this.resourceName = resourceName;
		return this;
	}

	/**
	 * @return the dgrmResourceName
	 */
	public String getDgrmResourceName() {
		return dgrmResourceName;
	}

	/**
	 * @param dgrmResourceName the dgrmResourceName to set
	 * @return 
	 */
	public ActReProcdef setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
		return this;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 * @return 
	 */
	public ActReProcdef setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * @return the hasStartFormKey
	 */
	public Boolean getHasStartFormKey() {
		return hasStartFormKey;
	}

	/**
	 * @param hasStartFormKey the hasStartFormKey to set
	 * @return 
	 */
	public ActReProcdef setHasStartFormKey(Boolean hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
		return this;
	}

	/**
	 * @return the suspensionState
	 */
	public Integer getSuspensionState() {
		return suspensionState;
	}

	/**
	 * @param suspensionState the suspensionState to set
	 * @return 
	 */
	public ActReProcdef setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
		return this;
	}
    
    
}
