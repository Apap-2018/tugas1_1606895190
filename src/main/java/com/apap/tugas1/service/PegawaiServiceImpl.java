package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;

	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public void add(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public PegawaiModel findPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public int hitungGajibyNIP(String nip) {
		double gajiPokok = 0;
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		int banyakJabatan = listJabatan.size();
		
		for (int i = 0; i < banyakJabatan; i++) {
			double gajiJabatan = listJabatan.get(i).getJabatan().getGajiPokok();
			if (gajiPokok < gajiJabatan) {
				gajiPokok = gajiJabatan;
			}
		}
		
		double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		System.out.println();
		System.out.println(gajiPokok);
		System.out.println(presentaseTunjangan);
		gajiPokok += (presentaseTunjangan*gajiPokok)/100;
		
		System.out.println();
		System.out.println(gajiPokok);
		System.out.println();
		
		return (int) gajiPokok;
	}

	@Override
	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public String makeNip(InstansiModel instansi, PegawaiModel pegawai) {
		ProvinsiModel provinsi = instansi.getProvinsi();
		String nip = "";
		nip += instansi.getId();

		Date tanggalLahir = pegawai.getTanggalLahir();
		String[] tglLahir = (""+tanggalLahir).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		
		nip += pegawai.getTahunMasuk();
		
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		
		
		return nip;
	}

	@Override
	public void delete(PegawaiModel pegawai) {
		pegawaiDb.delete(pegawai);
	}

	@Override
	public void update(PegawaiModel pegawaiUp, PegawaiModel pegawaiPrev) {
		pegawaiPrev.setInstansi(pegawaiUp.getInstansi());
		pegawaiPrev.setNama(pegawaiUp.getNama());
		pegawaiPrev.setNip(pegawaiUp.getNip());
		pegawaiPrev.setTahunMasuk(pegawaiUp.getTahunMasuk());
		pegawaiPrev.setTanggalLahir(pegawaiUp.getTanggalLahir());
		pegawaiPrev.setTempatLahir(pegawaiUp.getTempatLahir());
		
		int jumlahList = pegawaiPrev.getJabatanPegawaiList().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiPrev.getJabatanPegawaiList().get(i).setJabatan(pegawaiUp.getJabatanPegawaiList().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiUp.getJabatanPegawaiList().size(); i++) {
			pegawaiUp.getJabatanPegawaiList().get(i).setPegawai(pegawaiPrev);
			jabatanPegawaiDb.save(pegawaiUp.getJabatanPegawaiList().get(i));
		}
		
	}

}
