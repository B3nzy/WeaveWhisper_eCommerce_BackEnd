package com.weavewhisper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
