package com.techprimer.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//@EnableWebSecurity
public class JDBCSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource) //user and authority table created
                .usersByUsernameQuery("select username, password, enabled" +
                                "from users where username = ?"
                                )
                .authoritiesByUsernameQuery("select username, authority " +
                        "from authorities where username=?");

        //adding h2 embedded db,spring boot creeates h2 datasource autmatcially if you add
        //h2 in class path,

      /*  auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()  // default opinion about users/authorities tables etc,spring do it for you, create table
                .withUser(
                        User.withUsername("ram") //insert data in user tables
                        .password("ram")
                        .roles("USER")
                )
                .withUser(
                        User.withUsername("sam")
                                .password("sam")
                                .roles("ADMIN")
                );*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
                .and().formLogin();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
