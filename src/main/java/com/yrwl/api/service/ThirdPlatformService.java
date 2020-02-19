package com.yrwl.api.service;

import com.yrwl.api.dto.ThirdPlatformDTO;
import com.yrwl.api.entity.ThirdPlatformEntity;
import com.yrwl.common.model.PageQueryModel;

import java.util.List;
import java.util.Map;

/**
 * @author shentao
 * @date 2020-02-11
 */
public interface ThirdPlatformService {

    /**
     * 第三方平台列表
     * @param pageQueryModel
     * @return
     */
    List<ThirdPlatformEntity> listThirdPlatform(PageQueryModel pageQueryModel);

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
    void addThirdPlatform(ThirdPlatformDTO thirdPlatformDTO);

    /**
     * 更新第三方平台信息
     * @param thirdPlatformDTO
     * @return
     */
    void updateThirdPlatform(ThirdPlatformDTO thirdPlatformDTO);

    /**
     * 查看第三方是否有效
     * @param appId
     * @param appSecret
     * @return
     */
    boolean checkSecret(String appId, String appSecret);
}
