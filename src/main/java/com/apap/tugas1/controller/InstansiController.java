package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {

	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/instansi/tambah", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> instansiSearch(@RequestParam(value = "provinsiId", required = true) Long provinsiId){
		ProvinsiModel provinsi = provinsiService.findById(provinsiId);
		return provinsi.getInstansiList();
	}

}