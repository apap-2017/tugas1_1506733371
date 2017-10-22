package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	 @Select("select * from kecamatan where id = #{id}")
	 KecamatanModel selectKecamatanbyID(@Param("id") BigInteger id);
	 
	 @Select("select id, nama_kecamatan from kecamatan")
	 List<KecamatanModel> selectAllKecamatanList();
	 
	 @Select("select id, nama_kecamatan from kecamatan where kecamatan.id_kota = #{id_kota}")
	 List<KecamatanModel> selectKecamatanList(@Param("id_kota") BigInteger id_kota);
}
