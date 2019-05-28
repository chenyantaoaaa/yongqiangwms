package com.yongqiang.wms.model.stock;

import lombok.Data;

import java.util.Date;

@Data
public class Technology {
    private Integer id;

    private String name;

    private Integer upperWindTem;

    private Integer lowerWindTem;

    private Integer boxTem;

    private Integer dissolvedTem;

    private Integer hotrollingPressure;

    private Integer hotrollingUpperTem;

    private Integer hotrollingLowerTem;

    private Integer s1UpperSpeed;

    private Integer s1LowerSpeed;

    private Integer s1ConvulsionSpeed;

    private Integer s1SingleSpeed;

    private Integer s1MeterPump;

    private Integer s1MachineSpeed;

    private Integer s2UpperSpeed;

    private Integer s2LowerSpeed;

    private Integer s2ConvulsionSpeed;

    private Integer s2SingleSpeed;

    private Integer s2MeterPunp;

    private Integer s2MachineSpeed;

    private String remark;

    private Integer valid;

    private Byte creator;

    private Date createTime;

    private Byte modifier;

    private Date modifyTime;
}