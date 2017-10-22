package com.example.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private BigInteger id;
	private BigInteger id_kota;
	private String kode_kecamatan;
	private String nama_kecamatan;
}
