package com.example.controller;

import java.math.BigInteger;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.PendudukModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class SIKependudukanController
{
    @Autowired
    PendudukService pendudukDAO;
    @Autowired
    KeluargaService keluargaDAO;
    @Autowired
    KelurahanService kelurahanDAO;
    @Autowired
    KecamatanService kecamatanDAO;
    @Autowired
    KotaService kotaDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping("/cari-penduduk")
    public String cariPenduduk ()
    {
        return "cari-penduduk";
    }
    
    @RequestMapping("/penduduk")
	 public String viewPenduduk (Model model, @RequestParam(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		 if (penduduk != null) {
			 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
			 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanbyID(keluarga.getId_kelurahan());
			 KecamatanModel kecamatan = kecamatanDAO.selectKecamatanbyID(kelurahan.getId_kecamatan());
			 KotaModel kota = kotaDAO.selectKotabyID(kecamatan.getId_kota());
			 model.addAttribute ("penduduk", penduduk);
			 model.addAttribute("keluarga", keluarga);
			 model.addAttribute("kelurahan", kelurahan);
			 model.addAttribute("kecamatan", kecamatan);
			 model.addAttribute("kota", kota);
			 return "view-penduduk";
		 }
		 return "not-found";

    }
    
    @RequestMapping("/keluarga")
	 public String viewKeluarga (Model model, @RequestParam(value = "nomor_kk") String nomor_kk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nomor_kk);
		 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanbyID(keluarga.getId_kelurahan());
		 KecamatanModel kecamatan = kecamatanDAO.selectKecamatanbyID(kelurahan.getId_kecamatan());
		 KotaModel kota = kotaDAO.selectKotabyID(kecamatan.getId_kota());
		 List<PendudukModel> anggota_keluarga = pendudukDAO.selectAnggotaKeluarga(keluarga.getId());
		 if (keluarga != null) {
			 model.addAttribute ("keluarga", keluarga);
			 model.addAttribute("kelurahan", kelurahan);
			 model.addAttribute("kecamatan", kecamatan);
			 model.addAttribute("kota", kota);
			 model.addAttribute("anggota", anggota_keluarga);
			 return "view-keluarga";
		 } 
		 else {
			 return "not-found";
		 }
	 }
    
    @RequestMapping("/penduduk/tambah")
	 public String tambahPenduduk (Model model){
		 model.addAttribute("penduduk", new PendudukModel());
		 return "form-add-penduduk";
	 }
	 
	 @RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	 public String tambahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk) {
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 if (keluarga != null) {
			 PendudukModel pendudukBaru = pendudukDAO.addPenduduk(penduduk);
			 model.addAttribute ("nik_sukses", pendudukBaru.getNik());
			 return "success";
		 } else {
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/penduduk/ubah/{nik}")
	 public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik) {
		 PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		 if (penduduk != null) {
			 model.addAttribute("penduduk", penduduk);
			 return "form-update-penduduk";
		 } else {
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	 public String ubahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk, @PathVariable(value = "nik") String nik) {
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 
		 if (keluarga != null) {
			pendudukDAO.updatePenduduk(penduduk);
			model.addAttribute("nik_update", nik);
			 return "success";
		 } else {
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga/tambah")
	 public String tambahKeluarga(Model model){
		 List<KotaModel> kota_list = kotaDAO.selectKotaList();
		 List<KecamatanModel> kecamatan_list = kecamatanDAO.selectKecamatanList();
		 List<KelurahanModel> kelurahan_list = kelurahanDAO.selectKelurahanList();
		 model.addAttribute("keluarga", new KeluargaModel());
		 model.addAttribute("kota_list", kota_list);
		 model.addAttribute("kecamatan_list", kecamatan_list);
		 model.addAttribute("kelurahan_list", kelurahan_list);
		 return "form-add-keluarga";
	 }
	 
	 @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	 public String tambahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga){
		 String nkk = keluargaDAO.addKeluarga(keluarga);
		 if (keluarga != null) {
			 model.addAttribute ("nkk_sukses", nkk);
			 return "success";
		 } else {
			 return "not-found"; 
		 }
	 }
	 
	 @RequestMapping("/keluarga/ubah/{nkk}")
	 public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
		 if (keluarga != null) {
			 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanbyID(keluarga.getId_kelurahan());
			 
			 model.addAttribute("kelurahan_list", kelurahanDAO.selectKelurahanList());
			 model.addAttribute("kelurahan", kelurahan);
			 model.addAttribute("keluarga", keluarga);
			 
			 return "form-update-keluarga";
		 } else {
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	 public String ubahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga, @PathVariable(value = "nkk") String nkk){
		 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanbyID(keluarga.getId_kelurahan());

		 if (kelurahan != null) {
			 keluargaDAO.updateKeluarga(keluarga);
			 model.addAttribute("nkk_update", nkk);
			 return "success";
		 } else {
			 return "not-found"; 
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	 public String ubahStatusKematianSubmit (Model model, @RequestParam ( value = "nik" , required = true ) String nik){
		 pendudukDAO.updateIsWafat(pendudukDAO.selectPenduduk(nik));
		 model.addAttribute("nik_wafat", nik);
		 return "success";
	 }
	 
	 @RequestMapping(value = "/penduduk/cari")
	 public String cariPendudukKelurahan (Model model,
			 @RequestParam(value = "kt", required = false, defaultValue = "0") BigInteger id_kota,
	         @RequestParam(value = "kc", required = false, defaultValue = "0") BigInteger id_kecamatan,
	         @RequestParam(value = "kl", required = false, defaultValue = "0") BigInteger id_kelurahan){
		 
		 List<KotaModel> kota_list = kotaDAO.selectKotaList();
		 model.addAttribute("kota_list", kota_list);
		 
		 if (!id_kelurahan.equals(BigInteger.ZERO)) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("nama_kecamatan", kecamatanDAO.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			 model.addAttribute("id_kecamatan", id_kecamatan);
			 model.addAttribute("nama_kelurahan", kelurahanDAO.selectKelurahanbyID(id_kelurahan).getNama_kelurahan());
			 model.addAttribute("id_kelurahan", id_kelurahan);
			 model.addAttribute("penduduk_list", pendudukDAO.selectPendudukbyIdKelurahan(id_kelurahan));
			 model.addAttribute("showTable", "showTable");
		 } 
		 
		 else if (!id_kecamatan.equals(BigInteger.ZERO)) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("nama_kecamatan", kecamatanDAO.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			 model.addAttribute("id_kecamatan", id_kecamatan);
			 model.addAttribute("kelurahan_list", kelurahanDAO.selectKelurahanList(id_kecamatan));
		 }
		 
		 else if (!id_kota.equals(BigInteger.ZERO)) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("kecamatan_list", kecamatanDAO.selectKecamatanList(id_kota));
		 }
		 return "cari-penduduk";
	 }
	 
}
