package fr.jacgrana.springsecurityjpa.exceptions;

import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import org.springframework.security.core.AuthenticationException;

public class BadAuthenticationException extends AuthenticationException {

    private ErrorCodeEnum code;

    public BadAuthenticationException(ErrorCodeEnum code, String msg) {
        super(msg);
        this.code = code;
    }

    public ErrorCodeEnum getCode() {
        return code;
    }
}
