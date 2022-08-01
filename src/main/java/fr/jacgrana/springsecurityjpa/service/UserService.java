package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User read(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void create(User user) {
        Optional<User> userOptional = this.userRepository.findByUserName(user.getUserName());

        if(userOptional.isPresent()) {
            // TODO lancer exception perso
        }
        else {
            this.userRepository.save(user);
        }
    }

    public void update(User updatedUser, Integer id) {
        User userInDb = this.read(id);
        if (userInDb != null) {
            userInDb.setActive(updatedUser.getActive());
            userInDb.setPassword(updatedUser.getPassword());
            userInDb.setRoles(updatedUser.getRoles());
            userRepository.save(userInDb);
        }
    }

    public void delete(Integer id) {
        User userToDelete = this.read(id);
        if (userToDelete != null) {
            this.userRepository.delete(userToDelete);
        }
    }
}
