package fr.jacgrana.springsecurityjpa.advices;

import fr.jacgrana.springsecurityjpa.dto.ErrorDTO;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
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
}