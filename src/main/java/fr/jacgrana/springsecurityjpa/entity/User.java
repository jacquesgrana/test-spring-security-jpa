package fr.jacgrana.springsecurityjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    /*
    @Column(name = "roles", nullable = false)
    private String roles;
*/

    /*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="User_role",
            joinColumns= @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="role_id", referencedColumnName="id"))
    private List<Role> roles = new ArrayList<>();
*/

    @ManyToOne
    @JoinColumn(name="id_role", nullable=false)
    private Role role;
}
