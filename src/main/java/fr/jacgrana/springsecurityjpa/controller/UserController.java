package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.dto.ErrorDTO;
import fr.jacgrana.springsecurityjpa.dto.SignInRequestDTO;
import fr.jacgrana.springsecurityjpa.dto.SignInResponseDTO;
import fr.jacgrana.springsecurityjpa.entity.Role;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.exceptions.BadAuthenticationException;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.service.MyUserDetailService;
import fr.jacgrana.springsecurityjpa.service.UserService;
import fr.jacgrana.springsecurityjpa.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5500/*")
//@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE) //path = "user",
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JWTUtil jwtUtil;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return("<h1>Bienvenue</h1>");
    }

    @GetMapping("/user")
    //@RequestMapping(produces = MediaType.APPLICATION_XHTML_XML)
    public String user() {
        return("<h1>Bienvenue utilisateur</h1>");
    }

    @GetMapping("/manager")
    public String manager() {
        return("<h1>Bienvenue manager</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return("<h1>Bienvenue administrateur</h1>");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/admin/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) throws BadRequestException{
        this.userService.create(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/admin/update/{id}")
    public void update(@RequestBody User user,  @PathVariable("id") Integer id) throws BadRequestException{
        this.userService.update(user, id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/admin/delete/{id}")
    public void delete(@PathVariable("id") Integer id) throws BadRequestException {
        this.userService.delete(id);
    }

    // TODO faire methode getUserByUsername pour /user/username/{username} qui renvoie l'user'
    // TODO autoriser pour manager et admin dans security config

    @GetMapping("/user/username/{username}")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername (@PathVariable("username") String username) throws BadRequestException {
        return this.userService.findByUsername(username);
    }

    @GetMapping("/user/role/{username}")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getRoleByUsername (@PathVariable("username") String username) throws BadRequestException {
        return this.userService.getUserRoleByUsername(username);
    }

    @GetMapping("/user/all")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return this.userService.findAll();
    }

  //  @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/user/{id}")
    public User read(@PathVariable("id") Integer id) throws BadRequestException {
        return this.userService.read(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/signin") //, consumes = MediaType.APPLICATION_JSON_VALUE
    public SignInResponseDTO signin(@RequestBody SignInRequestDTO request) throws BadAuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new BadAuthenticationException(ErrorCodeEnum.BAD_CREDENTIALS, "Valeurs non reconnues!");
        }

        UserDetails userDetails = userDetailService
                .loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        SignInResponseDTO dto = new SignInResponseDTO(token);
        return dto;
    }

    /*
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) throws Exception {
        this.userService.create(user);
    }*/

}
