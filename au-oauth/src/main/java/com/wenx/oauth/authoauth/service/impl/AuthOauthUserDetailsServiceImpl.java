package com.wenx.oauth.authoauth.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wenx.domain.modular.domain.SysUser;
import com.wenx.domain.modular.mapper.SysUserMapper;
import com.wenx.domain.modular.service.SysUserService;
import com.wenx.oauth.authoauth.detail.AuthUserDetails;
import com.wenx.oauth.authoauth.service.AuUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author wenx
 */
@Slf4j
@Service
public class AuthOauthUserDetailsServiceImpl implements AuUserDetailsService {


    @Resource
    private SysUserMapper userMapper;

    @Override
    public AuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = new LambdaQueryChainWrapper<>(userMapper).eq(SysUser::getAccount,username).one();

        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        log.debug(" |- UserDetailsService loaded user : [{}]", username);
        AuthUserDetails userDetails = new AuthUserDetails();
        userDetails.setUserId(sysUser.getId());
        userDetails.setUsername(sysUser.getName());
        userDetails.setPassword(sysUser.getPassword());
        userDetails.setAccountNonLocked(sysUser.getStatus() == 1);
        //TODO 拓展账户及令牌失效
        userDetails.setAccountNonExpired(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);

        Collection<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new SimpleGrantedAuthority("Test"));

        userDetails.setAuthorities(authorities);

        return userDetails;
    }
}
