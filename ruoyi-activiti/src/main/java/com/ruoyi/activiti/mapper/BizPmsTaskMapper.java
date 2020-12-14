package com.ruoyi.activiti.mapper;

import java.util.Map;

/**
 * 任务Mapper接口
 * 
 * @author liyu
 * @date 2020-09-18
 */
public interface BizPmsTaskMapper
{
    /**
     * 更新任务指派人
     * @param param
     */
//	@Update("update ACT_HI_IDENTITYLINK set "
//			+ "USER_ID_=#{newUserId} "
//			+ "where TASK_ID_=#{tskId} "
//			+ "and TYPE_ = 'candidate' ")
    void updateCandidate(Map<String, String> param);
    
}
