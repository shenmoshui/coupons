package com.yrwl.api.dao;

import com.yrwl.api.dto.ThirdPlatformDTO;
import com.yrwl.api.entity.ThirdPlatformEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Mapper
public interface ThirdPlatformDao {

    /**
     * 第三方平台列表
     * @param map
     * @return
     */
    List<ThirdPlatformEntity> listThirdPlatform(Map<String, String> map);

    /**
     * 第三方平台信息
     * @param appId
     * @return
     */
    ThirdPlatformEntity getThirdPlatform(String appId);

    /**
     * 添加第三方平台信息
     * @param thirdPlatformDTO
     * @return
     */
    int addThirdPlatform(ThirdPlatformDTO thirdPlatformDTO);

    /**
     * 修改第三方平台信息
     * @param thirdPlatformDTO
     * @return
     */
    int updateThirdPlatform(ThirdPlatformDTO thirdPlatformDTO);

    /**
     * 查看第三方是否有效
     * @param appId
     * @param appSecret
     * @return
     */
    @Select("select count(1) from third_platform where app_id = #{appId} and app_secret = #{appSecret} and status = 1 and expiry_date > now()")
    int checkSecret(@Param("appId") String appId, @Param("appSecret") String appSecret);
}
