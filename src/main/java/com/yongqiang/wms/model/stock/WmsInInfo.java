package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmsInInfo {
    private Long id;

    private String whsInCode;

    private String clientName;

    private Long techId;

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