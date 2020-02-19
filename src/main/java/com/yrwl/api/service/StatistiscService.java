package com.yrwl.api.service;

import com.yrwl.api.entity.StatisticsEntity;

/**
 * @author shentao
 * @date 2020-02-16
 */
public interface StatistiscService {

    /**
     * 统计
     * @param appId
     * @return
     */
    StatisticsEntity listStatistics(String appId);
}
