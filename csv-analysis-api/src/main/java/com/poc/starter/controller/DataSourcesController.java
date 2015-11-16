package com.poc.starter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poc.starter.domain.service.DataSourcesService;

@Controller
@RequestMapping("dataSources")
public class DataSourcesController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataSourcesService dataSourcesService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllAvailableSources(HttpServletRequest request) {
		List<String> response = dataSourcesService.getAvailableCsvFilenames();
		
		log.info("Retrieved available filenames: " + response);
		
		return new ResponseEntity<List<String>>(response, HttpStatus.OK);
	}
}
