package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.dto.ErrorDTO;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
public class AccessDeniedController {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping("/unauthorized")
    public ErrorDTO unauthorized() {
        ErrorDTO dto = new ErrorDTO(ErrorCodeEnum.FORBIDDEN_ACCES, "Accès interdit");
        return dto;
    }
}
