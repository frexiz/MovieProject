package com.catalog.movies.core.users;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User findByEmail(final String email);

    String getUserNameById(final Integer id);

    boolean exists(final Integer id);

    User findOne(final Integer id);

    User saveAndFlush(User user);

    List<User> findAll();

    String validateUserRegisterDTO(final UserRegisterDTO userRegisterDTO) throws IOException;
}
