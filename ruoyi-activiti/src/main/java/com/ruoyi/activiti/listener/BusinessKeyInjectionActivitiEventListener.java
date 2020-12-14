package com.ruoyi.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * 为子流程添加 businessKey 的监听器
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月15日上午11:21:16
 * @Version: 1.0.0
 */
public class BusinessKeyInjectionActivitiEventListener implements ActivitiEventListener {
	
    private final Logger log = LoggerFactory.getLogger(BusinessKeyInjectionActivitiEventListener.class);
    
    @Override
    public void onEvent(ActivitiEvent event) {
    	switch (event.getType()) {
	    	case PROCESS_STARTED:
	    		if (event instanceof ActivitiProcessStartedEvent) {
	    			ActivitiProcessStartedEvent processStartedEvent = (ActivitiProcessStartedEvent) event;
	    			ExecutionEntity execEntity = (ExecutionEntity) processStartedEvent.getEntity();
	    			if (StringUtils.isEmpty(execEntity.getProcessBusinessKey())) {
	    				String key = getSuperProcessInstanceBusinessKey(execEntity);
	    				if (StringUtils.isNoneEmpty(key)) {
	    					execEntity.setBusinessKey(key);
	    					execEntity.updateProcessBusinessKey(key);
	    					// 更新父类
	                        updateSuperProcessInstanceBusinessKey(execEntity, key);
	    				}
	    			}
	    		}
	    		break;
            case TASK_CREATED:
                if (event instanceof ActivitiEntityEvent) {
                    ActivitiEntityEvent activityEntityEvent = (ActivitiEntityEvent) event;
 
                    TaskEntity taskEntity = (TaskEntity) activityEntityEvent.getEntity();
                    ExecutionEntity exEntity = taskEntity.getExecution();
                    
                    String key = exEntity.getBusinessKey();
                    log.debug("获取当前任务的流程实例的businessKey:{}",key);
                    if(StringUtils.isEmpty(key)){
                    	key = getSuperProcessInstanceBusinessKey(exEntity);
                        // 项目首个事务还未完成，从线程中取得
                        if (StringUtils.isEmpty(key)) {
                            // 在外层工作流添加事务后，由于属于同一个事务，所以不再需要通过线程自己传递 businessKey
//                        	key = MyExecutionListener.bussinessKeyLocal.get();
                        }
                        log.debug("获取当前任务 上一个流程实例的businessKey:{}",key);
                        log.debug("设置当前流程实例的businessKey:{}",key);
                        exEntity.setBusinessKey(key);
                        //让businessKey生效 此处非常关键。
                        exEntity.updateProcessBusinessKey(key);
                        // 更新父类
                        updateSuperProcessInstanceBusinessKey(exEntity, key);
                    }
                    // 设置流程实例 name
//                    String name = exEntity.getName();
//                    if (StringUtils.isEmpty(name) && sourceEntity != null) {
//                    	name = sourceEntity.getName();
//                    	if (StringUtils.isNotEmpty(name)) {
//                    		exEntity.setName(name);
//                    		log.info("设置当前流程实例的name:{}", name);
//                    	}
//                    }
                    break;
                }
            default:
                break;
        }
    }
    
    /**
     * 从父流程中找 businessKey
     * @param exEntity
     * @return
     */
    private String getSuperProcessInstanceBusinessKey(ExecutionEntity exEntity) {
        if (exEntity != null && StringUtils.isNotEmpty(exEntity.getBusinessKey())) {
        	return exEntity.getBusinessKey();
        } else if (exEntity != null && exEntity.getSuperExecution() != null) {
        	return getSuperProcessInstanceBusinessKey(exEntity.getSuperExecution());
        } else if (exEntity != null && exEntity.getParent() != null) {
        	return getSuperProcessInstanceBusinessKey(exEntity.getParent());
        }
        return null;
    }
    
    /**
     * 从父流程中找 businessKey
     * @param exEntity
     * @return
     */
    private void updateSuperProcessInstanceBusinessKey(ExecutionEntity exEntity, String businessKey) {
        if (exEntity != null && StringUtils.isEmpty(exEntity.getBusinessKey())) {
        	exEntity.setBusinessKey(businessKey);
        	exEntity.updateProcessBusinessKey(businessKey);
        } 
        else if (exEntity != null && exEntity.getSuperExecution() != null) {
        	updateSuperProcessInstanceBusinessKey(exEntity.getSuperExecution(), businessKey);
        } 
        else if (exEntity != null && exEntity.getParent() != null) {
        	updateSuperProcessInstanceBusinessKey(exEntity.getParent(), businessKey);
        }
    }
    
    @Override
    public boolean isFailOnException() {
        return false;
    }
}