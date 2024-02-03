package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.dtos.CartResponseDto;
import com.weavewhisper.entities.Cart;
import com.weavewhisper.entities.Customer;
import com.weavewhisper.entities.Product;
import com.weavewhisper.enums.CategoryType;
import com.weavewhisper.enums.GenderType;
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
		if (cartDao.existsByIdAndCustomerRef(CartId, customer)) {
			cartDao.deleteById(CartId);
			;
			return new ApiResponse(true, "Product successfully deleted from the cart.");
		} else {
			throw new ResourceNotFoundException("No such product exists with that cart id.");
		}
	}

	@Override
	public List<CartResponseDto> getCartItemsForCustomer(Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));

		List<Cart> cartList = cartDao.findByCustomerRef(customer);

		List<CartResponseDto> cartResList = new ArrayList<>();
		cartList.forEach(c -> {
			boolean isActive = true;
			if (c.getProductRef().getManufacturer() == null) {
				isActive = false;
			}
			CartResponseDto cartResponseDto = modelMapper.map(c.getProductRef(), CartResponseDto.class);
			System.out.println(cartResponseDto);
			cartResponseDto.setActive(isActive);
			cartResponseDto.setImageName(c.getProductRef().getImageList().get(0).getImageName());

			cartResList.add(cartResponseDto);

		});

		return cartResList;
	}

}
