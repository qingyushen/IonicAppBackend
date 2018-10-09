package com.bs.holder;

import com.bs.model.User;
import com.bs.model.todoTask;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class memberDetailVM {

  private Integer id;
  private Integer authorID;
  private String authorName = "";
  private Integer assignedToID;
  private String assignedToName;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp completedAt;
  private Integer completedDay;
  private String groupName;
  private String bodyText;
  private Timestamp dateTime;
  private String dueDate;
  private int done;
  private int visible;
  private int followed; // todo delete this on front end before deleting here
  private Set<User> followedBy;

  public memberDetailVM(todoTask t) {
    this.id = t.getId();
    this.authorID = t.getAuthorID();
    this.authorName = t.getAuthorName();
    this.assignedToID = t.getAssignedToID();
    this.assignedToName = t.getAssignedToName();
    this.createdAt = t.getCreatedAt();
    this.updatedAt = t.getUpdatedAt();
    this.completedAt = t.getCompletedAt();
    this.completedDay = t.getCompletedDay();
    this.groupName = t.getGroupName();
    this.bodyText = t.getBodyText();
    this.dateTime = t.getDateTime();
    this.dueDate = t.getDueDate();
    this.done = t.getDone();
    this.visible = t.getVisible();
    this.followed = t.getFollowed();
    this.followedBy = new HashSet<>(t.getFollowedBy());
  }

  public Set<User> getFollowedBy() {
    return followedBy;
  }

  public void setFollowedBy(Set<User> followedBy) {
    this.followedBy = followedBy;
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

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public Integer getAssignedToID() {
    return assignedToID;
  }

  public void setAssignedToID(Integer assignedToID) {
    this.assignedToID = assignedToID;
  }

  public String getAssignedToName() {
    return assignedToName;
  }

  public void setAssignedToName(String assignedToName) {
    this.assignedToName = assignedToName;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Timestamp getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Timestamp completedAt) {
    this.completedAt = completedAt;
  }

  public Integer getCompletedDay() {
    return completedDay;
  }

  public void setCompletedDay(Integer completedDay) {
    this.completedDay = completedDay;
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

  public int getFollowed() {
    return followed;
  }

  public void setFollowed(int followed) {
    this.followed = followed;
  }
}
