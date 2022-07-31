package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //.and().csrf().disable() http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers("/manager").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers("/user").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                .antMatchers("/").permitAll()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
