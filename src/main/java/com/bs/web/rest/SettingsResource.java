package com.bs.web.rest;

import com.bs.JWT.JWTProvider;
import com.bs.mapper.GroupMapper;
import com.bs.mapper.TodoTaskMapper;
import com.bs.model.Group;
import com.bs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;

@RestController
@Transactional
@RequestMapping("/settings")
public class SettingsResource {

  @Autowired
  TodoTaskMapper todoTaskMapper;
  @Autowired
  GroupMapper groupMapper;
  @Autowired
  JWTProvider jwtProvider;

  @GetMapping(value = "/changeLang/{lang}")
  public HashMap<String, Integer> changeLang(HttpServletRequest request,
                                             @PathVariable("lang") String lang) {
    User current = jwtProvider.getUserDetails(request);
    current.setLanguage(lang);
    HashMap<String, Integer> out = new HashMap<String, Integer>();
    // Check if group to join already exists, if not then create a new group
    // Add the group to user, Add user to group, save both
    out.put("success", 1);
    return out;
  }

  @GetMapping(value = "/getLang")
  public HashMap<String, String> getLang(HttpServletRequest request) {
    User current = jwtProvider.getUserDetails(request);
    HashMap<String, String> out = new HashMap<>();
    // Check if group to join already exists, if not then create a new group
    // Add the group to user, Add user to group, save both
    out.put("lang", current.getLanguage());
    return out;
  }
}
