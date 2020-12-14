package com.ruoyi.activiti.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ruoyi.activiti.engine.IBaseFlowService;
import com.ruoyi.common.exception.CustomException;

/**
 * 每个用户任务对应一个 Service, 其中初始化好了与手工任务处理相关业务.
 * 此手工任务处理上下文环境缓存每个 Service, 避免重复创建业务处理 Bean.
 * @author zhaojun
 */
@Component
public class FlowContext implements ApplicationContextAware {
	
	private static final String tskServiceMissing = "此流程实例处理器不存在或初始化失败, 请检查后台参数配置";
	
    /**
     * Map<Integer, AbstractBaseTskService>
     * Map<手工任务 ID, 流程处理 Service>
     */
    private static Map<String, IBaseFlowService> userFlowServiceMap = new ConcurrentHashMap<String, IBaseFlowService>();

    /**
     * 项目启动时, 自动调用数据库已存储的所有流程处理器进行初始化.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	userFlowServiceMap = applicationContext.getBeansOfType(IBaseFlowService.class);
    }


    /**
     * 获取指定驱动器的 Service.
     *
     * @param   flowId
     *          驱动器 ID
     *
     * @return  驱动器对应的 Service
     */
    public IBaseFlowService get(String flowId) {
    	IBaseFlowService abstractBaseFileService = userFlowServiceMap.get(flowId);
        if (abstractBaseFileService == null) {
            throw new CustomException(tskServiceMissing);
        }
        return abstractBaseFileService;
    }

}