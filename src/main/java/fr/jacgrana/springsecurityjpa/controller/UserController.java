package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE) //path = "user",
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return("<h1>Bienvenue</h1>");
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
    @PostMapping(path = "admin/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        this.userService.create(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "admin/update/{id}")
    public void update(@RequestBody User user,  @PathVariable("id") Integer id) {
        this.userService.update(user, id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "admin/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        this.userService.delete(id);
    }

    @GetMapping("/user")
    //@RequestMapping(produces = MediaType.APPLICATION_XHTML_XML)
    public String user() {
        return("<h1>Bienvenue utilisateur</h1>");
    }

    @GetMapping("/user/all")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return this.userService.findAll();
    }

    @GetMapping(path = "/user/{id}")
    public User read(@PathVariable("id") Integer id) throws Exception {
        return this.userService.read(id);
    }

    /*
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) throws Exception {
        this.userService.create(user);
    }*/

}
