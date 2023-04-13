package com.mracover.if_else_task.models.userModels;

import com.mracover.if_else_task.models.animalModels.Animal;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "chipperId",
            cascade=CascadeType.ALL,
            orphanRemoval=true)
    private List<Animal> animalList;

    @ManyToOne(cascade = {
                CascadeType.REFRESH,
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.PERSIST
                })
    @JoinColumn(name = "roleId")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> role = new ArrayList<>();
        role.add(getRole());
        return role;
    }

    @Override
    public String getUsername() {
        return null;
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
        return true;
    }
}
