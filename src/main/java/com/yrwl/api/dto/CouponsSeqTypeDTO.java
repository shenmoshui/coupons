package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author shentao
 * @date 2020-02-15
 */
@Data
@ApiModel(value = "CouponsSeqTypeDTO", description = "优惠券批次叠加参数")
public class CouponsSeqTypeDTO {

    @NotBlank(message = "优惠券批次不能为空")
    @ApiModelProperty(value = "优惠券批次", required = true)
    private String couponsSeq;

    @NotBlank(message = "优惠券批次类型不能为空")
    @ApiModelProperty(value = "优惠券批次类型", required = true)
    private String couponsType;
}
