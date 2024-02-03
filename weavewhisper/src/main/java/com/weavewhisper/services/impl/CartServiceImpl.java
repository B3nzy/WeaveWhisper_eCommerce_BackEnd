package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.entities.Cart;
import com.weavewhisper.entities.Customer;
import com.weavewhisper.entities.Product;
import com.weavewhisper.repositories.CartDao;
import com.weavewhisper.repositories.CustomerDao;
import com.weavewhisper.repositories.ProductDao;
import com.weavewhisper.services.CartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public ApiResponse addCart(CartRequestDto cartRequestDto) {
		Cart cart = modelMapper.map(cartRequestDto, Cart.class);
		Product product = productDao.findById(cartRequestDto.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("No such product exists with that id."));
		Customer customer = customerDao.findById(cartRequestDto.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));
		cart.setProductRef(product);
		cart.setCustomerRef(customer);
		cartDao.save(cart);
		return new ApiResponse(true, "Product successfully added to cart.");
	}

	@Override
	public ApiResponse removeCart(Long CartId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));
		if(cartDao.existsByIdAndCustomerRef(CartId, customer)) {
			cartDao.deleteById(CartId);;
			return new ApiResponse(true, "Product successfully deleted from the cart.");
		} else {
			throw new ResourceNotFoundException("No such product exists with that cart id.");
		}
	}

}
