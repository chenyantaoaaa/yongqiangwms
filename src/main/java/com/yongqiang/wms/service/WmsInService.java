package com.yongqiang.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yongqiang.wms.mapper.WmsInDetailMapper;
import com.yongqiang.wms.mapper.WmsInInfoMapper;
import com.yongqiang.wms.model.stock.WmsInDetail;
import com.yongqiang.wms.model.stock.WmsInInfo;
import com.yongqiang.wms.model.stock.WmsInInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yantao.chen on 2019-05-20
 */
@Service
public class WmsInService {

    @Autowired
    private WmsInInfoMapper wmsInInfoMapper;
    @Autowired
    private WmsInDetailMapper wmsInDetailMapper;

    public IPage<WmsInInfo> selectInfoByPage(WmsInInfoDto query) {

        return wmsInInfoMapper.selectInfoPage(query.getPage(),query);
    }

    public List<WmsInDetail> selectDetailInfoByMainId(Long mainId){
        QueryWrapper<WmsInDetail> wrapper = new QueryWrapper<>();
        WmsInDetail queryDto = new WmsInDetail();
        queryDto.setMainId(mainId);
        wrapper.setEntity(queryDto);
        return wmsInDetailMapper.selectList(wrapper);
    }

    public WmsInInfo addInfo(WmsInInfoDto createDto) {
        List<WmsInDetail> details =  createDto.getDetails();
        details.forEach( item ->{
            item.setCreateTime(new Date());
            item.setModifyTime(new Date());
            wmsInDetailMapper.insert(item);
        });
        return null;
    }
}
