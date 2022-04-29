package com.pms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pms.filter.CustomAuthenticationFilter;
import com.pms.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /*
    * configure method defines and protects routes.
    * It checks for authentication and authorization.
    * */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        /*
        The system allows to login and refresh to all the users.
         */
        http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh").permitAll();


        /*
        * Resources available only to admin.
        * */
        http.authorizeRequests().antMatchers(GET,"/api/users").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/managers").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/role/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/admin/add-department").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/payroll/weekly-timesheet").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST,"/api/payroll/weekly-timesheet").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
