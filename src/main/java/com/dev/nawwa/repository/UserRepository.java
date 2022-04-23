package com.dev.nawwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.nawwa.domain.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	User findUserByUserName(String username);
	
	User findUserByEmail(String email);
}
