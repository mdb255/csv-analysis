package com.poc.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.poc.starter.domain.service.CsvAnalysisService;
import com.poc.starter.domain.service.DataSourcesService;
import com.poc.starter.domain.service.impl.CsvAnalysisServiceImpl;
import com.poc.starter.domain.service.impl.DataSourcesServiceImpl;

@Configuration
public class AppConfig {
	
	@Bean
	public CsvAnalysisService csvAnalysisService() {
		return new CsvAnalysisServiceImpl();
	}
	
	@Bean
	public DataSourcesService dataSourcesService() {
		return new DataSourcesServiceImpl();
	}
}
