package fr.jacgrana.springsecurityjpa.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnimalLinkDTO {
    @NotNull
    private Integer idUSer;
    @NotNull
    private Integer idAnimal;
}
