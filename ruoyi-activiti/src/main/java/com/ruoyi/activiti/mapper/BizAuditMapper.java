/*
 * @(#)ActBusinessMapper.java 2020年1月6日 下午3:38:12
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.mapper;

import java.util.List;

import com.ruoyi.activiti.domain.BizAudit;
import com.ruoyi.activiti.vo.HiTaskVo;

/**
 * <p>File：BizAuditMapper.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 下午3:38:12</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
public interface BizAuditMapper
{
    List<HiTaskVo> getHistoryTaskList(HiTaskVo hiTaskVo);

    /**
     * logic删除
     * @param ids
     * @return
     * @author zmr
     */
    int deleteLogic(String[] ids);
    
    /**
     * 查询业务审核记录
     * 
     * @param id 业务审核记录ID
     * @return 业务审核记录
     */
    BizAudit selectBizAuditById(Long id);

    /**
     * 查询业务审核记录列表
     * 
     * @param bizAudit 业务审核记录
     * @return 业务审核记录集合
     */
    List<BizAudit> selectBizAuditList(BizAudit bizAudit);

    /**
     * 新增业务审核记录
     * 
     * @param bizAudit 业务审核记录
     * @return 结果
     */
    int insertBizAudit(BizAudit bizAudit);

    /**
     * 修改业务审核记录
     * 
     * @param bizAudit 业务审核记录
     * @return 结果
     */
    int updateBizAudit(BizAudit bizAudit);

    /**
     * 删除业务审核记录
     * 
     * @param id 业务审核记录ID
     * @return 结果
     */
    int deleteBizAuditById(Long id);

    /**
     * 批量删除业务审核记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBizAuditByIds(Long[] ids);
}
