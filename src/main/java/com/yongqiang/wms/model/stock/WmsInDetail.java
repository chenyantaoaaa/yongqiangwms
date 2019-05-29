package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WmsInDetail {
    private Long id;

    private Long mainId;

    private Integer coilLength;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private String batchNo;

    private Integer packNo;

    private Date productTime;

    private Long creator;

    private Date createTime;

    private Long modifier;

    private Date modifyTime;


}