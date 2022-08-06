package fr.jacgrana.springsecurityjpa.detail;

import fr.jacgrana.springsecurityjpa.entity.Role;
import fr.jacgrana.springsecurityjpa.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private Boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.getActive();
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getLabel().toString())).collect(Collectors.toList());

        //System.out.println("log : " + user.getRoles().toString());
        //this.authorities = new ArrayList<>();
        /*
        for(Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getLabel().toString()));
            //System.out.println("log : " + role.getLabel().toString());
        }*/
       // this.authorities = Arrays.stream(user.getRoles().split(",")).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
