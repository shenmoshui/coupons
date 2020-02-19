package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author  shentao
 * @date    2020-02-12
 */
@Data
@ApiModel(value = "CouponsPropertyDTO", description = "优惠券属性参数")
public class CouponsPropertyDTO {

    @ApiModelProperty(value = "第三方平台唯一标识")
    private String appId;

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券描述")
    private String mark;

    @ApiModelProperty(value = "优惠券初始化数量")
    private Integer num;

    @ApiModelProperty(value = "优惠券可叠加数量")
    private Integer superimpositionQuantity;

    @ApiModelProperty(value = "优惠券领取状态：1:可领取,2:停止领取,3:已领完")
    private Integer state;

    @ApiModelProperty(value = "优惠券状态，0:待发布,1:已发布,2:停止使用,3:停止领取,4:已领完,5:已过期,6:废弃")
    private Integer status;

    @ApiModelProperty(value = "优惠券有效期")
    private String expiryDate;

    @ApiModelProperty(value = "领取开始日期")
    private String receiveBeginDate;

    @ApiModelProperty(value = "领取截止日期")
    private String receiveEndDate;

    @ApiModelProperty(value = "扩展字段")
    private String extendInfo;

    @ApiModelProperty(value = "创建人")
    private String createdUser;

    @ApiModelProperty(value = "修改人")
    private String modifiedUser;
}
