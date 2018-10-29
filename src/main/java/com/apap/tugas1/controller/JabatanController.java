package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class JabatanController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired 
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	public String tambahJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();

		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "tambahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	public String tambahJabatanPost(@ModelAttribute JabatanModel jabatan_tambah, Model model) {
		
		jabatanService.addJabatan(jabatan_tambah);
		
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "tambahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		
		JabatanModel jabatan = jabatanService.findById(idJabatan);
		
		model.addAttribute("title", "Detail Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "detilJabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewAllJabatan(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.findAll();

		
		model.addAttribute("title", "Hapus Jabatan");
		model.addAttribute("listJabatan", listJabatan);
		return "viewAllJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		
		JabatanModel jabatan = jabatanService.findById(idJabatan);
		
		model.addAttribute("title", "Ubah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "ubahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahJabatanPost(@ModelAttribute JabatanModel jabatan_ubah, Model model) {
		
		JabatanModel jabatan = jabatanService.findById(jabatan_ubah.getId());
		
		jabatan.setNama(jabatan_ubah.getNama());
		jabatan.setDeskripsi(jabatan_ubah.getDeskripsi());
		jabatan.setGajiPokok(jabatan_ubah.getGajiPokok());
		
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "ubahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		JabatanModel jabatan = jabatanService.findById(idJabatan);
		
		jabatanService.deleteJabatan(jabatan);
		
		model.addAttribute("title", "Hapus Jabatan");
		return "hapusJabatan";
	}
	
}
