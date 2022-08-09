package fr.jacgrana.springsecurityjpa.entity;

import fr.jacgrana.springsecurityjpa.enums.GenreEnum;
import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_animal_type", nullable=false)
    private AnimalType animalType;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "genre", nullable=false)
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @Column(name = "birth", nullable=false)
    private LocalDate birth;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
