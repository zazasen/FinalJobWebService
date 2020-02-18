package com.cyrus.final_job.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            // 这里假设该资源登录之后即可访问，不需要权限，当然需要根据实际情况判断
            if ("ROLE_login".equals(configAttribute.getAttribute())) {
                // 匿名用户，没登陆
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录，请登录");
                } else {
                    return;
                }
            }

            // 当前登录用户所具备的角色
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                // 如果当前登录用户拥有该角色直接结束该循环
                if (authority.getAuthority().equals(configAttribute.getAttribute())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
