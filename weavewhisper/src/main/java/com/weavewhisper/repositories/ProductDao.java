package com.weavewhisper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.Product;
import java.util.List;


public interface ProductDao extends JpaRepository<Product, Long> {
	Product findByName(String name);
}
