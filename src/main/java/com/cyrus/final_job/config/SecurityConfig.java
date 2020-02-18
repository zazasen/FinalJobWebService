package com.cyrus.final_job.config;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.vo.UserVo;
import com.cyrus.final_job.service.system.UserService;
import com.cyrus.final_job.utils.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyFilter myFilter;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(objectPostProcessor)
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(3600)
                .userDetailsService(userService)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and().csrf().disable()
                // AuthenticationEntryPoint 用來解決匿名用戶访问无权限资源异常
                // 在这里是为了处理没有登录的时候访问资源时不要重定向，解决跨域问题
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        // 启动时自动创建表   如果数据库有该表，再设置为true，启动会报错
//        tokenRepositoryImpl.setCreateTableOnStartup(true);
        return tokenRepositoryImpl;
    }

    ObjectPostProcessor objectPostProcessor = new ObjectPostProcessor<FilterSecurityInterceptor>() {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
            o.setAccessDecisionManager(myAccessDecisionManager);
            o.setSecurityMetadataSource(myFilter);
            return o;
        }
    };

    // 登录成功返回消息
    AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            User user = (User) authentication.getPrincipal();
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            Result loginSuccess = Results.createOk("登录成功", userVo);
            String s = new ObjectMapper().writeValueAsString(loginSuccess);
            out.write(s);
            out.flush();
            out.close();
        }
    };

    // 登录失败返回消息
    AuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler() {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            Result error = Results.error("登录失败");
            if (e instanceof LockedException) {
                error.setMsg("账户被锁定，请联系管理员");
            } else if (e instanceof BadCredentialsException) {
                error.setMsg("用户名或密码输入错误，请重新输入");
            } else if (e instanceof DisabledException) {
                error.setMsg("账户被禁用，请联系管理员");
            } else if (e instanceof AccountExpiredException) {
                error.setMsg("账户过期，登录失败");
            } else if (e instanceof CredentialsExpiredException) {
                error.setMsg("密码过期，登录失败");
            }
            String s = new ObjectMapper().writeValueAsString(error);
            out.write(s);
            out.flush();
            out.close();
        }
    };


    // 注销成功返回消息
    LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler() {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            String s = new ObjectMapper().writeValueAsString(Results.createOk("注销成功!"));
            out.write(s);
            out.flush();
            out.close();
        }
    };

    //没登录处理
    AuthenticationEntryPoint authenticationEntryPoint = new AuthenticationEntryPoint() {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            PrintWriter out = response.getWriter();
            Result error = Results.error("访问失败！");
            if (authException instanceof InsufficientAuthenticationException) {
                error.setMsg("尚未登录，请登录后再试！");
            }
            String s = new ObjectMapper().writeValueAsString(error);
            out.write(s);
            out.flush();
            out.close();
        }
    };
}
