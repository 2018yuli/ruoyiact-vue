package com.ruoyi.activiti.mapper;

import java.util.List;
import com.ruoyi.activiti.domain.BizNode;

/**
 * 业务节点Mapper接口
 * 
 * @author liyu
 * @date 2020-09-02
 */
public interface BizNodeMapper 
{
    /**
     * 查询业务节点
     * 
     * @param id 业务节点ID
     * @return 业务节点
     */
    BizNode selectBizNodeById(Long id);

    /**
     * 查询业务节点列表
     * 
     * @param bizNode 业务节点
     * @return 业务节点集合
     */
    List<BizNode> selectBizNodeList(BizNode bizNode);

    /**
     * 新增业务节点
     * 
     * @param bizNode 业务节点
     * @return 结果
     */
    int insertBizNode(BizNode bizNode);

    /**
     * 修改业务节点
     * 
     * @param bizNode 业务节点
     * @return 结果
     */
    int updateBizNode(BizNode bizNode);

    /**
     * 删除业务节点
     * 
     * @param id 业务节点ID
     * @return 结果
     */
    int deleteBizNodeById(Long id);

    /**
     * 批量删除业务节点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBizNodeByIds(Long[] ids);
    
    /**
     * 根据 NodeId 删除业务节点
     * 
     * @param nodeId
     * @return
     */
    int deleteBizNodeByNodeId(String nodeId);
}