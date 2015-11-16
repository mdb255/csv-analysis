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
import org.springframework.web.bind.annotation.RequestParam;

import com.poc.starter.domain.service.CsvAnalysisService;
import com.poc.starter.domain.viewmodel.CsvRecordViewModel;

@Controller
@RequestMapping("csvData")
public class CsvDataController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CsvAnalysisService csvAnalysisService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<CsvRecordViewModel>> getCsvData(@RequestParam String sourceFilename, HttpServletRequest request) {
		List<CsvRecordViewModel> response = csvAnalysisService.getCsvRecords(sourceFilename);
		
		log.info("Retrieved CSV data: " + response);
		return new ResponseEntity<List<CsvRecordViewModel>>(response, HttpStatus.OK);
	}
}
