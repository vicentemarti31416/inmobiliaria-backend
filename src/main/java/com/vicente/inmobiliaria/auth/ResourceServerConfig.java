package com.vicente.inmobiliaria.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/clientes/", "/clientes/page/**", "/uploads/img/**").permitAll()
                .antMatchers(HttpMethod.GET, "/clientes/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/clientes/upload").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
                .antMatchers("/clientes/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }


}
