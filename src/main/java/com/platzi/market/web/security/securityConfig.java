package com.platzi.market.web.security;

import com.platzi.market.domain.service.ErikUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class securityConfig {

    private ErikUserDetailsService erikUserDetailsService;

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(erikUserDetailsService);
    }*/

    @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder builder = User.withDefaultPasswordEncoder();
        erikUserDetailsService = new ErikUserDetailsService();
        UserDetails user = erikUserDetailsService.loadUserByUsername("");
        //UserDetails admin = erikUserDetailsService.loadUserByUsername("").getPassword();
        //UserDetails user = builder.username("user").password("password").roles("USER").build();
        //UserDetails admin = builder.username("admin").password("password").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate").permitAll()
            .anyRequest().authenticated();

        /*http.authorizeRequests().antMatchers("/products").permitAll()
                .antMatchers("/products/**", "/settings/**").hasAuthority("Admin")
                //.hasAnyAuthority("Admin", "Editor", "Salesperson")
                //.hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/products/all")
                    .usernameParameter("erik")
                    .permitAll()
                .and()
                //.usernameParameter("123")
                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                .and()
                .logout().permitAll();*/

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
