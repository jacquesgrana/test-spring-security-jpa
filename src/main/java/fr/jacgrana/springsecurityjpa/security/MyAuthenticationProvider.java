package fr.jacgrana.springsecurityjpa.security;

import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.ErrorCode;
import fr.jacgrana.springsecurityjpa.exceptions.BadAuthenticationException;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import fr.jacgrana.springsecurityjpa.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws BadAuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Boolean isUSerIn = myUserDetailService.isUserOk(username, password);
        if(isUSerIn) {
            User user = userService.findByUsername(username);
            return new UsernamePasswordAuthenticationToken(
                    user.getUserName(),
                    user.getPassword(),
                    user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList()));
        }
        else {
            throw new BadAuthenticationException(ErrorCode.BAD_CREDENTIALS, "Authentification ko!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
