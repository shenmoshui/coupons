package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author  shentao
 * @date    2020-02-12
 */
@Data
@ApiModel(value = "IssueCouponsPropertyDTO", description = "发布优惠券属性参数")
public class IssueCouponsPropertyDTO {

    @NotBlank(message = "第三方平台唯一标识不能为空")
    @ApiModelProperty(value = "第三方平台唯一标识", required = true)
    private String appId;

    @NotBlank(message = "优惠券批次不能为空")
    @ApiModelProperty(value = "优惠券批次", required = true)
    private String couponsSeq;
}
