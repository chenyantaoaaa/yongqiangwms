package com.yongqiang.wms.controller;

import com.yongqiang.wms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yantao.chen on 2019-05-20
 */
@RestController
@RequestMapping("wmsin")
public class WmsInController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("testProject")
    @ResponseBody
    public List<User> testProject(){
        return userMapper.selectList(null);
    }
}
