package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.Role;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) throws BadRequestException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new BadRequestException(ErrorCodeEnum.USER_NOT_FOUND, "Pas d'utilisateur avec cet username : " + username));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User read(Integer id) throws BadRequestException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new BadRequestException(ErrorCodeEnum.USER_NOT_FOUND, "Pas d'utilisateur avec cet id : " + id));
    }

    public void create(User user) throws BadRequestException {
        Optional<User> userOptional = this.userRepository.findByUserName(user.getUserName());
        if(userOptional.isPresent()) {
            throw new BadRequestException(ErrorCodeEnum.USERNAME_ALREADY_EXISTS, "Il y a déjà un user avec cet username : " + user.getUserName());
        }
        else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            //System.out.println("Encoded password : " + encodedPassword);
            this.userRepository.save(user);
        }
    }

    public void update(User updatedUser, Integer id) throws BadRequestException {
        User userInDb = this.read(id);
        if (userInDb != null) {
            userInDb.setActive(updatedUser.getActive());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(8);
            String encodedPassword = bCryptPasswordEncoder.encode(updatedUser.getPassword());
            //System.out.println("Encoded password : " + encodedPassword);
            userInDb.setPassword(encodedPassword);
            userInDb.setRole(updatedUser.getRole());
            userRepository.save(userInDb);
        }
    }

    public void delete(Integer id) throws BadRequestException {
        User userToDelete = this.read(id);
        if (userToDelete != null) {
            this.userRepository.delete(userToDelete);
        }
    }

    // TODO faire methode qui renvoie le role d'un user trouvé par son username

    public Role getUserRoleByUsername(String username) throws BadRequestException {
        User user = this.findByUsername(username);
        return user.getRole();
    }
}
