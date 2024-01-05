package com.weavewhisper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weavewhisper.entities.Manufacturer;

public interface ManufacturerDao extends JpaRepository<Manufacturer, Long> {
	Manufacturer findByEmailAndPassword(String email, String password);
	boolean existsByBrandName(String brandName);
	boolean existsByPanNumber(String panNumber);
}
