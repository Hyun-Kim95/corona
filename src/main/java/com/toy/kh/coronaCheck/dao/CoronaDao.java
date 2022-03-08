package com.toy.kh.coronaCheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.toy.kh.coronaCheck.dto.CoronaDto;

@Mapper
public interface CoronaDao {

	void saveCenter(@Param("yadmNm") String yadmNm, @Param("sidoCdNm") String sidoCdNm, @Param("addr") String addr,
			@Param("telno") String telno, @Param("XPosWgs84") String XPosWgs84, @Param("YPosWgs84") String YPosWgs84);

	List<CoronaDto> getCenterOfLocal(@Param("local")String local);

}
