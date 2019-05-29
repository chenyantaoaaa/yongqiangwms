package com.yongqiang.wms.model.stock;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 入库Dto
 *
 * @author yang.shang
 * @create 2019-05-29 10:27
 **/
public class WmsInInfoDto extends WmsInInfo {
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 明细级数据
     */
    private List<WmsInDetail> details;

    public List<WmsInDetail> getDetails() {
        return details;
    }

    public void setDetails(List<WmsInDetail> details) {
        this.details = details;
    }
}
