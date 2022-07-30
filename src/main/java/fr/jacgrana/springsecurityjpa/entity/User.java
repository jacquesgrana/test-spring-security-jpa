package fr.jacgrana.springsecurityjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

    @Column(name = "roles", nullable = false)
    private String roles;

    public boolean isActive() {
        return this.active;
    }

}
