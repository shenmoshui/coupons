package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shentao
 * @date 2020-02-12
 */
@Data
@ApiModel(value = "CouponsDTO", description = "优惠券参数")
public class CouponsDTO {

    @ApiModelProperty(value = "第三方平台唯一标识")
    private String appId;

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券no")
    private String couponsNo;

    @ApiModelProperty(value = "优惠券状态，0:待使用,1:已使用,2:已过期,3:废弃")
    private Integer status;

    @ApiModelProperty(value = "可使用开始日期")
    private String expiryBeginDate;

    @ApiModelProperty(value = "可使用截止日期")
    private String expiryEndDate;

    @ApiModelProperty(value = "领取人")
    private String createdUser;

    @ApiModelProperty(value = "使用人")
    private String modifiedUser;
}
