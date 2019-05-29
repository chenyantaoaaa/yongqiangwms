package com.yongqiang.wms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongqiang.wms.mapper.UserMapper;
import com.yongqiang.wms.mapper.WmsInInfoMapper;
import com.yongqiang.wms.model.base.ReturnJson;
import com.yongqiang.wms.model.stock.WmsInInfo;
import com.yongqiang.wms.model.stock.WmsInInfoDto;
import com.yongqiang.wms.model.user.User;
import com.yongqiang.wms.service.WmsInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 尝试使用RESTFUL 风格的接口
 *
 * Created by yantao.chen on 2019-05-20
 */
@RestController
@RequestMapping("api/wmsin")
public class WmsInController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WmsInService wmsInService;

    @RequestMapping("testProject")
    @ResponseBody
    public List<User> testProject(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/viewPageList")
    @ResponseBody
    public ReturnJson searchList(WmsInInfoDto query){
        return new ReturnJson(wmsInService.selectInfoByPage(query));
    }

    /**
     * 尝试使用RESTFUL 风格的接口
     * @param mainId 通过主单ID查询detailList
     * @return
     */
    @RequestMapping(value = "/detailList/mainId/{id}" , method = {RequestMethod.GET})
    @ResponseBody
    public ReturnJson getDetailInfoByMainId(@PathVariable("id") Long mainId){
        return new ReturnJson(wmsInService.selectDetailInfoByMainId(mainId));
    }

    /**
     * 创建入库单
     * @param createDto 创建的Dto
     * @return
     */
    @RequestMapping(value = "/createWmsInfo" , method = {RequestMethod.POST})
    @ResponseBody
    public ReturnJson addInfo(WmsInInfoDto createDto){
        return new ReturnJson(wmsInService.addInfo(createDto));
    }

    /**
     * 更新入库单
     * @param createDto 创建的Dto
     * @return
     */
    @RequestMapping(value = "/updateWmsInfo" , method = {RequestMethod.POST})
    @ResponseBody
    public ReturnJson updateInfo(WmsInInfoDto createDto){
        return new ReturnJson(wmsInService.updateInfo(createDto));
    }
}
