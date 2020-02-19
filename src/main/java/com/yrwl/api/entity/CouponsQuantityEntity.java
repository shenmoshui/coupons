package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Getter
@Setter
@ApiModel(value = "CouponsQuantityEntity", description = "优惠券数量统计")
public class CouponsQuantityEntity extends BaseEntity{

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券名称")
    private String couponsName;

    @ApiModelProperty("优惠券发放数量")
    private String sendNum;

    @ApiModelProperty("优惠券领取数量")
    private String receiveNum;

    @ApiModelProperty("优惠券使用数量")
    private String consumeNum;
}
