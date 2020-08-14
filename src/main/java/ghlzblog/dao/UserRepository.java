package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.User;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String code);

}
