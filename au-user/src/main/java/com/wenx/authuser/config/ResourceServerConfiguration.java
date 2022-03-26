package com.wenx.authuser.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import javax.annotation.PostConstruct;


/**
 * @author wenx
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${security.oauth2.resource.token-info-uri}")
    private String checkTokenEndpointUrl;


    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Core [Herodotus Resource Server in component oauth] Auto Configure.");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
        resources.resourceId(resourceId).stateless(true);
        resources.tokenServices(tokenService());
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated();

        // 防止iframe 造成跨域
        http.headers().frameOptions().disable();
    }

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(secret);
        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        return tokenService;
    }

}
