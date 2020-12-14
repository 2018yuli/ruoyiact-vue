package com.ruoyi.activiti.vo;

import org.activiti.engine.repository.ProcessDefinition;

public class ReProcdef
{
    private String  id;

    private Integer rev;

    private String  category;

    private String  name;

    private String  key;

    private Integer version;

    private String  deploymentId;

    private String  resourceName;

    private String  dgrmResourceName;

    private String  description;

    private Byte    hasStartFormKey;

    private Byte    hasGraphicalNotation;

    private Integer suspensionState;

    private String  tenantId;

    private String  engineVersion;

    public ReProcdef(ProcessDefinition processDefinition)
    {
        setId(processDefinition.getId());
        setCategory(processDefinition.getCategory());
        setName(processDefinition.getName());
        setKey(processDefinition.getKey());
        setVersion(processDefinition.getVersion());
        setDeploymentId(processDefinition.getDeploymentId());
        setResourceName(processDefinition.getResourceName());
        setDgrmResourceName(processDefinition.getDiagramResourceName());
        setDescription(processDefinition.getDescription());
    }

    public ReProcdef(String id, Integer rev, String category, String name, String key, Integer version,
            String deploymentId, String resourceName, String dgrmResourceName, String description, Byte hasStartFormKey,
            Byte hasGraphicalNotation, Integer suspensionState, String tenantId, String engineVersion)
    {
        this.id = id;
        this.rev = rev;
        this.category = category;
        this.name = name;
        this.key = key;
        this.version = version;
        this.deploymentId = deploymentId;
        this.resourceName = resourceName;
        this.dgrmResourceName = dgrmResourceName;
        this.description = description;
        this.hasStartFormKey = hasStartFormKey;
        this.hasGraphicalNotation = hasGraphicalNotation;
        this.suspensionState = suspensionState;
        this.tenantId = tenantId;
        this.engineVersion = engineVersion;
    }

	public String getId() {
		return id;
	}

	public ReProcdef setId(String id) {
		this.id = id;
		return this;
	}

	public Integer getRev() {
		return rev;
	}

	public ReProcdef setRev(Integer rev) {
		this.rev = rev;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public ReProcdef setCategory(String category) {
		this.category = category;
		return this;
	}

	public String getName() {
		return name;
	}

	public ReProcdef setName(String name) {
		this.name = name;
		return this;
	}

	public String getKey() {
		return key;
	}

	public ReProcdef setKey(String key) {
		this.key = key;
		return this;
	}

	public Integer getVersion() {
		return version;
	}

	public ReProcdef setVersion(Integer version) {
		this.version = version;
		return this;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public ReProcdef setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}

	public String getResourceName() {
		return resourceName;
	}

	public ReProcdef setResourceName(String resourceName) {
		this.resourceName = resourceName;
		return this;
	}

	public String getDgrmResourceName() {
		return dgrmResourceName;
	}

	public ReProcdef setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ReProcdef setDescription(String description) {
		this.description = description;
		return this;
	}

	public Byte getHasStartFormKey() {
		return hasStartFormKey;
	}

	public ReProcdef setHasStartFormKey(Byte hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
		return this;
	}

	public Byte getHasGraphicalNotation() {
		return hasGraphicalNotation;
	}

	public ReProcdef setHasGraphicalNotation(Byte hasGraphicalNotation) {
		this.hasGraphicalNotation = hasGraphicalNotation;
		return this;
	}

	public Integer getSuspensionState() {
		return suspensionState;
	}

	public ReProcdef setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
		return this;
	}

	public String getTenantId() {
		return tenantId;
	}

	public ReProcdef setTenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public ReProcdef setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
		return this;
	}
    
    
}