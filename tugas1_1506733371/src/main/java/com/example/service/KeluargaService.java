package com.example.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.dao.KeluargaMapper;
import com.example.dao.KelurahanMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	@Autowired
	private KecamatanMapper kecamatanMapper;

	public KeluargaModel selectKeluargabyNKK(String nomor_kk) {
		return keluargaMapper.selectKeluargabyNKK(nomor_kk);
	}

	public KeluargaModel selectKeluargabyID(BigInteger id) {
		return keluargaMapper.selectKeluargabyID(id);
	}
	
	public String addKeluarga(KeluargaModel keluarga) {
		KelurahanModel kelurahan = kelurahanMapper.selectKelurahanbyID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanMapper.selectKecamatanbyID(kelurahan.getId_kecamatan());
		String kode_kecamatan = kecamatan.getKode_kecamatan();
		
		LocalDate localDate = LocalDate.now();
		String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);

		String tempNkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		     
		String nkk_sebelum = keluargaMapper.getNKKSebelum(tempNkk);
		Long nkk = Long.parseLong(tempNkk + "0001");
		if(nkk_sebelum != null) {
			nkk = Long.parseLong(nkk_sebelum) + 1;
		}
	
		String nkk_keluarga = Long.toString(nkk);
		keluarga.setNomor_kk(nkk_keluarga);
		keluarga.setId_kelurahan(kelurahan.getId());
		keluargaMapper.addKeluarga(keluarga);
		return nkk_keluarga;
	}
	
	public void updateKeluarga(KeluargaModel keluarga) {
		KeluargaModel selectedKeluarga = keluargaMapper.selectKeluargabyNKK(keluarga.getNomor_kk());
		KelurahanModel kelurahan = kelurahanMapper.selectKelurahanbyID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanMapper.selectKecamatanbyID(kelurahan.getId_kecamatan());
		selectedKeluarga.setId_kelurahan(kelurahan.getId());
		if (!selectedKeluarga.equals(keluarga)) {
			String nkk = keluarga.getNomor_kk();
			String current_nkk = nkk.substring(0, 12);
			String kode_kecamatan = kecamatan.getKode_kecamatan();
			
			LocalDate localDate = LocalDate.now();
		    String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
		     
		    String rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		    String tempNkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + rilis;
			
			if (!tempNkk.equals(current_nkk)) {
			    String nkk_sebelum = keluargaMapper.getNKKSebelum(tempNkk);
				Long nkk_baru = Long.parseLong(tempNkk + "0001");
				if(nkk_sebelum != null) {
					nkk_baru = Long.parseLong(nkk_sebelum) + 1;
				}
				keluarga.setNomor_kk(Long.toString(nkk_baru));
			}
			
			keluarga.setId_kelurahan(kelurahan.getId());
			keluargaMapper.updateKeluarga(keluarga);
		}
	}
	
	
}