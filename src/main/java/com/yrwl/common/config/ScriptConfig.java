package com.yrwl.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author  shentao
 * @date    2020-02-13
 */
@Slf4j
@Configuration
public class ScriptConfig {

    @Bean
    public RedisScript<Boolean> lockScript() {
        RedisScript<Boolean> redisScript = null;
        try {
            ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("/scripts/lock.lua"));
            redisScript = RedisScript.of(scriptSource.getScriptAsString(), Boolean.class);
        } catch (Exception e) {
            log.error("error:{}" , e);
        }

        return redisScript;
    }

    @Bean
    public RedisScript<Long> unlockScript() {
        RedisScript<Long> redisScript = null;
        try {
            ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("/scripts/unlock.lua"));
            redisScript = RedisScript.of(scriptSource.getScriptAsString(), Long.class);
        } catch (Exception e) {
            log.error("error:{}" , e);
        }

        return redisScript;
    }
}
