package fr.jacgrana.springsecurityjpa.exceptions;

import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;

public class BadRequestException extends Exception{

    private ErrorCodeEnum code;
    public BadRequestException(ErrorCodeEnum code, String message) {
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