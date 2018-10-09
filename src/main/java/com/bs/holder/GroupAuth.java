package com.bs.holder;

public class GroupAuth {
  public GroupAuth() {
  }

  public GroupAuth(String groupName, String groupPassword) {
    this.groupName = groupName;
    this.groupPassword = groupPassword;
  }

  private String groupName;
  private String groupPassword;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupPassword() {
    return groupPassword;
  }

  public void setGroupPassword(String groupPassword) {
    this.groupPassword = groupPassword;
  }
}
