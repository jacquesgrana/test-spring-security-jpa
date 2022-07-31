package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElse(null);
    }

}
