package com.bs.mapper;

import com.bs.model.Group;
import com.bs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface UserMapper extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {
  @Query(value = "select * from user",nativeQuery = true)
  public List<User> getAll();

  @Query("select u from User u where u.email = ?1")
  public List<User> checkExistence(String e);
  public User findUserById(Integer id);
  public void deleteUserById(Integer id);
  public User findUserByEmail(String email);
  public List<User> getAllByGroupsContains(Group group);
}
