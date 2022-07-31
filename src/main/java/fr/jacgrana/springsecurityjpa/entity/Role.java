package fr.jacgrana.springsecurityjpa.entity;

import fr.jacgrana.springsecurityjpa.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "label")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum label;

    @Override
    public String toString() {
        return this.getLabel().toString();
    }
}