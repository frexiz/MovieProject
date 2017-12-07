package com.catalog.movies.core.roles;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(final String name);

    List<Role> findAll();
}
