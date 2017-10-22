package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper{
    @Select("select * from penduduk where nik = #{nik}")
    PendudukModel selectPenduduk(@Param("nik") String nik);
    
    @Select("select * from penduduk where id = #{id}")
    PendudukModel selectPendudukbyId(@Param("id") BigInteger id);
    
    @Select("select * from penduduk where id_keluarga = #{id_keluarga}")
    List<PendudukModel> selectAnggotaKeluarga(@Param("id_keluarga") BigInteger id_keluarga);
    

    @Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, "
    		+ "agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
    		+ "VALUES ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin}, "
    		+ "${is_wni}, '${id_keluarga}', '${agama}', "
    		+ "'${pekerjaan}', '${status_perkawinan}', '${status_dalam_keluarga}', '${golongan_darah}', '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
    
    @Select("select * " 
			+ "from penduduk "
			+ "where nik BETWEEN #{nikMin} and #{nikMax}"
			+ "order by nik "
			+ "DESC LIMIT 1 ")
	PendudukModel selectPendudukTerakhir(@Param("nikMin")String nikMin,@Param("nikMax") String nikMax);
    
    @Select("select MAX(nik) from penduduk WHERE nik LIKE CONCAT(#{tempNik},'%')")
    String getNIKSebelum(String tempNik);
    
    @Update("UPDATE penduduk SET nik = '${penduduk.nik}', nama = '${penduduk.nama}', tempat_lahir = '${penduduk.tempat_lahir}', "
    		+ "tanggal_lahir = '${penduduk.tanggal_lahir}', jenis_kelamin = '${penduduk.jenis_kelamin}', "
    		+ "is_wni = '${penduduk.is_wni}', id_keluarga = '${penduduk.id_keluarga}', agama = '${penduduk.agama}', "
    		+ "pekerjaan = '${penduduk.pekerjaan}', status_perkawinan = '${penduduk.status_perkawinan}', "
    		+ "status_dalam_keluarga = '${penduduk.status_dalam_keluarga}', "
    		+ "golongan_darah = '${penduduk.golongan_darah}' "
    		+ "WHERE id = #{id}")
    void updatePenduduk(@Param("penduduk")PendudukModel penduduk, @Param("id")BigInteger id);
    
    @Update("UPDATE penduduk SET  is_wafat = '1' WHERE nik = #{nik}")
    void updateIsWafat(@Param("nik") String nik);
    
    @Select("select nik, nama, jenis_kelamin from "
    		+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
    		+ "JOIN penduduk "
    		+ "ON keluarga.id = penduduk.id_keluarga")
    List<PendudukModel> selectPendudukByIdKelurahan(BigInteger id_kelurahan);
    
}
