package com.ruoyi.activiti.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程设计模型部署对象 ACT_RE_MODEL
 * 
 * @author ruoyi
 * @date 2019-11-26
 */
public class ActReModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String            id;

    /** 乐观锁 */
    private Long              rev;

    /** 名称 */
    @Excel(name = "名称")
    private String            name;

    /** 模型标识 */
    @Excel(name = "模型标识")
    private String            key;

    /** 分类 */
    @Excel(name = "分类")
    private String            category;

    /** 最新修改时间 */
    private Date              lastUpdateTime;

    /** 版本 */
    @Excel(name = "版本")
    private Long              version;

    /** 以json格式保存流程定义的信息 */
    private String            metaInfo;

    /** 部署ID */
    @Excel(name = "部署ID")
    private String            deploymentId;

    /** 编辑源值ID */
    private String            editorSourceValueId;

    /** 编辑源额外值ID */
    private String            editorSourceExtraValueId;

    /** 租户 */
    private String            tenantId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getEditorSourceValueId() {
		return editorSourceValueId;
	}

	public void setEditorSourceValueId(String editorSourceValueId) {
		this.editorSourceValueId = editorSourceValueId;
	}

	public String getEditorSourceExtraValueId() {
		return editorSourceExtraValueId;
	}

	public void setEditorSourceExtraValueId(String editorSourceExtraValueId) {
		this.editorSourceExtraValueId = editorSourceExtraValueId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, createTime, deploymentId, editorSourceExtraValueId, editorSourceValueId, id, key,
				lastUpdateTime, metaInfo, name, rev, tenantId, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActReModel other = (ActReModel) obj;
		return Objects.equals(category, other.category) && Objects.equals(createTime, other.createTime)
				&& Objects.equals(deploymentId, other.deploymentId)
				&& Objects.equals(editorSourceExtraValueId, other.editorSourceExtraValueId)
				&& Objects.equals(editorSourceValueId, other.editorSourceValueId) && Objects.equals(id, other.id)
				&& Objects.equals(key, other.key) && Objects.equals(lastUpdateTime, other.lastUpdateTime)
				&& Objects.equals(metaInfo, other.metaInfo) && Objects.equals(name, other.name)
				&& Objects.equals(rev, other.rev) && Objects.equals(tenantId, other.tenantId)
				&& Objects.equals(version, other.version);
	}

	
    
}
