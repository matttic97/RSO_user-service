package com.izmenjavazapiskov.users.repository;

import com.izmenjavazapiskov.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(long id);

    //Long deleteByUserId(long id);

}
