package com.bs.holder;

public class Ranking {
  private Integer dayOfWeek = -1;
  private Integer tasksCompleted = 0;


  public Ranking(Integer dayOfWeek, Integer tasksCompleted) {
    this.dayOfWeek = dayOfWeek;
    this.tasksCompleted = tasksCompleted;
  }

  public Integer getDayOfWeek() {

    return dayOfWeek;
  }

  public void setDayOfWeek(Integer dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Integer getTasksCompleted() {
    return tasksCompleted;
  }

  public void setTasksCompleted(Integer tasksCompleted) {
    this.tasksCompleted = tasksCompleted;
  }
}
