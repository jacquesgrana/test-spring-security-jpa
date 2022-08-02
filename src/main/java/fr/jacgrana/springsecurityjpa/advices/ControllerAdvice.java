package fr.jacgrana.springsecurityjpa.advices;

import fr.jacgrana.springsecurityjpa.dto.ErrorDTO;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.exceptions.BadAuthenticationException;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.exceptions.ForbiddenAccesException;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

@Slf4j
@RestController
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public @ResponseBody ErrorDTO handleBadRequestException(BadRequestException exception) {
        //exception.printStackTrace();
        //log.error("Une erreur : ", exception);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(exception.getCode());
        errorDTO.setMessage(exception.getMessage());
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadAuthenticationException.class)
    public @ResponseBody ErrorDTO handleBadCredentialException(BadAuthenticationException exception) {
        //exception.printStackTrace();
        //log.error("Une erreur : ", exception);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(exception.getCode());
        errorDTO.setMessage(exception.getMessage());
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public @ResponseBody ErrorDTO handleForbiddenAccesException(AccessDeniedException exception) {
        //exception.printStackTrace();
        //log.error("Une erreur : ", exception);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(ErrorCodeEnum.FORBIDDEN_ACCES);
        errorDTO.setMessage("Accès refusé");
        return errorDTO;
    }

/*
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AuthenticationException.class)
    public @ResponseBody ErrorDTO handleForbiddenAccesException(AuthenticationException exception) {
        //exception.printStackTrace();
        //log.error("Une erreur : ", exception);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(ErrorCodeEnum.FORBIDDEN_ACCES);
        errorDTO.setMessage("Accès refusé");
        return errorDTO;
    }*/

/*
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = IOException.class)
    public @ResponseBody ErrorDTO handleForbiddenAccesException(IOException exception) {
        //exception.printStackTrace();
        //log.error("Une erreur : ", exception);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(ErrorCodeEnum.FORBIDDEN_ACCES);
        errorDTO.setMessage("Accès refusé");
        return errorDTO;
    }*/

}