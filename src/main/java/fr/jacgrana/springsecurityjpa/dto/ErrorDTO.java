package fr.jacgrana.springsecurityjpa.dto;

import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private ErrorCodeEnum code;
    private String message;

}
