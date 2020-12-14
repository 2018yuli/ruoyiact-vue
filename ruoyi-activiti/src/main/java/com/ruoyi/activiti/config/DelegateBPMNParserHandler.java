package com.ruoyi.activiti.config;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workflow listeners each node
 * 
 * @author yu.li l898974766@outlook.com
 * @Description:
 * @Date: 2020年9月14日下午2:56:31
 * @Version: 1.0.0
 */
public class DelegateBPMNParserHandler extends UserTaskParseHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(DelegateBPMNParserHandler.class);

    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        logger.debug("bpmnParse : {}, userTask : {}", bpmnParse, userTask);
        super.executeParse(bpmnParse, userTask);
        
        TaskDefinition taskDefinition = (TaskDefinition) bpmnParse
                .getCurrentActivity().getProperty(PROPERTY_TASK_DEFINITION);
        
        ActivitiListener activitiListener = new ActivitiListener();
        activitiListener.setEvent(TaskListener.EVENTNAME_CREATE);
        activitiListener.setEvent(ExecutionListener.EVENTNAME_START);
        activitiListener
                .setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        activitiListener.setImplementation("#{globalTaskListener}");
        
        taskDefinition
                .addTaskListener(TaskListener.EVENTNAME_CREATE, bpmnParse
                        .getListenerFactory()
                        .createDelegateExpressionTaskListener(activitiListener));
        
        taskDefinition
        .addTaskListener(ExecutionListener.EVENTNAME_START, bpmnParse
                .getListenerFactory()
                .createDelegateExpressionTaskListener(activitiListener));
        
    }

}
