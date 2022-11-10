package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.detail.MyUserDetails;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import fr.jacgrana.springsecurityjpa.exceptions.ForbiddenAccesException;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

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
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class) //adds your custom CorsFilter
                .authorizeRequests(
                        (query)-> query
                                .antMatchers(HttpMethod.GET, "/admin").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.POST, "/admin/create").hasRole(UserRoleEnum.ROLE_ADMIN.toString()) // TODO modifier chemin : /admin/user/create idem pour les deux lignes d'après
                                .antMatchers(HttpMethod.PUT, "/admin/update/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.DELETE, "/admin/delete/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                // /admin/user/link/animal // /admin/user/unlink/animal
                                .antMatchers(POST, "/admin/user/link/animal").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(POST, "/admin/user/unlink/animal").hasRole(UserRoleEnum.ROLE_ADMIN.toString())

                                .antMatchers(HttpMethod.GET, "/manager").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user/all").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString())
                                .antMatchers(HttpMethod.GET, "/user").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/user/role/{username}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/user/username/{username}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/animal/all").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/animal/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.POST, "/animal/create").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.PUT, "/animal/update/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.DELETE, "/animal/delete/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/animal/orphans").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/animaltype/all").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.GET, "/animaltype/{id}").hasAnyRole(UserRoleEnum.ROLE_ADMIN.toString(), UserRoleEnum.ROLE_MANAGER.toString(), UserRoleEnum.ROLE_USER.toString())
                                .antMatchers(HttpMethod.POST, "/animaltype/create").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.PUT, "/animaltype/update/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())
                                .antMatchers(HttpMethod.DELETE, "/animaltype/delete/{id}").hasRole(UserRoleEnum.ROLE_ADMIN.toString())

                                .anyRequest().authenticated())
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // .accessDeniedPage("/403.html")
                .authenticationEntryPoint(jwtEntryPoint)
                //.and().cors()
                //.and().cors()
                .and().csrf().disable()
                .httpBasic().disable();
                //.httpBasic(Customizer.withDefaults());
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
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccesDeniedHandler();
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return (web) -> web.ignoring()
                .antMatchers(POST,"/signin")
                .antMatchers(GET,"/")
                .antMatchers(GET,"/unauthorized")
                .antMatchers(GET,"/user/role/{username}"); //.antMatchers(GET,"/unauthorized")
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
