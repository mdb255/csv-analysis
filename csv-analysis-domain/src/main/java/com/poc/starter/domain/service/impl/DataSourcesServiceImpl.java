package com.poc.starter.domain.service.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import com.poc.starter.domain.service.DataSourcesService;

public class DataSourcesServiceImpl implements DataSourcesService {

	@Override
	public List<String> getAvailableCsvFilenames() {
		File csvDir = new File("CsvData");
		
		String[] filenames = csvDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return (name.endsWith(".csv"));
			}
		});
		return Arrays.asList(filenames);
	}
}
