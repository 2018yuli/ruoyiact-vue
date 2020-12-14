package com.ruoyi.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.activiti.domain.ActRuTask;
import com.ruoyi.activiti.mapper.ActRuTaskMapper;
import com.ruoyi.activiti.service.IBizActivitiNativeSearchService;

/**
 * 工作流自定义任务原生 sql 处理 
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月24日下午5:51:41
 * @Version: 1.0.0
 */
@Service
public class BizActivitiNativeSearchServiceImpl implements IBizActivitiNativeSearchService {

	@Autowired
	private ActRuTaskMapper actRuTaskMapper;
	
	@Override
	public List<ActRuTask> selectIng(ActRuTask ruTsk) {
		return actRuTaskMapper.selectIng(ruTsk);
	}

}
