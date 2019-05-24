package com.yongqiang.wms.controller;

import com.yongqiang.wms.model.BaseDictionary;
import com.yongqiang.wms.model.base.ReturnJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yantao.chen on 2019-05-21
 */
@RestController
@RequestMapping("api/dict")
public class DicController {
    @RequestMapping(value = "/getall", method = {RequestMethod.POST})
    public ReturnJson getAll() {
        HashMap<String, List<BaseDictionary>> dic = new HashMap<>();
        return new ReturnJson(dic);
    }

    @RequestMapping(value = "/getBaseData", method = {RequestMethod.POST})
    public ReturnJson getBaseData() {
        HashMap<String, Object> baseData = new HashMap<>();
        return new ReturnJson(baseData);
    }
}
