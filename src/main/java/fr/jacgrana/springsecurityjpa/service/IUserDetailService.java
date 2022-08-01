package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserDetailService extends UserDetailsService {

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    public List<User> getAllUser();

    Boolean isUserOk(String username, String password);
}
