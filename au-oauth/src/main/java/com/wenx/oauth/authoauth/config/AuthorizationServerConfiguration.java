package com.wenx.oauth.authoauth.config;

import com.wenx.oauth.authoauth.service.AuUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wenx
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    @Resource
    private DataSource dataSource;
    /**
     * 注入authenticationManager
     * 来支持 password grant type
     */
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;
    @Resource
    private AuUserDetailsService userDetailsService;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Authorization Server in component oauth] Auto Configure.");
    }


    /**
     * 配置客户端详情服务（ClientDetailsService）
     * 客户端详情信息在这里进行初始化
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * config
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                // 授权允许存储方式
                .approvalStore(createApprovalStore())
                // 授权码模式code存储方式
                .authorizationCodeServices(createAuthorizationCodeServices())
                // token存储方式
                .tokenStore(createTokenStore())
                .userDetailsService(userDetailsService)
                .accessTokenConverter(defaultAccessTokenConverter());

        // 自定义确认授权页面
        endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access");
        // 自定义错误页
        endpoints.pathMapping("/oauth/error", "/oauth/error");
    }

    /**
     * 声明 TokenStore 管理方式实现
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore createTokenStore() {
        return new RedisTokenStore(lettuceConnectionFactory);
    }

    /**
     * 授权store。主要用于Authorization Code模式中，确认授权范围页面。
     *
     * @return ApprovalStore
     */
    @Bean
    public ApprovalStore createApprovalStore() {
        TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
        tokenApprovalStore.setTokenStore(createTokenStore());
        return tokenApprovalStore;
    }

    /**
     * 授权码
     *
     * @return AuthorizationCodeServices
     */
    @Bean
    public AuthorizationCodeServices createAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    /**
     * ClientDetails
     * @return
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public DefaultAccessTokenConverter defaultAccessTokenConverter(){
        return new DefaultAccessTokenConverter();
    }
}
