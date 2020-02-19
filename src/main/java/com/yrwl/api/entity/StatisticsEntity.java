package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Getter
@Setter
@ApiModel(value = "StatisticsEntity", description = "统计")
public class StatisticsEntity extends BaseEntity {

    @ApiModelProperty(value = "优惠券批次数据统计")
    private List<CouponsQuantityEntity> list;

    @ApiModelProperty(value = "批次总数")
    private String allCouponsSeqNum;

    @ApiModelProperty(value = "发放总数")
    private String allSendNum;

    @ApiModelProperty(value = "领取总数")
    private String allReceiveNum;

    @ApiModelProperty(value = "使用总数")
    private String allConsumeNum;
}
