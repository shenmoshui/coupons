package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shentao
 * @date 2020-02-12
 */
@Getter
@Setter
@ApiModel(value = "CouponsEntity", description = "优惠券")
public class CouponsEntity extends BaseEntity {

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "第三方平台唯一标识")
    private String appId;

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券no")
    private String couponsNo;

    @ApiModelProperty(value = "优惠券状态，0:待使用,1:已使用,2:未到使用期,3:已过期,4:废弃")
    private int status;

    @ApiModelProperty(value = "可使用开始日期")
    private String expiryBeginDate;

    @ApiModelProperty(value = "可使用截止日期")
    private String expiryEndDate;
}
