package com.bs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity     //Tells Hibernate to make a table out of this class
@Table(name = "tasks")    //表名
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class todoTask implements Serializable {

  public todoTask() {
  }

  @Override
  public String toString() {
    return "todoTask{" +
        "id=" + id +
        ", authorID=" + authorID +
        ", assignedToID=" + assignedToID +
        ", bodyText='" + bodyText + '\'' +
        ", dateTime=" + dateTime +
        '}';
  }

  @Id     //id约束
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ID")    //映射行列名和长度
  private Integer id;

  @Column(name = "AUTHORID")
  private Integer authorID;

  @Column(name = "Author_name")
  private String authorName = "";

  @Column(name = "ASSIGNEDTOID")
  private Integer assignedToID;

  @Column(name = "assignedto_name")
  private String assignedToName;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "UPDATED_AT")
  private Timestamp updatedAt;

  @Column(name = "Completed_at")
  private Timestamp completedAt;

  @Column(name = "Completed_day")
  private Integer completedDay;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "BODYTEXT")
  private String bodyText;

 // @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "DATE")
  private Timestamp dateTime;
  //private Date dateTime = new Date();

  @Column(name = "DUEDATE")
  private String dueDate;

  @Column(name = "DONE")
  private int done;

  @Column(name = "VISIBLE")
  private int visible;

  @Column(name = "followed")
  private int followed; // todo delete this on front end before deleting here

  @ManyToMany(cascade = CascadeType.ALL)
//  @JoinTable
 // @ManyToMany(mappedBy = "tasksFollowed")
  @JoinTable
//  @JsonBackReference(value = "user-followtasks")
//  @JsonManagedReference(value = "followed_by")
  private Set<User> followedBy = new HashSet<>();

  public Set<User> getFollowedBy() {
    return followedBy;
  }

  public void setFollowedBy(Set<User> followedBy) {
    this.followedBy = followedBy;
  }

  public Integer getCompletedDay() {
    return completedDay;
  }

  public void setCompletedDay(Integer completedDay) {
    this.completedDay = completedDay;
  }

  public Timestamp getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Timestamp completedAt) {
    this.completedAt = completedAt;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public int getFollowed() {
    return followed;
  }

  public void setFollowed(int followed) {
    this.followed = followed;
  }

  public String getAssignedToName() {
    return assignedToName;
  }

  public void setAssignedToName(String assignedToName) {
    this.assignedToName = assignedToName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAuthorID() {
    return authorID;
  }

  public void setAuthorID(Integer authorID) {
    this.authorID = authorID;
  }

  public Integer getAssignedToID() {
    return assignedToID;
  }

  public void setAssignedToID(Integer assignedToID) {
    this.assignedToID = assignedToID;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getBodyText() {
    return bodyText;
  }

  public void setBodyText(String bodyText) {
    this.bodyText = bodyText;
  }

  public Timestamp getDateTime() {
    return dateTime;
  }

  public void setDateTime(Timestamp dateTime) {
    this.dateTime = dateTime;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public int getDone() {
    return done;
  }

  public void setDone(int done) {
    this.done = done;
  }

  public int getVisible() {
    return visible;
  }

  public void setVisible(int visible) {
    this.visible = visible;
  }
}
