package com.catalog.movies.core.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceManager implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(final String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
