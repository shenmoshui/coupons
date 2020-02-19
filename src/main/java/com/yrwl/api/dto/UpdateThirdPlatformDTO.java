package com.yrwl.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Data
@ApiModel(value = "ThirdPlatformDTO", description = "第三方平台更新参数")
public class UpdateThirdPlatformDTO {

    @ApiModelProperty(value = "第三方平台名称")
    private String platformName;

    @NotBlank(message = "第三方平台唯一标识不能为空")
    @ApiModelProperty(value = "第三方平台唯一标识", required = true)
    private String appId;

    @ApiModelProperty(value = "第三方平台联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "第三方平台联系人电话")
    private String mobile;

    @ApiModelProperty(value = "描述")
    private String mark;
}
