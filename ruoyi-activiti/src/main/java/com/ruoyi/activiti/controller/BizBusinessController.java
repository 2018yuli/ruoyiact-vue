package com.ruoyi.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.activiti.domain.BizBusiness;
import com.ruoyi.activiti.service.IBizBusinessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 流程业务 提供者
 * 
 * @author ruoyi
 * @date 2020-01-06
 */
@RestController
@RequestMapping("act/business")
public class BizBusinessController extends BaseController
{
    @Autowired
    private IBizBusinessService bizBusinessService;

    /**
     * 查询流程业务
     */
    @GetMapping("get/{id}")
    public BizBusiness get(@PathVariable("id") String id)
    {
        return bizBusinessService.selectBizBusinessById(id);
    }

    /**
     * 查询流程业务列表
     */
    @GetMapping("list/my")
    public AjaxResult list(BizBusiness bizBusiness)
    {
        startPage();
        bizBusiness.setUserId(SecurityUtils.getUserId());
        bizBusiness.setDelFlag(false);
        return result(bizBusinessService.selectBizBusinessList(bizBusiness));
    }

    /**
     * 新增保存流程业务
     */
    @PostMapping("save")
    public AjaxResult addSave(@RequestBody BizBusiness bizBusiness)
    {
        bizBusiness.setUserId(SecurityUtils.getUserId());
        return toAjax(bizBusinessService.insertBizBusiness(bizBusiness));
    }

    /**
     * 修改保存流程业务
     */
    @PostMapping("update")
    public AjaxResult editSave(@RequestBody BizBusiness bizBusiness)
    {
        return toAjax(bizBusinessService.updateBizBusiness(bizBusiness));
    }

    /**
     * 删除流程业务
     */
    @PostMapping("remove")
    public AjaxResult remove(String ids)
    {
        return toAjax(bizBusinessService.deleteBizBusinessLogic(ids));
    }
}
