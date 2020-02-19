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
@ApiModel(value = "CouponsPropertyEntity", description = "第三方平台信息")
public class ThirdPlatformEntity extends BaseEntity{

    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "第三方平台名称")
    private String platformName;

    @ApiModelProperty(value = "第三方平台唯一标识")
    private String appId;

    @ApiModelProperty(value = "第三方平台秘钥")
    private String appSecret;

    @ApiModelProperty(value = "第三方平台联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "第三方平台联系人电话")
    private String mobile;

    @ApiModelProperty(value = "描述")
    private String mark;

    @ApiModelProperty(value = "合作有效期")
    private String expiryDate;

    @ApiModelProperty(value = "合作状态，1:合作中,2:合同到期,3:暂停使用")
    private int status;

    @ApiModelProperty(value = "创建人")
    private String createdUser;

    @ApiModelProperty(value = "创建时间")
    private String createdDate;

    @ApiModelProperty(value = "修改人")
    private String modifiedUser;

    @ApiModelProperty(value = "修改时间")
    private String modifiedDate;
}
