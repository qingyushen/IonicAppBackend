package com.bs.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity     //Tells Hibernate to make a table out of this class
@Table(name = "user")    //表名
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable{

  public User() {
  }

  public User(String name, String email, String password, String team) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.team = team;
  }

  @Id     //id约束
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ID")    //映射行列名和长度
  private Integer id=-1;

  @Column(name = "NAME")  //映射行列名和长度
  private String name="";

  @Column(name = "EMAIL")   //映射行列名和长度
  private String email="";

  @Column(name = "PASSWORD")
  private String password="";

  @Column(name = "PRIVILEGE")
  private String privilege=""; // "admin" or "member"

  @Column(name = "TEAM")
  private String team="";

  @Column(name = "tasks_completed")
  private Integer tasksCompleted = 0;

  @Column(name = "complete_time")
  private Long completeTime = (long)0; // time spent completing tasks in seconds

  @Column(name = "profile_img_url")
  private String profileImgUrl = "";

  @Column(name = "cover_img_url")
  private String coverImgUrl = "";

  @Column(name = "avg_time")
  private Long avgTime = (long)0;

  @Column(name = "def_lang")
  private String language = "cn";

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;

    User otherUser = (User) o;

    if (otherUser.getId() == this.getId()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = this.getId().hashCode();
    return result;
  }


  //  @ManyToMany(fetch = FetchType.LAZY,
//      cascade = {
//          CascadeType.PERSIST,
//          CascadeType.MERGE
//      })
//  @JoinTable(name = "task_group",
//      joinColumns = { @JoinColumn(name = "task_id") },
//      inverseJoinColumns = { @JoinColumn(name = "team_id") })
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable
 // @JsonManagedReference
  private Set<Group> groups = new HashSet<>();

  @ManyToMany(mappedBy = "followedBy")
//  @JsonBackReference
  //@ManyToMany(cascade = CascadeType.ALL)
  @JsonBackReference(value="followed_by")
  private Set<todoTask> tasksFollowed = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable
  private Set<Group> groupsAdminOf = new HashSet<>();

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCoverImgUrl() {
    return coverImgUrl;
  }

  public void setCoverImgUrl(String coverImgUrl) {
    this.coverImgUrl = coverImgUrl;
  }

  public String getProfileImgUrl() {
    return profileImgUrl;
  }

  public void setProfileImgUrl(String profileImgUrl) {
    this.profileImgUrl = profileImgUrl;
  }

  public Set<Group> getGroupsAdminOf() {
    return groupsAdminOf;
  }

  public void setGroupsAdminOf(Set<Group> groupsAdminOf) {
    this.groupsAdminOf = groupsAdminOf;
  }

  public Set<todoTask> getTasksFollowed() {
    return tasksFollowed;
  }

  public void setTasksFollowed(Set<todoTask> tasksFollowed) {
    this.tasksFollowed = tasksFollowed;
  }

  public Long getAvgTime() {
    return avgTime;
  }

  public void setAvgTime(Long avgTime) {
    this.avgTime = avgTime;
  }

  public Integer getTasksCompleted() {
    return tasksCompleted;
  }

  public void setTasksCompleted(Integer tasksCompleted) {
    this.tasksCompleted = tasksCompleted;
  }

  public Long getCompleteTime() {
    return completeTime;
  }

  public void setCompleteTime(Long completeTime) {
    this.completeTime = completeTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPrivilege() {
    return privilege;
  }

  public void setPrivilege(String privilege) {
    this.privilege = privilege;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

}
