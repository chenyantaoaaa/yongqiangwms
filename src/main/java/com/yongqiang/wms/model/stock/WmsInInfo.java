package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.util.Date;

@Data
public class WmsInInfo {
    private Long id;

    private String clientName;

    private String brand;

    private Integer gramWeight;

    private Integer clothWidth;

    private Integer num;

    private String classes;

    private String remark;

    private Long creator;

    private Date createTime;

    private Long modifier;

    private Date modifyTime;
}