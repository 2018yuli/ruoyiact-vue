package com.ruoyi.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.activiti.domain.ActReProcdef;
import com.ruoyi.activiti.service.IActReProcdefService;
import com.ruoyi.activiti.vo.ReProcdef;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 流程控制接口
 *
 * @Auther: Ace Lee
 * @Date: 2019/3/5 15:04
 */
@RestController
@RequestMapping("act/prof")
public class ActivitiController extends BaseController
{
    @Autowired
    private RepositoryService    repositoryService;

    @Autowired
    private RuntimeService       runtimeService;

    @Autowired
    private IActReProcdefService procdefService;

    /**
     * 启动一个流程
     * 
     * @param key
     * @return
     * @author zmr
     */
    @GetMapping("start/{key}")
    public AjaxResult start(@PathVariable("key") String key)
    {
        runtimeService.startProcessInstanceByKey(key);
        return AjaxResult.success();
    }

    @GetMapping("allLatest")
    public AjaxResult list()
    {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().latestVersion();
        List<ProcessDefinition> processDefinitions = query.list();
        List<ReProcdef> list = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions)
        {
            ReProcdef reProcdef = new ReProcdef(processDefinition);
            list.add(reProcdef);
        }
        return AjaxResult.success().put("rows", list);
    }

    @GetMapping("list")
    public AjaxResult list(ActReProcdef actReProcdef)
    {
        startPage();
        return result(procdefService.selectList(actReProcdef));
    }

    @PostMapping("remove")
    public AjaxResult deleteOne(String ids)
    {
        String[] idArr = ids.split(",");
        for (String id : idArr)
        {
            long count = runtimeService.createProcessInstanceQuery().deploymentId(id).count();
            if (count > 0)
            {
                return AjaxResult.error("流程正在运行中，无法删除");
            }
            else
            {
                // 根据deploymentID删除定义的流程，普通删除
                repositoryService.deleteDeployment(id);
            }
            // 强制删除
            // repositoryService.deleteDeployment(id, true);
            // System.out.println("强制删除--流程定义删除成功");
        }
        return AjaxResult.success();
    }
}
