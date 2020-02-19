package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author shentao
 * @date 2020-02-14
 */
@Data
@ApiModel(value = "ConsumeCouponsDTO", description = "消费优惠券参数")
public class ConsumeCouponsDTO {

    @NotBlank(message = "优惠券批次不能为空")
    @ApiModelProperty(value = "优惠券批次", required = true)
    private String couponsSeq;

    @NotBlank(message = "优惠券no不能为空")
    @ApiModelProperty(value = "优惠券no", required = true)
    private String couponsNo;

    @ApiModelProperty(value = "使用人")
    private String modifiedUser;
}
