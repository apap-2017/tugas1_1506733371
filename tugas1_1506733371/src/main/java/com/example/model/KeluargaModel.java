package com.example.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private BigInteger id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private BigInteger id_kelurahan;
	private int is_tidak_berlaku;
}
