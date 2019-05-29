package com.yongqiang.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yongqiang.wms.mapper.WmsInInfoMapper;
import com.yongqiang.wms.model.stock.WmsInInfo;
import com.yongqiang.wms.model.stock.WmsInInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yantao.chen on 2019-05-20
 */
@Service
public class WmsInService {

    @Autowired
    private WmsInInfoMapper wmsInInfoMapper;

    public IPage<WmsInInfo> selectInfoByPage(WmsInInfoDto query) {

        return wmsInInfoMapper.selectInfoPage(query.getPage(),query);
    }

    public List<WmsInInfo> selectInfoByMainId(Long mainId){
        QueryWrapper<Long> wrapper = new QueryWrapper<>();
        wrapper.setEntity(mainId);
        //return wmsInInfoMapper.selectList(wrapper);
        return null;
    }
}
