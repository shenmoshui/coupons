package com.yrwl.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.yrwl.constants.CommonConst;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.jwt.JwtHelper;
import com.yrwl.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author  shentao
 * @date    2020-02-11
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取用户凭证
        String token = request.getHeader(CommonConst.ACCESS_TOKEN);
        //第三方平台请求appId
        String requestAppId = request.getHeader("appId");
        //token或者appId为空，抛出错误
        if(StringUtils.isBlank(token)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10001);
        }
        else if(StringUtils.isBlank(requestAppId)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10004);
        }
        //校验token有效性
        Map<String, Claim> jwt = JwtHelper.verifyToken(token);
        //获取token中appId的值
        String appId = jwt.get("appId").asString();
        //appId为空或者与请求appId不一致，抛出错误
        if(StringUtils.isEmpty(appId)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10003);
        }
        else if(!Objects.equals(appId, requestAppId)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10005);
        }
        //获取第三方平台请求获得token时，优惠券系统保存的token值
        String tokenTemp = redisUtil.get(CommonConst.ACCESS_TOKEN + ":" + appId) + "";
        //值为空或者与当前请求token不一致，则token已失效
        if(StringUtils.isEmpty(tokenTemp) || !token.equals(tokenTemp)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10002);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }
}
