package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.detail.MyUserDetails;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements IUserDetailService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu avec le login : " + userName));
        //System.out.println("user : " + user.get().getActive() + " / roles : " + user.get().getRoles());
        return user.map(MyUserDetails::new).get();
    }

    public List<User>  getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Boolean isUserIn(String username, String password) {
        return true;
    }
}
