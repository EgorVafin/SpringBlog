package com.example.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //private final SecurityUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/api/post/**").authenticated()
                .antMatchers(HttpMethod.POST,"/api/post/**").authenticated()
                .anyRequest().permitAll()

//                .and()
//                .formLogin().loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll()

//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .permitAll()

//
//                .and()
//                .rememberMe()
//                .key("uniqueAndSecret")
//                .tokenValiditySeconds(86400)
//                .userDetailsService(userDetailsService)
//                .alwaysRemember(true)
//                .useSecureCookie(true)
        ;
    }

}
