package com.catalog.movies.core.roles;

import com.catalog.movies.core.users.User;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Transient
    public String getSimpleName() {
        return StringUtils.capitalize(this.getName().substring(5).toLowerCase());

    }
}
