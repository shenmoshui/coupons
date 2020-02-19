package com.yrwl.api.dto;

import com.yrwl.constants.CommonConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Data
@ApiModel(value = "AddThirdPlatformDTO", description = "添加第三方平台请求参数")
public class AddThirdPlatformDTO {

    @NotBlank(message = "第三方平台名称不能为空")
    @ApiModelProperty(value = "第三方平台名称", required = true)
    private String platformName;

    @NotBlank(message = "第三方平台联系人姓名不能为空")
    @ApiModelProperty(value = "第三方平台联系人姓名", required = true)
    private String contactName;

    @NotBlank(message = "第三方平台联系人电话不能为空")
    @ApiModelProperty(value = "第三方平台联系人电话", required = true)
    private String mobile;

    @ApiModelProperty(value = "描述")
    private String mark;

    @NotBlank(message = "合作有效期不能为空")
    @Pattern(regexp = CommonConst.REGEXP_DATE, message = "格式应为:YYYY-MM-DD")
    @ApiModelProperty(value = "合作有效期, 格式:YYYY-MM-DD", required = true)
    private String expiryDate;

}
