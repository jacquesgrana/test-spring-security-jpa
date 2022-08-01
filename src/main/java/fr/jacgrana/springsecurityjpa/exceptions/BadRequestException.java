package fr.jacgrana.springsecurityjpa.exceptions;

import fr.jacgrana.springsecurityjpa.enums.ErrorCode;

public class BadRequestException extends Exception{

    private ErrorCode code;
    public BadRequestException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }
}