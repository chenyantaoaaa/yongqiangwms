package com.yongqiang.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yongqiang.wms.mapper.QualityMapper;
import com.yongqiang.wms.model.stock.Quality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yantao.chen on 2019-05-20
 */
@Service
public class QualityService {
    @Autowired
    QualityMapper qualityMapper;

    public IPage<Quality> testPage(Page page){
        return qualityMapper.selectQualityByPage(page);
    }
}
