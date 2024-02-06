package com.weavewhisper.services;

import java.util.List;

import com.weavewhisper.dtos.OrderHistoryResponseDto;

public interface OrderHistoryService {
	List<OrderHistoryResponseDto> getOrderHistoryOfCustomer(Long customerId);
}
