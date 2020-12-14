package com.ruoyi.activiti.mapper;

import java.util.List;
import com.ruoyi.activiti.domain.BizBusiness;

/**
 * 业务Mapper接口
 * 
 * @author liyu
 * @date 2020-09-02
 */
public interface BizBusinessMapper 
{
    /**
     * 查询业务
     * 
     * @param id 业务ID
     * @return 业务
     */
    BizBusiness selectBizBusinessById(Long id);

    /**
     * 查询业务列表
     * 
     * @param bizBusiness 业务
     * @return 业务集合
     */
    List<BizBusiness> selectBizBusinessList(BizBusiness bizBusiness);

    /**
     * 新增业务
     * 
     * @param bizBusiness 业务
     * @return 结果
     */
    int insertBizBusiness(BizBusiness bizBusiness);

    /**
     * 修改业务
     * 
     * @param bizBusiness 业务
     * @return 结果
     */
    int updateBizBusiness(BizBusiness bizBusiness);

    /**
     * 删除业务
     * 
     * @param id 业务ID
     * @return 结果
     */
    int deleteBizBusinessById(Long id);

    /**
     * 批量删除业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBizBusinessByIds(Long[] ids);
}