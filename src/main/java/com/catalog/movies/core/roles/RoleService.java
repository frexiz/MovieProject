package com.catalog.movies.core.roles;

import java.util.List;

public interface RoleService {
    Role findByName(final String name);

    List<Role> findAll();
}
