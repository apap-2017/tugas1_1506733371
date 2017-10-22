package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select * from kelurahan where id = #{id}")
    KelurahanModel selectKelurahanbyID(@Param("id") BigInteger id);
	
	@Select("select id, nama_kelurahan from kelurahan")
	List<KelurahanModel> selectAllKelurahanList();
	
	@Select("select id, nama_kelurahan from kelurahan where kelurahan.id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanList(@Param("id_kecamatan") BigInteger id_kecamatan);
}
