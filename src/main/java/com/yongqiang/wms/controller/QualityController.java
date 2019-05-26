package com.yongqiang.wms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongqiang.wms.model.base.ReturnJson;
import com.yongqiang.wms.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yantao.chen on 2019-05-20
 */
@RestController
@RequestMapping("api/quality")
public class QualityController {
    @Autowired
    private QualityService qualityService;

    @RequestMapping("testpage")
    @ResponseBody
    public ReturnJson testPage(@RequestBody Page page){
        return new ReturnJson(qualityService.testPage(page));
    }
}
