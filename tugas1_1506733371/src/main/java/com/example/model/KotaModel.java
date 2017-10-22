package com.example.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KotaModel {
	private BigInteger id;
	private String kode_kota;
	private String nama_kota;
}
