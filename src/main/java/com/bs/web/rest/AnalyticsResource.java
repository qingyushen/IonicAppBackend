package com.bs.web.rest;

import com.bs.JWT.JWTProvider;
import com.bs.holder.Ranking;
import com.bs.mapper.GroupMapper;
import com.bs.mapper.TodoTaskMapper;
import com.bs.mapper.UserMapper;
import com.bs.model.Group;
import com.bs.model.LoginInfo;
import com.bs.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/analysis")
public class AnalyticsResource {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private GroupMapper groupMapper;
  @Autowired
  private TodoTaskMapper todoTaskMapper;
  @Autowired
  private JWTProvider jwtProvider;

  @GetMapping(value="/tasksPerDay")
  public List<Ranking> tasksCompleted(HttpServletRequest request) {
    User current = jwtProvider.getUserDetails(request);
    List<Ranking> out = new ArrayList<>();
    Ranking rank;

    for (int i = 1; i < 6; i++) {
      rank = new Ranking(i, todoTaskMapper.countTasks(current.getTeam(), i));
      out.add(rank);
    }

    return out;
  }
}
