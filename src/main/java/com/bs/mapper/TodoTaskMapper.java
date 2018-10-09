package com.bs.mapper;

import com.bs.model.User;
import com.bs.model.todoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TodoTaskMapper extends JpaRepository<todoTask,Long>,JpaSpecificationExecutor<todoTask> {
    @Query(value = "select * from tasks",nativeQuery = true)
    public List<todoTask> testFindAll();
//    @Query("select t from tasks t where t.assignedToID = ?1 and t.groupID = ?2")
//    public List<todoTask> get(String person, String group);
    public List<todoTask> getAllByAssignedToIDAndAndGroupName(Integer person, String group);
    public List<todoTask> getAllByAssignedToIDAndGroupNameAndFollowedByNotContaining(Integer id, String grpName, User p);
    public List<todoTask> getAllByAssignedToIDAndAndGroupNameAndFollowed(Integer person, String group, Integer follow);
    public List<todoTask> getAllByAssignedToIDAndGroupNameAndFollowedByContaining(Integer person,
                                                                                  String grp,
                                                                                  User follow);

    public List<todoTask> findTop30ByGroupNameAndVisibleOrderByUpdatedAtDesc(String group, Integer vis);
    public List<todoTask> getAllByGroupNameAndVisibleOrderByAssignedToIDAscDoneAscDueDateDesc(
        String group, int visible);
    public List<todoTask> getAllByGroupNameAndVisible(String group, Integer vis);
    public List<todoTask> getAllByAssignedToID(Integer id);
    public todoTask findById(Integer id);
    public void deleteUserById(Integer id);
    public void deleteById(Integer id);
    public void removeById(Integer id);
    public void deleteAllByAssignedToIDAndGroupName(Integer id, String name);
    public List<todoTask> getAllByAssignedToIDAndGroupNameAndVisible(Integer id, String name, Integer vis);

    @Query(value = "select COUNT(*) from tasks where (completed_day = ?2 and group_name = ?1)", nativeQuery = true)
    public Integer countTasks(String grpName, Integer day);

    @Query("select t from todoTask t where (t.assignedToID = ?1 or t.authorID = ?1) and t.groupName = ?2 and t.followed = 0")
    public List<todoTask> getTasks(Integer userID, String groupName);

   // @Query("select t from todoTask t where (t.assignedToID = ?1 or t.authorID = ?1) and t.groupName = ?2 and t.followed = 1")
    //public List<todoTask> getFollowedTasks(Integer userID, String groupName);
    public List<todoTask> getAllByGroupNameAndFollowedByContaining(String grpName, User current);
}
