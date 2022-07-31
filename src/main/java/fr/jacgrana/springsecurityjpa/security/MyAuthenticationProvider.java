package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.entity.Role;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.service.IUserDetailService;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import fr.jacgrana.springsecurityjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Boolean isUSerIn = myUserDetailService.isUserIn(username, password);
        User user = userService.findByUsername(username);
        if(user == null) {
            isUSerIn = false;
        }
        if(isUSerIn) {
            //System.out.println("user : " + username + " / " + password);
            for(Role role : user.getRoles()) {
                //System.out.println("role : " + role.toString());
            }
            return new UsernamePasswordAuthenticationToken(
                    user.getUserName(),
                    user.getPassword(),
                    user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));

        }
        else {
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
