package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;

@Service
public class KecamatanService {
	@Autowired
	private KecamatanMapper kecamatanMapper;

    public KecamatanModel selectKecamatanbyID(BigInteger id_kecamatan) {
    		return kecamatanMapper.selectKecamatanbyID(id_kecamatan);
    }
    
    public List<KecamatanModel> selectKecamatanList(){
    	return kecamatanMapper.selectAllKecamatanList();
    }
    
    public List<KecamatanModel> selectKecamatanList(BigInteger id_kota){
    	return kecamatanMapper.selectKecamatanList(id_kota);
    }
    
}
