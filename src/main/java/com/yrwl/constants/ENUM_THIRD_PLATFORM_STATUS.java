package com.yrwl.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  shentao
 * @date    2020-02-11
 */
@Getter
@AllArgsConstructor
public enum ENUM_THIRD_PLATFORM_STATUS {

    START(1, "合作中"),

    END(2, "合同到期"),

    STOP(3, "暂停合作");

    private int code;

    private String name;
}
