package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WmsInDetail {
    private Byte id;

    private Byte mainId;

    private Integer coilLength;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private String batchNo;

    private Integer packNo;

    private Date productTime;

    private Byte creator;

    private Date createTime;

    private Byte modifier;

    private Date modifyTime;
}