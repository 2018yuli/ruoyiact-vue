package com.ruoyi.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.activiti.consts.ActivitiConstant;
import com.ruoyi.activiti.domain.BizBusiness;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.activiti.service.IHistoryInfoService;
import com.ruoyi.activiti.vo.HiProcInsVo;
import com.ruoyi.activiti.vo.ProcessInsVo;
import com.ruoyi.activiti.vo.RuTask;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;

/**
 * <p>File：ProcessInsController.java</p>
 * <p>Title: 流程实例</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 下午1:27:18</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
@RestController
@RequestMapping("act/process/ins/")
public class ProcessInsController extends BaseController
{
	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    
	@Autowired
    private RuntimeService      runtimeService;

    @Autowired
    private TaskService         taskService;

    @Autowired
    private HistoryService      historyService;

    @Autowired
    private IHistoryInfoService historyInfoService;

    @Autowired
    private ISysUserService   userService;

    @Autowired
    private IBizBusinessService bizBusinessService;

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{processInstanceId}/{state}")
    public AjaxResult updateState(@PathVariable("state") String state,
            @PathVariable("processInstanceId") String processInstanceId)
    {
        if (state.equals("active"))
        {
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("已激活ID为:{}的流程实例", processInstanceId);
        }
        else if (state.equals("suspend"))
        {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("已挂起ID为:{}的流程实例", processInstanceId);
        }
        return AjaxResult.success();
    }

    /**
     * 获取任务列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "tasks")
    public AjaxResult tasks(PageDomain page)
    {
        List<Task> tasks = taskService.createTaskQuery().listPage(page.getPageSize() * (page.getPageNum() - 1),
                page.getPageSize());
        long count = runtimeService.createExecutionQuery().count();
        List<RuTask> list = new ArrayList<>();
        for (Task task : tasks)
        {
            list.add(new RuTask(task));
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", page.getPageNum());
        m.put("total", count);
        return AjaxResult.success(m);
    }

    @RequestMapping(value = "runs")
    public AjaxResult getList(PageDomain page, String name, String key)
    {
        List<ProcessInsVo> list = new ArrayList<>();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc();
        if (StringUtils.isNotBlank(name))
        {
            query.processInstanceNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(key))
        {
            query.processDefinitionKey(key);
        }
        long count = query.count();
        int first = (page.getPageNum() - 1) * page.getPageSize();
        List<ProcessInstance> processInstanceList = query.listPage(first, page.getPageSize());
        processInstanceList.forEach(e -> {
            list.add(new ProcessInsVo(e));
        });
        list.forEach(e -> {
            List<HistoricIdentityLink> identityLinks = historyService
                    .getHistoricIdentityLinksForProcessInstance(e.getId());
            for (HistoricIdentityLink hik : identityLinks)
            {
                // 关联发起人
                if ("starter".equals(hik.getType()) && StringUtils.isNotBlank(hik.getUserId()))
                {
                    e.setApplyer(userService.selectUserById(Long.valueOf(hik.getUserId())).getUserName());
                }
            }
            // 关联当前任务
            Task task = taskService.createTaskQuery().processInstanceId(e.getId()).singleResult();
            if (task != null)
            {
                e.setCurrTaskName(task.getName());
            }
        });
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", page.getPageNum());
        m.put("total", count);
        return AjaxResult.success(m);
    }

    @RequestMapping(value = "finished")
    public AjaxResult finush(HiProcInsVo hiProcInsVo)
    {
        startPage();
        return result(historyInfoService.getHiProcInsListDone(hiProcInsVo));
    }

    /**
     * 删除运行中实例
     * 
     * @param ids
     * @param reason
     * @return
     * @author zmr
     */
    @PostMapping(value = "remove")
    public AjaxResult remove(String ids, String reason)
    {
        if (StringUtils.isBlank(reason))
        {
            reason = "";
        }
        String[] idArr = ids.split(",");
        for (String id : idArr)
        {
            // 关联业务状态结束
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
            BizBusiness bizBusiness = new BizBusiness();
            bizBusiness.setId(Long.valueOf(pi.getBusinessKey()));
            bizBusiness.setCurrentTask(ActivitiConstant.END_TASK_NAME);
            bizBusiness.setStatus(ActivitiConstant.STATUS_CANCELED);
            bizBusiness.setResult(ActivitiConstant.RESULT_CANCELED);
            bizBusinessService.updateBizBusiness(bizBusiness);
            runtimeService.deleteProcessInstance(id, reason);
        }
        return AjaxResult.success();
    }

    /**
     * 删除已经结束的实例
     * 
     * @param ids
     * @return
     * @author zmr
     */
    @RequestMapping("remove/his")
    public AjaxResult removeHis(String ids)
    {
        String[] idArr = ids.split(",");
        for (String id : idArr)
        {
            historyService.deleteHistoricProcessInstance(id);
        }
        return AjaxResult.success();
    }
}
