package com.cyrus.final_job.config;

import com.cyrus.final_job.entity.system.Menu;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.security.access.SecurityConfig;

import java.util.Collection;
import java.util.List;

@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> allMenu = menuService.getAllMenuWithRole();
        if (pathMatcher.match("/home/getPublishedStaffNeeds", requestUrl) ||
                pathMatcher.match("/home/getStaffNeedsDetail", requestUrl) ||
                pathMatcher.match("/home/inputResume", requestUrl)) {
            return SecurityConfig.createList("ROLE_pass");
        }
        for (Menu menu : allMenu) {
            if (pathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] rolesStr = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    rolesStr[i] = roles.get(i).getRoleName();
                }
                if (rolesStr.length == 0) return SecurityConfig.createList("ROLE_NOALLOW");
                return SecurityConfig.createList(rolesStr);
            }
        }
        // 没有找到对应的 url 资源，这里假设该资源登录之后即可访问，不需要权限，当然需要根据实际情况判断
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
