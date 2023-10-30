package com.sprint4_activity.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByName(String name);

	boolean existsByName(String name);

	Product findProductByName(String name);

	Product findProductById(long id);
}
