package com.wenx.authservice.service;

import com.wenx.authservice.dao.Sys_userMapper;
import com.wenx.authservice.model.Sys_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private Sys_userMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 这个地方可以通过username从数据库获取正确的用户信息，包括密码和权限等。
        Sys_user user = userMapper.queryUserByName(username);
        if (user == null) {
            throw new BadCredentialsException("user: " + username + " not found.");
        }
        return new User(user.getUsername(), user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN"));
    }
}
