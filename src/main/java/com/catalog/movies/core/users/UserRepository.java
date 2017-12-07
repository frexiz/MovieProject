package com.catalog.movies.core.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    boolean exists(final Integer id);

    @Query(value="select fullName from users where id = ?1", nativeQuery = true)
    String getUserNameById(Integer id);

}
