/*
 * @(#)ActTaskController.java 2020年1月7日 下午6:15:46
 * Copyright 2020 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ruoyi.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.ruoyi.activiti.consts.ActivitiConstant;
import com.ruoyi.activiti.domain.ActRuTask;
import com.ruoyi.activiti.domain.BizAudit;
import com.ruoyi.activiti.domain.BizBusiness;
import com.ruoyi.activiti.listener.MyExecutionListener;
import com.ruoyi.activiti.service.IBizActivitiNativeSearchService;
import com.ruoyi.activiti.service.IBizAuditService;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.activiti.vo.HiTaskVo;
import com.ruoyi.activiti.vo.RuTask;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * <p>File：ActTaskController.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月7日 下午6:15:46</p>
 * <p>Company: zmrit.com </p>
 * @author zmr
 * @version 1.0
 */
@RestController
@RequestMapping("act/task")
public class ActTaskController extends BaseController
{
    @Autowired
    private TaskService         taskService;

    @Autowired
    private IBizAuditService    bizAuditService;

    @Autowired
    private RuntimeService      runtimeService;

    @Autowired
    private ISysUserService   remoteUserService;

    @Autowired
    private IBizBusinessService businessService;
    
    @Autowired
    private IBizActivitiNativeSearchService nativeSearch;
    
    /**
     * task待办
     * 
     * @return
     * @author zmr
     */
//    @RequestMapping(value = "ing")
//    public AjaxResult ing(RuTask ruTask, PageDomain page)
//    {
//        List<RuTask> list = new ArrayList<>();
//        Long userId = SecurityUtils.getUserId();
//        
//        TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(userId + "").orderByTaskCreateTime()
//                .desc();
//        if (StringUtils.isNotBlank(ruTask.getProcessDefKey()))
//        {
//            query.processDefinitionKey(ruTask.getProcessDefKey());
//        }
//        // 根据流程节点查询
//        if (StringUtils.isNotEmpty(ruTask.getTaskDefKey())) {
//        	String[] tskDefKeyArr = StringUtils.split(ruTask.getTaskDefKey(), ",");
//        	if (tskDefKeyArr.length == 1) {
//        		if (StringUtils.equals(ruTask.getTaskDefKey(), BizTskConstants.CONFIRM_Str))
//        			query.taskDefinitionKeyLike(BizTskConstants.CONFIRM_Str_LIKE);
//        		else
//        			query.taskDefinitionKey(ruTask.getTaskDefKey());
//        	}else {
//        		query.or();
//        		Arrays.stream(tskDefKeyArr).forEach(key -> {
//        			if (StringUtils.equals(key, BizTskConstants.CONFIRM_Str))
//            			query.taskDefinitionKeyLike(BizTskConstants.CONFIRM_Str_LIKE);
//            		else
//            			query.taskDefinitionKey(key);
//        		});
//        		query.endOr();
//        	}
//        }
//        long count = query.count();
//        int first = (page.getPageNum() - 1) * page.getPageSize();
//        List<Task> taskList = query.listPage(first, page.getPageSize());
//        if (taskList.size() > 0)
//        {
//            // 转换vo
//            taskList.forEach(e -> {
//                RuTask rt = new RuTask(e);
////                List<IdentityLink> identityLinks = runtimeService
////                        .getIdentityLinksForProcessInstance(rt.getProcInstId());
////                for (IdentityLink ik : identityLinks)
////                {
////                    // 关联发起人
//////                    if ("starter".equals(ik.getType()) && StringUtils.isNotBlank(ik.getUserId()))
////                	if ("starter".equals(ik.getType()) && StringUtils.isNotBlank(ik.getUserId()))
////                    {
////                        rt.setApplyer(
////                                remoteUserService.selectUserById(Long.parseLong(ik.getUserId())).getUserName());
////                    }
////                }
//                
//                // 关联业务key
//                ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(rt.getProcInstId())
//                        .singleResult();
//                if (pi != null && StringUtils.isNotEmpty(pi.getBusinessKey())) {
//                	BizBusiness business = businessService.selectBizBusinessById(pi.getBusinessKey());
//                	rt.setBusinessKey(business.getId() + "");
//                	rt.setProcessName(business.getTitle());
//                	rt.setProcessDefKey(pi.getProcessDefinitionKey());
//                	rt.setProcessDefName(pi.getProcessDefinitionName());
//                	rt.setTableName("");
//                	rt.setApplyer(business.getApplyer());
//                	rt.setUserId(business.getUserId() + "");
//                	
//                	rt.setTableId(business.getTableId());
//                }
//                list.add(rt);
//            });
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("rows", list);
//        map.put("pageNum", page.getPageNum());
//        map.put("total", count);
//        return AjaxResult.success(map);
//    }
    
