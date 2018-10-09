//package com.bs.model;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity     //必须的实体注释，表明其交给jpa管理
//@Table(name = "task_group")    //表名
//public class TaskGroup implements Serializable{
//  @Id     //id约束
//  @GeneratedValue(strategy= GenerationType.IDENTITY)
//  @Column(name = "PROJECT ID",length = 11)    //映射行列名和长度
//  private Integer projectID;
//  @Column(name = "USER ID",length = 11)    //映射行列名和长度
//  private Integer userID;
//
//  public Integer getProjectID() {
//    return projectID;
//  }
//
//  public void setProjectID(Integer projectID) {
//    this.projectID = projectID;
//  }
//
//  public Integer getUserID() {
//    return userID;
//  }
//
//  public void setUserID(Integer userID) {
//    this.userID = userID;
//  }
//}
//
