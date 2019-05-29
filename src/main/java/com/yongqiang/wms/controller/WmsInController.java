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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
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
     *
     * @param mainId 通过主单ID查询detailList
     * @return
     */
    @RequestMapping("/detailList/mainId/{id}")
    @ResponseBody
    public ReturnJson getDetailInfoByMainId(@PathVariable("id") Long mainId){
        return new ReturnJson(wmsInService.selectInfoByMainId(mainId));
    }
}
