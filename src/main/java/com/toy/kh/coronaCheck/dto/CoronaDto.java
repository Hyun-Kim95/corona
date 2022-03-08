package com.toy.kh.coronaCheck.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoronaDto {
	private int id;
	private String name;
	private String sido;
	private String addr;
	private String telno;
	private String xposwgs84;
	private String yposwgs84;
}
