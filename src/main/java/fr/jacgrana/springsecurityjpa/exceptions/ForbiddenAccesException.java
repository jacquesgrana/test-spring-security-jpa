package fr.jacgrana.springsecurityjpa.exceptions;

import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;

public class ForbiddenAccesException extends Exception{

    private ErrorCodeEnum code;
    public ForbiddenAccesException(ErrorCodeEnum code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCodeEnum getCode() {
        return code;
    }

    public void setCode(ErrorCodeEnum code) {
        this.code = code;
    }
}
