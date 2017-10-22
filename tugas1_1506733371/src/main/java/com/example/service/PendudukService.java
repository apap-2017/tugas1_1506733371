package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.dao.KecamatanMapper;
import com.example.dao.KeluargaMapper;
import com.example.dao.KelurahanMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class PendudukService
{
    @Autowired
    private PendudukMapper pendudukMapper;
    
    @Autowired
    private KeluargaMapper keluargaMapper;
    
    @Autowired
    private KelurahanMapper kelurahanMapper;
    
    @Autowired
    private KecamatanMapper kecamatanMapper;

    public PendudukModel selectPenduduk(String nik)
    {
        return pendudukMapper.selectPenduduk(nik);
    }

	public List<PendudukModel> selectAnggotaKeluarga(BigInteger id_keluarga) {
		return pendudukMapper.selectAnggotaKeluarga(id_keluarga);
	}

	public PendudukModel selectPendudukTerakhir(String nikMin, String nikMax) {
		return pendudukMapper.selectPendudukTerakhir(nikMin, nikMax);
	}

	public PendudukModel addPenduduk(PendudukModel penduduk) {
		
		String nik_penduduk = createNik(penduduk);
		penduduk.setNik(nik_penduduk);
		pendudukMapper.addPenduduk(penduduk);
		return penduduk;
	}
	
	public String createNik(PendudukModel penduduk) {
		KeluargaModel keluarga = keluargaMapper.selectKeluargabyID(penduduk.getId_keluarga());
		KelurahanModel kelurahan = kelurahanMapper.selectKelurahanbyID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanMapper.selectKecamatanbyID(kelurahan.getId_kecamatan());
		String kode_lokasi = kecamatan.getKode_kecamatan();
		
		String tanggal_lahir = penduduk.getTanggal_lahir();
		String tanggal = tanggal_lahir.substring(8, 10);
		if (penduduk.getJenis_kelamin() == 1) {
			int tgl = Integer.parseInt(tanggal);
			tgl = tgl + 40;
			tanggal = Integer.toString(tgl);
		}

		String tempNik = kode_lokasi.substring(0, kode_lokasi.length()-1) + tanggal + tanggal_lahir.substring(5, 7) + tanggal_lahir.substring(2,4);
		 
		String nik_sebelum = pendudukMapper.getNIKSebelum(tempNik);
		Long nik = Long.parseLong(tempNik + "0001");
		if(nik_sebelum != null) {
			 nik = Long.parseLong(nik_sebelum) + 1;
		}
		 
		String nik_penduduk = Long.toString(nik);
		return nik_penduduk;
	}
	
	public void updatePenduduk(PendudukModel penduduk) {
		PendudukModel selectedPenduduk = pendudukMapper.selectPendudukbyId(penduduk.getId());
		if(penduduk.getGolongan_darah() == null) {
			 penduduk.setGolongan_darah(selectedPenduduk.getGolongan_darah());
		} if(penduduk.getStatus_perkawinan() == null) {
			 penduduk.setStatus_perkawinan(selectedPenduduk.getStatus_perkawinan());
		} if(penduduk.getStatus_dalam_keluarga() == null) {
			 penduduk.setStatus_dalam_keluarga(selectedPenduduk.getStatus_dalam_keluarga());
		} if(penduduk.getAgama() == null) {
			 penduduk.setAgama(selectedPenduduk.getAgama());
		}
		
		BigInteger id_penduduk = selectedPenduduk.getId();
		
		if (!selectedPenduduk.equals(penduduk)) {
			if (!(selectedPenduduk.getTanggal_lahir().equals(penduduk.getTanggal_lahir()) && selectedPenduduk.getId_keluarga() == penduduk.getId_keluarga() && selectedPenduduk.getJenis_kelamin() == penduduk.getJenis_kelamin())) {
				penduduk.setNik(createNik(penduduk));
			}
			pendudukMapper.updatePenduduk(penduduk, id_penduduk);
		}
	}
	
	public void updateIsWafat(PendudukModel penduduk) {
		BigInteger id_keluarga = penduduk.getId_keluarga();
		List<PendudukModel> anggota_keluarga = pendudukMapper.selectAnggotaKeluarga(id_keluarga);
		boolean keluargaNonAktif = false;
		int counter = 0;
		
		for (PendudukModel p : anggota_keluarga) {
			if (p.getIs_wafat() != 0) {
				counter = counter + 1;
			}
		}
		if (counter == anggota_keluarga.size()) {
			keluargaNonAktif = true;
		}
		
		if (keluargaNonAktif) {
			keluargaMapper.updateStatusBerlaku(id_keluarga);
		}
		
		pendudukMapper.updateIsWafat(penduduk.getNik());
	}
	
	public List<PendudukModel> selectPendudukbyIdKelurahan(BigInteger id_kelurahan) {
		return pendudukMapper.selectPendudukByIdKelurahan(id_kelurahan);
	}
}
