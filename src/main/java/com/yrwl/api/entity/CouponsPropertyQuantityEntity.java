package com.yrwl.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Getter
@Setter
@ApiModel(value = "CouponsPropertyQuantityEntity", description = "优惠券批次数量统计")
public class CouponsPropertyQuantityEntity extends BaseEntity {

    @ApiModelProperty(value = "优惠券批次")
    private String couponsSeq;

    @ApiModelProperty(value = "优惠券名称")
    private String couponsName;

    @ApiModelProperty(value = "优惠券数量统计")
    private List<CouponsQuantityEntity> list;
}
