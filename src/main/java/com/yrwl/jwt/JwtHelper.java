package com.yrwl.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yrwl.constants.CommonConst;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.JodaTimeUtils;
import com.yrwl.utils.LogUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  shentao
 * @date  2019-05-02
 */
@Slf4j
@UtilityClass
public class JwtHelper {

    private static final String ISSUER = "Tao";
    private static final String SECRET = "LAUNCH_TAO";

    public String createToken(String appId, String appSecret){
        //header设置
        Map<String, Object> map = new HashMap<>(3);
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Date date = new Date();
        String token = JWT.create().
                withHeader(map).
                withClaim(PublicClaims.ISSUER, ISSUER).
                withClaim(PublicClaims.AUDIENCE,"Coupons").
                withClaim("appId", appId).
                withClaim("appSecret", appSecret).
                withIssuedAt(date).
                withExpiresAt(JodaTimeUtils.strToDate(JodaTimeUtils.adjustTime(date, Calendar.SECOND, CommonConst.EXPIRY_DATE))).
                sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    public Map<String, Claim> verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);

            return jwt.getClaims();
        } catch (Exception e) {
            log.error("token校验失败{}", LogUtils.getStackTraceInfo(e));
            // token 校验失败, 抛出Token验证非法异常
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_10003);
        }
    }

}
