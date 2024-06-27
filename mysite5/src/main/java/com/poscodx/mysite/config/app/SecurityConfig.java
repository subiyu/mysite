package com.poscodx.mysite.config.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.poscodx.mysite.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
            		.ignoring()
            		.requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                	.requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.logout()
    		.logoutUrl("/user/logout")
    		.logoutSuccessUrl("/")
    		.and()
    		
    		.formLogin()
    		.loginPage("/user/login")
    		.loginProcessingUrl("/user/auth")
    		.usernameParameter("email") 				// 로그인 폼에서 아이디 필드의 파라미터 이름을 email로 설정
    		.passwordParameter("password")				// 로그인 폼에서 비밀번호 필드의 파라미터 이름을 "password"로 설정
    		.defaultSuccessUrl("/")						// 성공했을 때 메인으로
    		//.failureUrl("/user/login?result=fail")		// 실패했을 때
    		.failureHandler(new AuthenticationFailureHandler() {
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					request.setAttribute("email", request.getParameter("email"));
					request
						.getRequestDispatcher("/user/login")
						.forward(request, response);
				}
    		})
    		.and()
    		
    		.csrf()
    		.disable()
    		
    		.authorizeHttpRequests(registry -> {
    			/* ACL */
    			registry
	    			.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null)).hasRole("ADMIN")
    				.requestMatchers(new RegexRequestMatcher("^/user/update$", null)).hasAnyRole("ADMIN", "USER")
    				.requestMatchers(new RegexRequestMatcher("^/board/?(write|reply|delete|modify).*$", null)).hasAnyRole("ADMIN", "USER")
    				
		    		.anyRequest()
		    		.permitAll();
    		});
//    		.exceptionHandling(exceptionHanlingConfigurer -> {
//    			exceptionHanlingConfigurer.accessDeniedPage("/WEB-INF/views/error/403.jsp")
//    		});
    		
    	return http.build();
    }
    
    // Authentication Manager
    @Bean
    public AuthenticationManager autenAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setPasswordEncoder(passwordEncoder);
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	return new ProviderManager(authenticationProvider);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4);
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
    	return new UserDetailsServiceImpl();
    }
}