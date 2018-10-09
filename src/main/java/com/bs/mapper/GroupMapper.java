package com.bs.mapper;

import com.bs.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface GroupMapper extends JpaRepository<Group,Long>,JpaSpecificationExecutor<Group> {
  @Query(value = "select * from team",nativeQuery = true)
  public List<Group> testFindAll();
  public Group findByName(String name);
  public void deleteProjectById(Integer id);
}