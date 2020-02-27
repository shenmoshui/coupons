package com.yrwl.api.dto;

import com.yrwl.constants.CommonConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author  shentao
 * @date    2020-02-20
 */
@Data
@ApiModel(value = "UpdateCouponsPropertyDTO", description = "更新优惠券属性参数")
public class UpdateCouponsPropertyDTO {

    @NotBlank(message = "第三方平台唯一标识不能为空")
    @ApiModelProperty(value = "第三方平台唯一标识", required = true)
    private String appId;

    @NotBlank(message = "优惠券批次不能为空")
    @ApiModelProperty(value = "优惠券批次", required = true)
    private String couponsSeq;

    @NotBlank(message = "优惠券名称不能为空")
    @ApiModelProperty(value = "优惠券名称", required = true)
    private String name;

    @ApiModelProperty(value = "优惠券描述")
    private String mark;

    @Min(value = 1, message = "优惠券需设置数量")
    @ApiModelProperty(value = "优惠券初始化数量", required = true)
    private Integer num;

    @Min(value = 1, message = "优惠券可叠加数量最少1张")
    @ApiModelProperty(value = "优惠券可叠加数量(按张数计算), 1:不叠加,2:可使用两张，依次类推", required = true)
    private Integer superimpositionQuantity;

    @NotBlank(message = "优惠券有效期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATETIME, message = "优惠券有效期格式应为:YYYY-MM-DD HH:mm:ss")
    @ApiModelProperty(value = "优惠券有效期", required = true)
    private String expiryDate;

    @NotBlank(message = "领取开始日期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATETIME, message = "领取开始日期格式应为:YYYY-MM-DD HH:mm:ss")
    @ApiModelProperty(value = "领取开始日期", required = true)
    private String receiveBeginDate;

    @NotBlank(message = "领取截止日期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATETIME, message = "领取截止日期格式应为:YYYY-MM-DD HH:mm:ss")
    @ApiModelProperty(value = "领取截止日期", required = true)
    private String receiveEndDate;

    @ApiModelProperty(value = "扩展字段")
    private String extendInfo;
}
