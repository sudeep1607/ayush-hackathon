package com.fts.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fts.services.BarCodeService;

@Controller
@RequestMapping(value = "/barCodeGen")
public class BarCodeController {
	@Autowired
	private BarCodeService barCodeService;
	@RequestMapping(value = "/barCodeMethod", method = RequestMethod.POST)
	@ResponseBody
	public String barCodeMethod(@RequestParam("barCode") String barCode) throws Exception
	{
		   String response = barCodeService.genrateBarCode(barCode);
		   return "{\"success\":\"success\",\"msg\":\"" + response + "\"}";
	}
}