package com.poc.starter.domain.viewmodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CsvRecordViewModel {

	@Getter @Setter private String country;
	
	@Getter @Setter private Integer under25;
	
	@Getter @Setter private Integer between25and50;
	
	@Getter @Setter private Integer over50;
}
