package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.detail.MyUserDetails;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
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
        //auth.userDetailsService(userDetailsService);
        List<UserDetails> userDetails = new ArrayList<>();
        List<User> users =myUserDetailService.getAllUser();
        for(User user : users) {
            userDetails.add(new MyUserDetails(user));
        }
                // TODO recuperer liste des user et constriure la liste des user
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //.and().csrf().disable() http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/admin").hasAuthority(UserRoleEnum.ROLE_ADMIN.toString())
                .antMatchers("/manager").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers("/user/{id}").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers("/user/all").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                .antMatchers("/user").hasAnyAuthority(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                .antMatchers("/").permitAll()
                .and().formLogin();
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
