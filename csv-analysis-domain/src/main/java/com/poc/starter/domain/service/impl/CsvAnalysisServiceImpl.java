package com.poc.starter.domain.service.impl;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.poc.starter.domain.service.CsvAnalysisService;
import com.poc.starter.domain.viewmodel.CsvRecordViewModel;

public class CsvAnalysisServiceImpl implements CsvAnalysisService {

	@Override
	public List<CsvRecordViewModel> getCsvRecords(String sourceFilename) {
		CSVParser parser = null;
		FileReader reader = null;
		
		List<CsvRecordViewModel> resultVmList = new ArrayList<CsvRecordViewModel>();

		try {
			// CSV column headers
			final String COUNTRY = "country";
			final String UNDER_25 = "under 25";
			final String BTWN_25_AND_50 = "25 to 50";
			final String OVER_50 = "50+";
			
			CSVFormat format = CSVFormat.DEFAULT.withHeader(new String[] { 
				COUNTRY,
				UNDER_25,
				BTWN_25_AND_50,
				OVER_50
			});
			
			// Look for files in the CsvData folder
			reader = new FileReader("CsvData/" + sourceFilename);
			parser = new CSVParser(reader, format);
			
			List<CSVRecord> records = parser.getRecords();
			// Remove the headers row
			records.remove(0);

			// Map raw CSV data to ViewModel
			resultVmList = records.stream().map(new Function<CSVRecord, CsvRecordViewModel>() {
				@Override
				public CsvRecordViewModel apply(CSVRecord input) {
					CsvRecordViewModel vm = new CsvRecordViewModel();
					vm.setCountry(input.get(COUNTRY));
					vm.setUnder25(Integer.parseInt(input.get(UNDER_25)));
					vm.setBetween25and50(Integer.parseInt(input.get(BTWN_25_AND_50)));
					vm.setOver50(Integer.parseInt(input.get(OVER_50)));
					return vm;
				}
			}).collect(Collectors.toList());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (parser != null) {
					parser.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultVmList;
	}

}