    @RequestMapping(value = "ing2")
    public AjaxResult ing2(RuTask ruTask, PageDomain page)
    {
    	Long userId = SecurityUtils.getUserId();
    	PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
    	ActRuTask ruTsk = new ActRuTask();
    	// 任务负责人查询条件
    	ruTsk.setAssignee(userId + "");
    	// 所属流程查询条件
    	if (StringUtils.isNotEmpty(ruTask.getProcessDefKey()))
    		ruTsk.setProcDefId(ruTask.getProcessDefKey() + "%");
    	// 流程节点查询条件
    	String tskDefKey = ruTask.getTaskDefKey();
    	if (StringUtils.isNotEmpty(tskDefKey)) {
    		List<String> tskDefKeyList = StringUtils.str2List(tskDefKey, ",", true, true);
    		if (tskDefKeyList.size() == 1)
    			ruTsk.setTaskDefKey(tskDefKeyList.get(0));
    		else
    			ruTsk.setTaskDefKeys(tskDefKeyList);
    	}
    	List<ActRuTask> tskList = nativeSearch.selectIng(ruTsk);
    	TableDataInfo tableData = getDataTable(tskList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", tableData.getRows());
        map.put("pageNum", page.getPageNum());
        map.put("total", tableData.getTotal());
        return AjaxResult.success(map);
    }
    
    /**
     * task 已办
     * @param hiTaskVo
     * @return
     */
    @RequestMapping(value = "done")
    public AjaxResult done(HiTaskVo hiTaskVo)
    {
        startPage();
        hiTaskVo.setAuditorId(SecurityUtils.getUserId());
        hiTaskVo.setDeleteReason(ActivitiConstant.REASON_COMPLETED);
        String tskDefKey = hiTaskVo.getTaskDefKey();
        if (!StringUtils.isEmpty(tskDefKey)) {
        	List<String> tskDefKeyList = StringUtils.str2List(tskDefKey, ",", true, true);
        	if (tskDefKeyList.size() > 1) {
        		hiTaskVo.setTaskDefKey(null);
        		hiTaskVo.setTaskDefKeys(tskDefKeyList);
        	}
        }
        return result(bizAuditService.getHistoryTaskList(hiTaskVo));
    }

    /**
     * task 流转历史
     * 
     * @param hiTaskVo
     * @return
     * @author zmr
     */
    @RequestMapping(value = "flow")
    public AjaxResult flow(HiTaskVo hiTaskVo)
    {
        startPage();
        return result(bizAuditService.getHistoryTaskList(hiTaskVo));
    }

    /**
     * 审批
     * 
     * @param bizAudit
     * @return
     * @author zmr
     */
    @PostMapping("audit")
    public AjaxResult audit(@RequestBody BizAudit bizAudit)
    {
    	SysUser user = remoteUserService.selectUserById(SecurityUtils.getUserId());
        businessService.audit(bizAudit, user.getUserId(), user.getNickName() + StringUtils.SEPERATE_MIDDLE_STRIKE + user.getUserName());
        return AjaxResult.success();
    }

    /**
     * 批量审批
     * @param bizAudit
     * @return
     */
    @PostMapping("audit/batch")
    public AjaxResult auditBatch(@RequestBody BizAudit bizAudit)
    {
        SysUser user = remoteUserService.selectUserById(SecurityUtils.getUserId());
        for (String taskId : bizAudit.getTaskIds())
        {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            BizBusiness bizBusiness = businessService.selectBizBusinessById(pi.getBusinessKey());
            if (null != bizBusiness)
            {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("result", bizAudit.getResult());
                // 审批
                taskService.complete(taskId, variables);
                // 构建插入审批记录
                BizAudit audit = new BizAudit().setTaskId(taskId).setResult(bizAudit.getResult())
                        .setProcName(bizBusiness.getProcName()).setProcDefKey(bizBusiness.getProcDefKey())
                        .setApplyer(bizBusiness.getApplyer()).setAuditor(user.getNickName() + StringUtils.SEPERATE_MIDDLE_STRIKE + user.getUserName())
                        .setAuditorId(user.getUserId());
                bizAuditService.insertBizAudit(audit);
                //businessService.setAuditor(bizBusiness.getProcInstId(), bizBusiness, audit.getResult(), SecurityUtils.getUserId());
            }
        }
        return AjaxResult.success();
    }

    /**
     * remove审批记录 逻辑删除
     * @param ids
     * @return
     */
    @PostMapping("remove")
    public AjaxResult remove(String ids)
    {
        return toAjax(bizAuditService.deleteBizAuditLogic(ids));
    }
}
