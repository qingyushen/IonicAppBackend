package com.bs.JWT;

import com.bs.mapper.GroupMapper;
import com.bs.mapper.UserMapper;
import com.bs.model.Group;
import com.bs.model.User;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created August 6 2018
 */
@Configuration
public class JWTProvider {
  private final Logger log = LoggerFactory.getLogger(JWTProvider.class);
  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.token-validity-in-seconds}")
  private long tokenValidityInSeconds;
  @Value("${jwt.token-validity-in-seconds-for-remember-me}")
  private long tokenValidityInSecondsForRememberMe;
  @Autowired
  UserMapper userMapper;
  @Autowired
  GroupMapper groupMapper;

  /**
   * create token
   *
   * @param user
   * @param rememberMe
   * @return
   */
  public String createToken(User user, Boolean rememberMe) {
    Map<String, Object> claims = new HashMap();
    claims.put("userid", user.getId());
    claims.put("email", user.getEmail());
    claims.put("team", user.getTeam());
    claims.put("privilege", user.getPrivilege());
    claims.put("name", user.getName());

    long now = (new Date()).getTime();
    Date validity;
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInSecondsForRememberMe);
    } else {
      validity = new Date(now + this.tokenValidityInSeconds);
    }

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * validate token
   *
   * @param authToken
   * @return
   */
  public boolean validateToken(String authToken) throws Exception {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: {}", e);
      throw new Exception("Invalid JWT signature.");
    } catch (MalformedJwtException e) {
      log.info("Invalid JWT token.");
      log.trace("Invalid JWT token trace: {}", e);
      throw new Exception("Invalid JWT token.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace: {}", e);
      throw new Exception("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace: {}", e);
      throw new Exception("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: {}", e);
      throw new Exception("JWT token compact of handler is invalid.");
    }
  }

  /**
   * Parse the token and obtain the 2nd part JSON file containing user details
   * @param token - the encrypted JWT
   * @return The user id in the JSON
   */
  public String parseEmail(String token) {
    if (token.startsWith("Bearer ")) {
      token = token.substring(7, token.length());
    }
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
    return claims.get("email", String.class);
  }

  public User getUserDetails(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    String email = parseEmail(token);
    return userMapper.findUserByEmail(email);
  }

  /**
   * Parse the token and obtain the 2nd part JSON file containing user details
   * @param token - the encrypted JWT
   * @return The user id in the JSON
   */
  public String parseGroup(String token) {
    if (token.startsWith("Bearer ")) {
      token = token.substring(7, token.length());
    }
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
    return claims.get("team", String.class);
  }

  public Group getGroupDetails(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    String curGroup = parseGroup(token);
    return groupMapper.findByName(curGroup);
  }
}
