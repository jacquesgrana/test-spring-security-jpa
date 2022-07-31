package fr.jacgrana.springsecurityjpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResourceController {

    @GetMapping("/")
    public String home() {
        return("<h1>Bienvenue</h1>");
    }

    @GetMapping("/user")
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

}
