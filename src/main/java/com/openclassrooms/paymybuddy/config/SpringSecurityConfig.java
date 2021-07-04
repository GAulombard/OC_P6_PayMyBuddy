package com.openclassrooms.paymybuddy.config;

import com.openclassrooms.paymybuddy.security.UserAuthenticationSuccessHandler;
import com.openclassrooms.paymybuddy.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Autowired
    UserDetailsService userDetailsService;*/

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorize -> {
                    authorize.antMatchers("/","/index","/login","/signup","/error/403","/error/404")
                            .permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login.html")
                //.defaultSuccessUrl("/", true)
                .successHandler(AuthenticationSuccessHandler()) // customise success authentication handler
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedPage("/error/403")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler AuthenticationSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

}

