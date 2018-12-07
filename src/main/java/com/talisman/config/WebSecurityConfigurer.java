package com.talisman.config;

import com.talisman.authenticators.DbAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private DbAuthenticator dbAuthenticator;

    @Autowired
    public WebSecurityConfigurer(final DbAuthenticator dbAuthenticator) {

        this.dbAuthenticator = dbAuthenticator;
    }

    @Autowired
    public void configureGlobalAuthentication(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{

        authenticationManagerBuilder.userDetailsService(this.dbAuthenticator);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable();

        httpSecurity.authorizeRequests().antMatchers("/orderList", "/order", "/accountInformation")
                    .access("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')");

        httpSecurity.authorizeRequests().antMatchers("/product").access("hasRole('ROLE_MANAGER')");

        httpSecurity.authorizeRequests().and().exceptionHandling().accessDeniedPage("/accessDenied");

        httpSecurity.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/springSecurityCheck")
                .loginPage("/login")
                .defaultSuccessUrl("/accountInformation")
                .failureUrl("/login?error=true")
                .usernameParameter("userName")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }
}
