package com.ruoyi.activiti.service;

import java.util.List;

import com.ruoyi.activiti.domain.ActRuTask;

public interface IBizActivitiNativeSearchService {

	/**
	 * 待办任务查询
	 * @return
	 */
	List<ActRuTask> selectIng(ActRuTask ruTsk);
}
