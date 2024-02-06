package com.weavewhisper.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.Customer;
import com.weavewhisper.entities.OrderHistory;
import com.weavewhisper.entities.Product;


public interface OrderHistoryDao extends JpaRepository<OrderHistory, Long> {
	List<OrderHistory> findByCustomerRef(Customer customer);
	boolean existsByIdAndProductRefAndCustomerRef(Long id, Product productRef, Customer customerRef);
}
