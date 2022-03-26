package com.wenx.oauth.authoauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wenx
 */

@ComponentScan("com.wenx")
@MapperScan("com.wenx.**.mapper")
@SpringBootApplication(scanBasePackages = "com.wenx")
public class AuthOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthOauthApplication.class, args);
    }

}
