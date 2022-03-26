package com.wenx.authuser.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wenx.authuser.core.handler.MybatisPlusMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenx
 */
@Configuration
public class MybatisConfig {

    /**
     * 自定义公共字段自动注入
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}
