package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author shentao
 * @date 2020-02-15
 */
@Getter
@Setter
@ApiModel(value = "CouponsSeqTypeEntity", description = "优惠券批次叠加")
public class CouponsSeqTypeEntity extends BaseEntity{

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券批次类型")
    private String couponsType;

    @ApiModelProperty(value = "创建人")
    private String createdUser;

    @ApiModelProperty(value = "创建时间")
    private String createdDate;

    @ApiModelProperty(value = "修改人")
    private String modifiedUser;

    @ApiModelProperty(value = "修改时间")
    private String modifiedDate;
}
