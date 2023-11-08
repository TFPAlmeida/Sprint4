package com.sprint4_activity.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByName(String name);

	boolean existsByName(String name);

	Product findProductByName(String name);

	Product findProductById(long id);

	@Query("FROM Product WHERE barcode=:bc")
	Product findProductByBarCode(String bc);

	@Query(value = "SELECT P.* FROM PRODUCTS_TBL AS P INNER JOIN CATEGORY_TBL AS C ON P.CATEGORY_ID = C.CATEGORY_ID WHERE C.NAME = :cat", nativeQuery = true)
	List<Product> findProductsByCategory(String cat);

	@Query(value = "SELECT * FROM PRODUCTS_TBL AS P INNER JOIN ORDER_PRODUCT AS O ON O.PRODUCT_ID = P.ID INNER JOIN CATEGORY_TBL AS C ON P.CATEGORY_ID = C.CATEGORY_ID WHERE C.NAME = :cat", nativeQuery = true)
	List<Product> findProductsInOrdersByCategory(String cat);

}
