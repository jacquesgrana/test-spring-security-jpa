package fr.jacgrana.springsecurityjpa.exceptions;

import fr.jacgrana.springsecurityjpa.enums.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class BadAuthenticationException extends AuthenticationException {

    private ErrorCode code;

    public BadAuthenticationException(ErrorCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
