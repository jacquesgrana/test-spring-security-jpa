package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.detail.MyUserDetails;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import fr.jacgrana.springsecurityjpa.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private JWTEntryPoint jwtEntryPoint;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JWTUtil jwtUtil;

    @Bean
    public InMemoryUserDetailsManager configureAuthentication() {
        List<User> users = myUserDetailService.getAllUser();
        List<UserDetails> userDetails = users.stream().map(u -> new MyUserDetails(u)).collect(Collectors.toList());
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //.and().csrf().disable() http.csrf().disable();
/*
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
                .antMatchers(HttpMethod.GET, "/signin").permitAll()
                //.and().formLogin()
                //.anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();  // TODO enlever des que possible
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();*/

        http
                .authorizeRequests(
                        (query)-> query

                                .antMatchers(HttpMethod.GET, "/admin").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.POST, "/admin/create").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.PUT, "/admin/update/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.DELETE, "/admin/delete/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.GET, "/manager").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user/all").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()

                .httpBasic(Customizer.withDefaults());
        //jwtUtil = new JWTUtil();
        //jwtRequestFilter = new JwtRequestFilter(myUserDetailService, jwtUtil, "Authorization");
        //httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilter()
        return http.build();
    }

/*
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }
*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return (web) -> web.ignoring()
                .antMatchers(POST,"/signin")
                .antMatchers(GET,"/");
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(8);
    }
}
