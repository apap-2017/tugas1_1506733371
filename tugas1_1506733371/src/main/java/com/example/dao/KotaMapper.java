package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select nama_kota from kota where id = #{id}")
    KotaModel selectKotabyID(@Param("id") BigInteger id);
	
	@Select("select * from kota")
    List<KotaModel> selectKotaList();
}
