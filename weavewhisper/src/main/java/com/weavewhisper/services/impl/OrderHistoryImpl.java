package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.OrderHistoryResponseDto;
import com.weavewhisper.entities.Customer;
import com.weavewhisper.entities.OrderHistory;
import com.weavewhisper.repositories.CustomerDao;
import com.weavewhisper.repositories.OrderHistoryDao;
import com.weavewhisper.services.OrderHistoryService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderHistoryImpl implements OrderHistoryService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private OrderHistoryDao orderHistoryDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<OrderHistoryResponseDto> getOrderHistoryOfCustomer(Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));

		List<OrderHistory> orderHistoryList = orderHistoryDao.findByCustomerRef(customer).stream()
				.sorted((p1, p2) -> (-1) * p1.getCreatedAt().compareTo(p2.getCreatedAt())).collect(Collectors.toList());

		List<OrderHistoryResponseDto> orderHistoryResList = new ArrayList<>();

		for (int i = 0; i < orderHistoryList.size(); i++) {
			OrderHistory oHist = orderHistoryList.get(i);

			OrderHistoryResponseDto oHistRes = modelMapper.map(oHist, OrderHistoryResponseDto.class);

			oHistRes.setName(oHist.getProductRef().getName());
			oHistRes.setImageName(oHist.getProductRef().getImageList().get(0).getImageName());
			oHistRes.setOrderHistoryId(oHist.getId());
			oHistRes.setProductId(oHist.getProductRef().getId());

			System.out.println(oHistRes);

			orderHistoryResList.add(oHistRes);
		}

		return orderHistoryResList;
	}

}
