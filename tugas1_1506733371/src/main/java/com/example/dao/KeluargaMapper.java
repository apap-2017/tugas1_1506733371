package com.example.dao;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
    KeluargaModel selectKeluargabyNKK(@Param("nomor_kk") String nomor_kk);
	
	@Select("select * from keluarga where id = #{id}")
	KeluargaModel selectKeluargabyID(@Param("id") BigInteger id);
	
	@Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) values (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga(KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} where id = #{id}")
    void updateKeluarga(KeluargaModel keluarga);
	
	@Select("select MAX(nomor_kk) from keluarga WHERE nomor_kk LIKE CONCAT(#{tempNkk},'%')")
    String getNKKSebelum(String tempNkk);
	
	@Update("update keluarga SET is_tidak_berlaku = '1' WHERE id = #{id}")
	void updateStatusBerlaku(@Param("id") BigInteger id_keluarga);
}
