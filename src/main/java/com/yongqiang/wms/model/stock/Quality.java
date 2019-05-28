package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Quality {
    private Long id;

    private Long wmsInId;

    private Integer portraitPower;

    private Integer transversePower;

    private BigDecimal ratio;

    private Integer portraitDrafting;

    private Integer transverseDrafting;

    private BigDecimal singleSilkThin;

    private BigDecimal thickness;

    private BigDecimal even;

    private BigDecimal soft;

    private String remark;

    private Long creator;

    private Date createTime;

    private Long modifier;

    private Date modifyTime;
}