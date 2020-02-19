package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Getter
@Setter
@ApiModel(value = "CouponsPropertyEntity", description = "优惠券属性")
public class CouponsPropertyEntity extends BaseEntity {

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "第三方平台唯一标识")
    private String appId;

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券描述")
    private String mark;

    @ApiModelProperty(value = "优惠券初始化数量")
    private int num;

    @ApiModelProperty(value = "优惠券可叠加数量")
    private int superimpositionQuantity;

    @ApiModelProperty(value = "优惠券领取状态：1:可领取,2:停止领取,3:已领完")
    private int state;

    @ApiModelProperty(value = "优惠券状态，0:待发布,1:已发布,2:停止使用,4:已过期,5:废弃")
    private int status;

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

    @ApiModelProperty(value = "创建时间")
    private String createdDate;

    @ApiModelProperty(value = "修改人")
    private String modifiedUser;

    @ApiModelProperty(value = "修改时间")
    private String modifiedDate;
}
