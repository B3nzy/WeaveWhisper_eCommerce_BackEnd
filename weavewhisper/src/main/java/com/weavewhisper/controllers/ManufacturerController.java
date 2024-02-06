package com.weavewhisper.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ManufacturerChnageOrderStatusDto;
import com.weavewhisper.dtos.ManufacturerSoldProductResponseDto;
import com.weavewhisper.services.ManufacturerService;

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;

	@GetMapping("/dashboard/getorders/{manufacturerId}")
	public ResponseEntity<?> getUndeliveredOrders(@PathVariable Long manufacturerId) {
		List<ManufacturerSoldProductResponseDto> manufacturerSoldProductResponseDtoList = manufacturerService
				.getAllSoldProducts(manufacturerId);
		return ResponseEntity.status(HttpStatus.OK).body(manufacturerSoldProductResponseDtoList);
	}

	@PostMapping("/changeorderstatus")
	public ResponseEntity<?> handleSoldOrderStatus(
			@RequestBody ManufacturerChnageOrderStatusDto manufacturerChnageOrderStatusDto) {
		ApiResponse apiRes = manufacturerService.changeSoldProductStatus(manufacturerChnageOrderStatusDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}

}
