package com.yrwl.api.dto;

import com.yrwl.constants.CommonConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author shentao
 * @date 2020-02-12
 */
@Data
@ApiModel(value = "ReceiveCouponsDTO", description = "领取优惠券参数")
public class ReceiveCouponsDTO {

    @NotBlank(message = "优惠券批次不能为空")
    @ApiModelProperty(value = "优惠券批次", required = true)
    private String couponsSeq;

    @NotBlank(message = "可使用开始日期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATETIME, message = "可使用开始日期格式应为:YYYY-MM-DD HH:mm:ss")
    @ApiModelProperty(value = "可使用开始日期", required = true)
    private String expiryBeginDate;

    @NotBlank(message = "可使用截止日期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATETIME, message = "可使用截止日期格式应为:YYYY-MM-DD HH:mm:ss")
    @ApiModelProperty(value = "可使用截止日期", required = true)
    private String expiryEndDate;

    @ApiModelProperty(value = "领取人")
    private String createdUser;
}
