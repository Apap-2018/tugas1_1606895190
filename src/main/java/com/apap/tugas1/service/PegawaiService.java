package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void add(PegawaiModel pegawai);
	
	PegawaiModel findPegawaiByNip(String nip);
	
	int hitungGajibyNIP(String nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	String makeNip(InstansiModel instansi, PegawaiModel pegawai);
	
	void delete(PegawaiModel pegawai);
	
	void update(PegawaiModel pegawaiUpdate, PegawaiModel pegawaiBefore);

}
