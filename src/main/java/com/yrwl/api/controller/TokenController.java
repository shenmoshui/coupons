package com.yrwl.api.controller;

import com.yrwl.annotation.NoAuth;
import com.yrwl.api.service.ThirdPlatformService;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.constants.CommonConst;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.jwt.JwtHelper;
import com.yrwl.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Api(tags = "1、Token认证")
@RestController
@RequestMapping(CommonConst.VERSION + "accessToken")
@Slf4j
@NoAuth
public class TokenController {

    @Autowired
    private ThirdPlatformService thirdPlatformService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping()
    @ApiOperation(value = "获取Token", notes = "获取Token接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "第三方平台唯一标识", required = true),
            @ApiImplicitParam(name = "appSecret", value = "第三方平台秘钥", required = true)
    })
    public ResponseObject<String> getToken(@RequestParam String appId, @RequestParam String appSecret){
        //请求参数不能为空
        if(StringUtils.isAnyBlank(appId, appSecret)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_1);
        }
        //校验第三方是否有效
        if(!thirdPlatformService.checkSecret(appId, appSecret)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10006);
        }
        //生成token，并将token信息保存到redis中，后续进行双向校验
        String token = JwtHelper.createToken(appId, appSecret);
        redisUtil.set(CommonConst.ACCESS_TOKEN + ":" + appId, token, CommonConst.EXPIRY_DATE);

        return ResponseObject.success(token);
    }

}
