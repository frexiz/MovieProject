package com.catalog.movies.config;

import com.catalog.movies.core.users.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
//@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests().anyRequest().permitAll().and().csrf();
//        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().hasAnyRole().antMatchers("/auth/login").permitAll()
//
//                .and().formLogin().loginPage("/auth/login").defaultSuccessUrl("/")
//                .usernameParameter("email").passwordParameter("password").and()
//                .logout().logoutSuccessUrl("/auth/login?logout")
//                .and().exceptionHandling().accessDeniedPage("/error/403").and().csrf();
//
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/global/**", "/assets/**", "src/main/resources/static/**", "/resources/**").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/auth/logout").authenticated()
                .antMatchers("/user/**").authenticated()

                .and().formLogin().loginPage("/auth/login").defaultSuccessUrl("/", true).
                usernameParameter("email").passwordParameter("password")
                .and().sessionManagement().maximumSessions(1).expiredUrl("/auth/login")
                .and().and().logout().logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login").deleteCookies().and().csrf()
                .and().exceptionHandling().accessDeniedPage("/error/403").and().csrf().disable();
    }


}
