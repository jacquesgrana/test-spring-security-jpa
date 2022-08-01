package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.detail.MyUserDetails;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Bean
    protected InMemoryUserDetailsManager configureAuthentication() {
        List<User> users =myUserDetailService.getAllUser();
        List<UserDetails> userDetails = users.stream().map(u -> new MyUserDetails(u)).collect(Collectors.toList());
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //.and().csrf().disable() http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admin").hasAuthority(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/admin/create").hasAuthority(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers(HttpMethod.PUT, "/admin/update/{id}").hasAuthority(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/admin/delete/{id}").hasAuthority(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers(HttpMethod.GET, "/manager").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers(HttpMethod.GET, "/user/{id}").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers(HttpMethod.GET, "/user/all").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers(HttpMethod.GET, "/user").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .and().formLogin()
                .and().csrf().disable();  // TODO enlever des que possible
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(8);
    }
}
