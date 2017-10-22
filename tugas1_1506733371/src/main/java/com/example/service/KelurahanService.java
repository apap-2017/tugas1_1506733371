package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;

@Service
public class KelurahanService {
	@Autowired
	private KelurahanMapper kelurahanMapper;

	public KelurahanModel selectKelurahanbyID(BigInteger id) {
		return kelurahanMapper.selectKelurahanbyID(id);
	}
	
	public List<KelurahanModel> selectKelurahanList(){
		return kelurahanMapper.selectAllKelurahanList();
	}
	
	public List<KelurahanModel> selectKelurahanList(BigInteger id_kecamatan){
		return kelurahanMapper.selectKelurahanList(id_kecamatan);
	}
}
