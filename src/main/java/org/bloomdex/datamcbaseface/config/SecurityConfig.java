package org.bloomdex.datamcbaseface.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.bloomdex.datamcbaseface.controller.AbstractController.api_prefix;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    public static BCryptPasswordEncoder PasswordEncoder;

    private DataSource dataSource;

    /**
     * Creating this SecurityConfig should happen automatically,
     * if you are creating this object by hand you are probably doing something wrong!
     * @param dataSource The datasource the database should be put in
     */
    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Authentication setup.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().exceptionHandling()
                .authenticationEntryPoint(
                    (httpServletRequest, httpServletResponse, e)
                            -> httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/" + api_prefix + "logout"))
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpStatus.OK.value()))
                .and().cors()
                .and().csrf().disable();
    }

    /**
     * Configuration for users in the database.
     * @param builder
     * @throws Exception Thrown when builder.jdbcAuthentication() fails.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        PasswordEncoder = new BCryptPasswordEncoder();

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(PasswordEncoder);
    }
}
