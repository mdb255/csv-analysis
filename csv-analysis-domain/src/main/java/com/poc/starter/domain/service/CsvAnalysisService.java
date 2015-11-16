package com.poc.starter.domain.service;

import java.util.List;

import com.poc.starter.domain.viewmodel.CsvRecordViewModel;

public interface CsvAnalysisService {

	List<CsvRecordViewModel> getCsvRecords(String sourceFilename);
}
