package com.fareye;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by khushal on 4/8/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    /*method to grant access to a particular view/link*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/register").permitAll().antMatchers("/login").permitAll().antMatchers("/createUser").permitAll().
                antMatchers("/webjars/angularjs/1.4.9/angular.js").permitAll().antMatchers("/angular-ui-router.js").permitAll().antMatchers("/myModule.js").permitAll().antMatchers("/myController.js").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .passwordParameter("password")
                .usernameParameter("name")
                .permitAll()
                .and()
                .logout()
                .permitAll().
                and().csrf().disable();
        http.userDetailsService(userDetailsService());


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService());

    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
