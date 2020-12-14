package com.ruoyi.activiti.service;

import java.util.List;

import com.ruoyi.activiti.domain.ActReModel;

/**
 * 流程设计模型部署Service接口
 * 
 * @author ruoyi
 * @date 2019-11-26
 */
public interface IActReModelService 
{
    /**
     * 查询流程设计模型部署
     * 
     * @param id 流程设计模型部署ID
     * @return 流程设计模型部署
     */
    ActReModel selectActReModelById(String id);

    /**
     * 查询流程设计模型部署列表
     * 
     * @param actReModel 流程设计模型部署
     * @return 流程设计模型部署集合
     */
    List<ActReModel> selectActReModelList(ActReModel actReModel);

    /**
     * 新增流程设计模型部署
     * 
     * @param actReModel 流程设计模型部署
     * @return 结果
     */
    int insertActReModel(ActReModel actReModel);

    /**
     * 修改流程设计模型部署
     * 
     * @param actReModel 流程设计模型部署
     * @return 结果
     */
    int updateActReModel(ActReModel actReModel);

    /**
     * 批量删除流程设计模型部署
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteActReModelByIds(String ids);

    /**
     * 删除流程设计模型部署信息
     * 
     * @param id 流程设计模型部署ID
     * @return 结果
     */
    int deleteActReModelById(String id);
}
