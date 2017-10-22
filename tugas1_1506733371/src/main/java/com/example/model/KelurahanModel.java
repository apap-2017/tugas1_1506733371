package com.example.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel { 
	private BigInteger id;
	private BigInteger id_kecamatan;
	private String kode_kelurahan;
	private String nama_kelurahan;
	private String kode_pos;
}
