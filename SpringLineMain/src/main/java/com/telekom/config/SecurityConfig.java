package com.telekom.config;

import com.telekom.security.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.telekom.model.entity")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String WELCOME = "/welcome";

    @Autowired
    private AuthProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/tariffs/**", "/options/**", "/users/**", "/optionGroups/**", "/newContract/**").hasRole("ADMIN")
                .antMatchers(WELCOME).hasRole("USER")
                .antMatchers("/existingContract/**", "/tariffPages").hasAnyRole("USER", "ADMIN")
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl(WELCOME)
                .failureUrl("/login?error=true")
                .usernameParameter("login").passwordParameter("password").permitAll()
                .and().exceptionHandling()
                .accessDeniedPage(WELCOME)
                .and().logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }


}
