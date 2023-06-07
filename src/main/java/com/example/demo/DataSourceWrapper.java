package com.example.demo;

import javax.sql.DataSource;

public class DataSourceWrapper {
  String key;
  DataSource dataSource;

  public DataSourceWrapper(String key, DataSource dataSource) {
    this.key = key;
    this.dataSource = dataSource;
  }

  public String getKey() {
    return key;
  }

  public DataSource getDataSource() {
    return dataSource;
  }
}
