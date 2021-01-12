package com.ruoyi.project.kettle.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.kettle.service.KettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kettle/ktr")
public class KettleCtrl {
    @Autowired
    private KettleService kettleService;
    @Log(title = "kettle测试")
    @GetMapping("/ktr")
    public AjaxResult ktr() {
        kettleService.runKtr("test.ktr",null);
        return AjaxResult.success("success");
    }
}
