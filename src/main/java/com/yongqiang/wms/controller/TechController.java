package com.yongqiang.wms.controller;

import com.yongqiang.wms.model.base.ReturnJson;
import com.yongqiang.wms.model.stock.TechDto;
import com.yongqiang.wms.model.stock.Technology;
import com.yongqiang.wms.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yantao.chen on 2019-05-20
 */
@RestController
@RequestMapping("api/tech")
public class TechController {
    @Autowired
    TechService techService;

    /**
     * 分页查询工艺信息
     * @param techDto
     * @return
     */
    @RequestMapping("getTechsByPage")
    @ResponseBody
    public ReturnJson getTechsByPage(@RequestBody TechDto techDto){
        return new ReturnJson(techService.getTechsByPage(techDto));
    }

    /**
     * 根据主键id查询工艺信息
     */
    @RequestMapping("getTechInfoById")
    @ResponseBody
    public ReturnJson getTechInfoById(@RequestBody Technology technology){
        return new ReturnJson(techService.getTechInfoById(technology.getId()));
    }

    /**
     * 新增工艺信息
     */
    @RequestMapping("addTechInfo")
    @ResponseBody
    public ReturnJson addTechInfo(@RequestBody Technology technology){
        return new ReturnJson(techService.addTechInfo(technology));
    }

    /**
     * 修改工艺信息
     */
    @RequestMapping("updateTechInfo")
    @ResponseBody
    public ReturnJson updateTechInfo(@RequestBody Technology technology){
        return new ReturnJson(techService.updateTechInfo(technology));
    }


    /**
     * 删除工艺信息
     */
    @RequestMapping("deleteTechInfo")
    @ResponseBody
    public ReturnJson deleteTechInfo(@RequestBody Technology technology){
        return new ReturnJson(techService.deleteTechInfo(technology));
    }
}
