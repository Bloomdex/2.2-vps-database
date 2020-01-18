package org.bloomdex.datamcbaseface.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
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
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Configuration for users in the database.
     * @param builder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        var passwordEncoder = new BCryptPasswordEncoder();

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("bloomdex-test-dummy").password(passwordEncoder.encode("8pB5CYeXWmAPmMn8gS9W6Q5NApRR7QB82sDoCpUCB8MQ9auY")).roles("USER"))
                .passwordEncoder(passwordEncoder);
    }
}
