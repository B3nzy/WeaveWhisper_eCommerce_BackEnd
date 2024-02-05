package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.weavewhisper.custom_exceptions.IllegalCartItemException;
import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.AddBalanceResponseDto;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartCountResponseDto;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.dtos.CartResponseDto;
import com.weavewhisper.dtos.PlaceOrderRequestDto;
import com.weavewhisper.dtos.PlaceOrderResponseDto;
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

	@Value("${razorpay.key_id}")
	private String keyId;

	@Value("${razorpay.key_secret}")
	private String secret;

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
	public CartCountResponseDto getCartCount(Long customerId) {
		CartCountResponseDto cartCountResponseDto = new CartCountResponseDto();
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));
		List<Cart> cartList = cartDao.findByCustomerRef(customer);
		cartCountResponseDto.setSuccess(true);
		cartCountResponseDto.setCartCount(cartList.size());
		return cartCountResponseDto;
	}

	@Override
	public ApiResponse removeCart(Long CartId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));
		if (cartDao.existsByIdAndCustomerRef(CartId, customer)) {
			cartDao.deleteById(CartId);
			;
			return new ApiResponse(true, "Product successfully removed from the cart.");
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
			CartResponseDto cartResponseDto = modelMapper.map(c.getProductRef(), CartResponseDto.class);
			if (c.getProductRef().getManufacturer() == null) {
				isActive = false;
			} else {
				cartResponseDto.setBrandName(c.getProductRef().getManufacturer().getBrandName());
			}

			System.out.println(cartResponseDto);
			cartResponseDto.setActive(isActive);
			cartResponseDto.setImageName(c.getProductRef().getImageList().get(0).getImageName());
			cartResponseDto.setColor(c.getColor());
			cartResponseDto.setSize(c.getSize());
			cartResponseDto.setProductId(c.getProductRef().getId());
			cartResponseDto.setCartId(c.getId());

			cartResList.add(cartResponseDto);

		});

		return cartResList;
	}

	@Override
	public PlaceOrderResponseDto handlePlaceOrderRequest(PlaceOrderRequestDto placeOrderRequestDto)
			throws RazorpayException {
		Customer customer = customerDao.findById(placeOrderRequestDto.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("No such user found with that id"));

		String reciept = "order#" + System.currentTimeMillis();

		RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);

		List<Cart> cartList = cartDao.findByCustomerRef(customer);

		double price = 0;

		for (int i = 0; i < cartList.size(); i++) {
			Cart c = cartList.get(i);
			if (c.getProductRef().getManufacturer() == null) {
				throw new IllegalCartItemException("Some product from your cart doesnt exists anymore.");
			} else if (c.getProductRef().getInventoryCount() == 0) {
				throw new IllegalCartItemException("Some product from your cart is already sold out.");
			}
			price += c.getProductRef().getSellingPrice();

		}
		;

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", price * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", reciept);

		JSONObject notes = new JSONObject();
		notes.put("id", customer.getId());
		notes.put("email", customer.getEmail());
		notes.put("fullName", customer.getFullName());
		notes.put("reciept", reciept);
		notes.put("address", placeOrderRequestDto.getAddress());
		notes.put("phoneNumber", placeOrderRequestDto.getPhoneNumber());

		orderRequest.put("notes", notes);

		Order order = razorpayClient.orders.create(orderRequest);

		System.out.println(order);

		String orderId = order.get("id");

		PlaceOrderResponseDto placeOrderResponseDto = new PlaceOrderResponseDto();
		placeOrderResponseDto.setOrderId(orderId);
		placeOrderResponseDto.setTotalAmount(price);
		placeOrderResponseDto.setSuccess(true);
		placeOrderResponseDto.setFullName(customer.getFullName());
		placeOrderResponseDto.setAddress(placeOrderRequestDto.getAddress());
		placeOrderResponseDto.setPhoneNumber(placeOrderRequestDto.getPhoneNumber());
		placeOrderResponseDto.setEmail(customer.getEmail());
		return placeOrderResponseDto;
	}

}
