package com.example.demo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

  Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    setFirmDatabase(req);
    LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
    chain.doFilter(request, response);
    LOG.info("Logging Response :{}", res.getContentType());
  }

  void setFirmDatabase(HttpServletRequest request){
     String firmId =  Arrays.stream(request.getQueryString().split("&"))
          .filter(s -> s.startsWith("firm="))
          .map(s -> s.split("=")[1])
          .findFirst().orElse("FIRM1");
     ClientDatabaseContextHolder.set(firmId);
  }

  // other methods
}