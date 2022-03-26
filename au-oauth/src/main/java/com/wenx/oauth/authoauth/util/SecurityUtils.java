package com.wenx.oauth.authoauth.util;

import cn.hutool.core.bean.BeanUtil;
import com.wenx.oauth.authoauth.detail.AuthUserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wenx
 */
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

    public static final String PREFIX_ROLE = "ROLE_";
    public static final String PREFIX_SCOPE = "SCOPE_";

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return ObjectUtils.isNotEmpty(getAuthentication()) && getAuthentication().isAuthenticated();
    }

    public static Object getDetails() {
        return getAuthentication().getDetails();
    }

    /**
     * 当用户角色发生变化，或者用户角色对应的权限发生变化，那么就从数据库中重新查询用户相关信息
     *
     * @param newAuthUserDetails 从数据库中重新查询并生成的用户信息
     */
    public static void reloadAuthority(AuthUserDetails newAuthUserDetails) {
        // 重新new一个token，因为Authentication中的权限是不可变的.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                newAuthUserDetails, newAuthUserDetails.getPassword(),
                newAuthUserDetails.getAuthorities());
        token.setDetails(getDetails());
        getSecurityContext().setAuthentication(token);
    }

    /**
     * 获取认证用户信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static AuthUserDetails getPrincipal() {
        if (isAuthenticated()) {
            Authentication authentication = getAuthentication();
            if (authentication.getPrincipal() instanceof AuthUserDetails) {
                return (AuthUserDetails) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof Map) {
                Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
                return BeanUtils.mapToBean(principal, AuthUserDetails.class);
            }
        }

        return null;
    }

    public static String getUsername() {
        AuthUserDetails user = getPrincipal();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public static AuthUserDetails getPrincipals() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof AuthUserDetails) {
                return (AuthUserDetails) principal;
            } else if (principal instanceof LinkedHashMap) {
                AuthUserDetails user = new AuthUserDetails();
                BeanUtil.fillBeanWithMap((LinkedHashMap) principal, user, true);
                return user;
            } else {
                throw new IllegalStateException("获取用户数据失败");
            }
        }
        return null;
    }

    public static Long getUserId() {
        AuthUserDetails AuthUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(AuthUserDetails)) {
            return AuthUserDetails.getUserId();
        }

        return null;
    }


    public static String encrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean matches(String rawPassowrd, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassowrd, encodedPassword);
    }

    public static String[] whitelistToAntMatchers(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            String[] array = new String[list.size()];
            log.debug("[Auth] |- Fetch The REST White List.");
            return list.toArray(array);
        }

        log.warn("[Auth] |- Can not Fetch The REST White List Configurations.");
        return new String[]{};
    }

    public static String wellFormRolePrefix(String content) {
        return wellFormPrefix(content, PREFIX_ROLE);
    }

    public static String wellFormPrefix(String content, String prefix) {
        if (StringUtils.startsWith(content, prefix)) {
            return content;
        } else {
            return prefix + content;
        }
    }

}
