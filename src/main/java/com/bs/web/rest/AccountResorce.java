package com.bs.web.rest;

import com.bs.JWT.JWTProvider;
import com.bs.holder.Registration;
import com.bs.mapper.GroupMapper;
import com.bs.mapper.UserMapper;
import com.bs.model.Group;
import com.bs.model.LoginInfo;
import com.bs.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/auth")
public class AccountResorce {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private GroupMapper groupMapper;
  @Autowired
  private JWTProvider provider;
  private com.bs.security.DESedeOperator des;
  private String token;
  private LoginInfo loginInfo;

  /**
   * Log the user and send a token
   * @param login contains the username and password
   * @return
   */
  @PostMapping("/login")
  public LoginInfo login(@RequestBody User login) {
    System.out.println("printing:" + login.getEmail() + login.getPassword());
    try {
      User dbUser = userMapper.findUserByEmail(login.getEmail());
      // If the login username is valid, enter loop and check password
      if (dbUser != null) {
        if (login.getPassword().equals(des.deEncrypt(dbUser.getPassword())) ||
            login.getPassword().equals(dbUser.getPassword())) {

          // Set the JSON object to be returned
          loginInfo = new LoginInfo();
          loginInfo.setId(dbUser.getId());
          loginInfo.setName(dbUser.getName());
          Group curGroup = groupMapper.findByName(dbUser.getTeam());
          if (dbUser.getGroupsAdminOf().contains(curGroup)) {
            loginInfo.setPrivilege("admin");
          } else {
            loginInfo.setPrivilege("member");
          }
          loginInfo.setTeam(dbUser.getTeam());
          loginInfo.setEmail(dbUser.getEmail());
          loginInfo.setProfileImgUrl(dbUser.getProfileImgUrl());
          loginInfo.setCoverImgUrl(dbUser.getCoverImgUrl());
          loginInfo.setLanguage(dbUser.getLanguage());
          token = provider.createToken(dbUser, true);
          loginInfo.setToken(token);
          //response.addHeader("Authorization", token);
          return loginInfo;
        }
      } else {
        throw new Exception("The email or password does not exist.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Registration page - Called when registering a new user
   * @param info - Holder object containing name, email, password, group name and password
   * @return - null if unsuccessful, and the user object of successful
   */
  @PostMapping("/addUser")
  public User addUser(@RequestBody Registration info) {
    User u = new User(info.getName(), info.getEmail(), info.getPassword(), info.getGroupName());
    if (userMapper.checkExistence(u.getEmail()).size() > 0) {
      return null; // email already registered
    }
    if (info.getGroupName().equals("default") || info.getGroupName() == null) {
      return null;
    }
    // Create a new group or find existing group
    Group groupToJoin = groupMapper.findByName(u.getTeam());
    // If group does not exist, create a new one
    if (groupToJoin == null) {

      groupToJoin = new Group(u.getTeam(), des.encrypt(info.getGroupPassword()));
      u.getGroupsAdminOf().add(groupToJoin);
    }
    // If group exists but incorrect password entered
    else if (!groupToJoin.getPassword().equals(des.encrypt(info.getGroupPassword()))) {
      return null;
    }
    u.setPassword(des.encrypt(u.getPassword()));
    //groupToJoin.getUsers().add(u);
    u.getGroups().add(groupToJoin);
    u.setCoverImgUrl("../../assets/img/BG.jpg");
    u.setProfileImgUrl("./assets/img/user.jpg");
    userMapper.save(u);
    return u;
  }
}
