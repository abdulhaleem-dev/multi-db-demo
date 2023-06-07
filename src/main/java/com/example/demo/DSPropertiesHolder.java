package com.example.demo;

import java.util.HashMap;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("sp")
public class DSPropertiesHolder {
    HashMap<String, DataSourceProperties> firms = new HashMap<>();

  public HashMap<String, DataSourceProperties> getFirms() {
    return firms;
  }
}
