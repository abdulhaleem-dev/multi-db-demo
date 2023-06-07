package com.example.demo;

import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackageClasses = Matter.class)
@EnableJpaRepositories(basePackageClasses = MatterRepository.class)
@EnableTransactionManagement
public class DataSourceConfig {

  @Autowired
  DSPropertiesHolder dsPropertiesHolder;

  @Bean("firmRoutingDataSource")
  @DependsOn("dataSources")
  public DataSource routingDataSource(Map<Object,Object> dataSources){
    AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
    routingDataSource.setTargetDataSources(dataSources);
    routingDataSource.setDefaultTargetDataSource(dataSources.get("FIRM1"));
    return routingDataSource;
  }

  @Primary
  @Bean(name = "dataSources")
  public Map<Object, Object> dataSources(){
    return dsPropertiesHolder.getFirms().values()
        .stream().map(dsp -> new DataSourceWrapper(dsp.getName(),toDataSource(dsp)))
        .collect(Collectors.toMap(DataSourceWrapper::getKey, DataSourceWrapper::getDataSource));
  }

  DataSource toDataSource(DataSourceProperties dsp){
    DataSource dataSource = DataSourceBuilder.create()
        .url(dsp.getUrl())
        .username(dsp.getUsername())
        .password(dsp.getPassword())
        .driverClassName(dsp.getDriverClassName())
        .build();
    return dataSource;
  }
}
