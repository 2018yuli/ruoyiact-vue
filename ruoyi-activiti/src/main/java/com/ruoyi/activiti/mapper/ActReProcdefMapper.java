/*
 * @(#)ActReProcdefMapper.java 2020年1月6日 上午10:20:27
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.mapper;

import java.util.List;

import com.ruoyi.activiti.domain.ActReProcdef;

/**
 * 流程定义Mapper接口
 * 
 * @author liyu
 * @date 2020-09-02
 */
public interface ActReProcdefMapper 
{
    /**
     * 查询流程定义
     * 
     * @param id 流程定义ID
     * @return 流程定义
     */
    ActReProcdef selectActReProcdefById(String id);

    /**
     * 查询流程定义列表
     * 
     * @param actReProcdef 流程定义
     * @return 流程定义集合
     */
    List<ActReProcdef> selectActReProcdefList(ActReProcdef actReProcdef);

    /**
     * 新增流程定义
     * 
     * @param actReProcdef 流程定义
     * @return 结果
     */
    int insertActReProcdef(ActReProcdef actReProcdef);

    /**
     * 修改流程定义
     * 
     * @param actReProcdef 流程定义
     * @return 结果
     */
    int updateActReProcdef(ActReProcdef actReProcdef);

    /**
     * 删除流程定义
     * 
     * @param id 流程定义ID
     * @return 结果
     */
    int deleteActReProcdefById(String id);

    /**
     * 批量删除流程定义
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteActReProcdefByIds(String[] ids);
}