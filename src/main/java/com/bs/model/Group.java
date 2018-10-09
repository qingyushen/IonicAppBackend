package com.bs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity     //必须的实体注释，表明其交给jpa管理
@Table(name = "team")    //表名
public class Group implements Serializable{
  @Id     //id约束
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ID")    //映射行列名和长度
  private Integer id;
  @Column(name = "NAME")  //映射行列名和长度
  private String name;

  @Column(name = "password")
  private String password;

//  @ManyToMany(fetch = FetchType.LAZY,
//      cascade = {
//          CascadeType.PERSIST,
//          CascadeType.MERGE
//      },
//      mappedBy = "team")
  @ManyToMany(mappedBy = "groups")
  @JsonBackReference(value = "user-groups")
  private Set<User> users = new HashSet<>();

  @ManyToMany(mappedBy = "groupsAdminOf")
  @JsonBackReference(value = "admin-groups")
  private Set<User> admins = new HashSet<>();

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<User> getAdmins() {
    return admins;
  }

  public void setAdmins(Set<User> admins) {
    this.admins = admins;
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

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Group() {
  }

  public Group(String name) {
    this.setName(name);
  }

  public Group(String name, String password) {
    this.name = name;
    this.password = password;
  }

  @Override
  public String toString() {
    return "Group{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
