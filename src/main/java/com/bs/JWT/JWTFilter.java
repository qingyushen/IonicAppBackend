package com.bs.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = {"/api/*", "/settings/*"})
public class JWTFilter implements Filter {
  @Autowired
  private com.bs.JWT.JWTProvider jwtProvider;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        filterConfig.getServletContext());
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    if("OPTIONS".equals(httpServletRequest.getMethod())){
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }
    String jwt = resolveToken(httpServletRequest);
    System.out.println("jwt successful: " + jwt);
    try {
      jwtProvider.validateToken(jwt);
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      e.printStackTrace();
      httpServletResponse.sendError(401, e.getMessage());
    }
  }

  @Override
  public void destroy() {

  }

  public String resolveToken(HttpServletRequest httpServletRequest) {

    String bearerToken = httpServletRequest.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}
