package com.weavewhisper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.Cart;

public interface CartDao extends JpaRepository<Cart, Long> {

}
