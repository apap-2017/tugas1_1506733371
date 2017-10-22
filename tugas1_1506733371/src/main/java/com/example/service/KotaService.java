package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;

@Service
public class KotaService {
	@Autowired
	private KotaMapper kotaMapper;

    public KotaModel selectKotabyID(BigInteger id_kota) {
    	return kotaMapper.selectKotabyID(id_kota);
    }

    public List<KotaModel> selectKotaList(){
    	return kotaMapper.selectKotaList();
    }
}
