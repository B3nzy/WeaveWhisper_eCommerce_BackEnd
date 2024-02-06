package com.weavewhisper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.OrderHistory;

public interface OrderHistoryDao extends JpaRepository<OrderHistory, Long> {

}
